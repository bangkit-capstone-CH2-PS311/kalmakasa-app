package com.kalmakasa.kalmakasa.presentation.screens.app_consultant.appointment_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.domain.repository.ReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAppointmentViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,
) : ViewModel() {

    private var currentIndex = MutableStateFlow(0)

    private val questions = listOf(
        ReportQuestions.ISSUES,
        ReportQuestions.DYNAMIC,
        ReportQuestions.TRIGGER,
        ReportQuestions.RECOMMEND,
    )

    private var answers = MutableStateFlow(
        questions.associateWith { "" }
    )

    private var isUploading = MutableStateFlow(false)
    private var uploadState: MutableStateFlow<Resource<String>> =
        MutableStateFlow(Resource.Loading)

    private val reportState: StateFlow<PatientReportState> = combine(
        currentIndex, answers, isUploading, uploadState
    ) { index, answer, isUploading, uploadState ->
        PatientReportState(
            currentIndex = index,
            currentQuestion = questions[index].question,
            currentAnswer = answer[questions[index]] ?: "",
            progress = (index + 1).toFloat() / questions.size,
            isFinal = index == (questions.size - 1),
            isUploading = isUploading,
            uploadState = uploadState,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PatientReportState()
    )

    val linkState: MutableStateFlow<Resource<String>?> = MutableStateFlow(null)
    val consentState: MutableStateFlow<Resource<String>?> = MutableStateFlow(null)

    private val appointmentId: MutableStateFlow<String?> = MutableStateFlow(null)

    val reservation: MutableStateFlow<Resource<Reservation>> = MutableStateFlow(Resource.Loading)

    val uiState: StateFlow<DetailAppointmentState> = combine(reportState, reservation)
    { reportState, reservation ->
        DetailAppointmentState(reservation, reportState)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailAppointmentState()
    )

    fun getAppointmentDetail(id: String) {
        viewModelScope.launch {
            appointmentId.value = id
            reservationRepository.getReservationDetail(id).collect {
                reservation.value = it
            }
        }
    }

    fun generateLink(reservation: Reservation) {
        viewModelScope.launch {
            val times = reservation.time.split(" - ")
            val startTime = times[0]
            val endTime = times[1]
            reservationRepository.createReservationLink(
                reservation.id,
                DateUtil.localToApiDate(reservation.date),
                startTime,
                endTime,
                reservation.patient.id,
                reservation.consultant.id
            ).collect {
                linkState.value = it
                if (it is Resource.Success) {
                    appointmentId.value?.let { id ->
                        getAppointmentDetail(id)
                    }
                }
            }
        }
    }

    fun generateConsentLink() {
        viewModelScope.launch {
            reservationRepository.getConsentLink().collect {
                consentState.value = it
            }
        }
    }

    fun uploadReport(id: String) {
        viewModelScope.launch {
            isUploading.value = true
            reservationRepository.createReservationReport(
                id = id,
                commonIssues = answers.value[ReportQuestions.ISSUES] ?: "",
                psychologicalDynamics = answers.value[ReportQuestions.DYNAMIC] ?: "",
                triggers = answers.value[ReportQuestions.TRIGGER] ?: "",
                recommendation = answers.value[ReportQuestions.RECOMMEND] ?: ""
            ).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        isUploading.value = false
                        uploadState.value = result
                    }

                    is Resource.Loading -> {
                        isUploading.value = true
                        uploadState.value = result
                    }

                    is Resource.Success -> {
                        isUploading.value = false
                        uploadState.value = Resource.Success("Success")
                        getAppointmentDetail(id)
                    }
                }
            }
        }
    }

    fun updateAnswer(text: String) {
        answers.update {
            val ans = it.toMutableMap()
            ans[questions[currentIndex.value]] = text
            ans
        }
    }

    fun nextQuestion() {
        if (currentIndex.value < questions.size) {
            currentIndex.value++
        }
    }

    fun previousQuestion() {
        if (currentIndex.value > 0) {
            currentIndex.value--
        }
    }
}

data class DetailAppointmentState(
    val reservation: Resource<Reservation> = Resource.Loading,
    val reportState: PatientReportState = PatientReportState(),
)

data class PatientReportState(
    val currentIndex: Int = 0,
    val currentQuestion: String = "",
    val currentAnswer: String = "",
    val progress: Float = 0f,
    val uploadState: Resource<String> = Resource.Loading,
    val isUploading: Boolean = false,
    val isFinal: Boolean = false,
)

enum class ReportQuestions(val question: String) {
    TRIGGER("Trigger"),
    ISSUES("Common Issues"),
    DYNAMIC("Psychological Dynamics"),
    RECOMMEND("Recommendation"),
}