package com.kalmakasa.kalmakasa.data.network

//import com.kalmakasa.kalmakasa.data.network.model.ApiDoctor
//import com.kalmakasa.kalmakasa.data.network.model.ApiUser
//import com.kalmakasa.kalmakasa.data.network.response.fake.AuthResponse
//import com.kalmakasa.kalmakasa.data.network.response.fake.DoctorResponse
//import com.kalmakasa.kalmakasa.data.network.response.fake.ListDoctorResponse
//import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
//import kotlinx.coroutines.delay
//import kotlin.random.Random
//
//class FakeApiService : ApiService {
//    override suspend fun login(email: String, password: String): AuthResponse {
//        delay(500)
//        return if (Random.nextInt(1, 4) == 3) {
//            AuthResponse(true, "Login Error")
//        } else {
//            AuthResponse(
//                false, "Login Success", ApiUser(
//                    "User Full Name",
//                    "123",
//                    "AjkdhasjhToken"
//                )
//            )
//
//        }
//    }
//
//    override suspend fun register(name: String, email: String, password: String): AuthResponse {
//        delay(500)
//        return if (Random.nextInt(1, 4) == 3) {
//            AuthResponse(true, "Register Error")
//        } else {
//            AuthResponse(
//                false, "Register Success", ApiUser(
//                    "User Full Name",
//                    "123",
//                    "AjkdhasjhToken"
//                )
//            )
//        }
//    }
//
//    override suspend fun getListDoctor(): ListDoctorResponse {
//        delay(500)
////        return if (Random.nextInt(1, 4) == 3) {
////            ListDoctorResponse(
////                error = true,
////                message = "Error retrieving the data, please check your connection",
////            )
////        } else {
//        return ListDoctorResponse(
//            error = false,
//            message = "",
//            listDoctor = FakeDataSource.doctorList
//        )
//    }
//
//    override suspend fun getDoctorById(id: String): DoctorResponse {
//        delay(500)
//        return DoctorResponse(
//            error = false,
//            message = "Berhasil",
//            doctor = FakeDataSource.doctorList.find { it.id == id }
//        )
//    }
//}
//
//object FakeDataSource {
//    val doctorList = listOf(
//        ApiDoctor(
//            id = "Doctor-1-id",
//            name = "Doctor 1",
//            specialist = "Depression",
//            expYear = 2,
//            patients = 200,
//            biography = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellen tesque in imperdiet augue. Mauris in purus lorem. In egestas ultrices hendrerit. In fringilla magna in odio semper iaculis.",
//        ),
//        ApiDoctor(
//            id = "Doctor-2-id",
//            name = "Doctor 2",
//            specialist = "Anxiety",
//            expYear = 2,
//            patients = 315,
//            biography = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellen tesque in imperdiet augue. Mauris in purus lorem. In egestas ultrices hendrerit. In fringilla magna in odio semper iaculis.",
//        ),
//        ApiDoctor(
//            id = "Doctor-3-id",
//            name = "Doctor 3",
//            specialist = "Depression",
//            expYear = 3,
//            patients = 2150,
//            biography = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellen tesque in imperdiet augue. Mauris in purus lorem. In egestas ultrices hendrerit. In fringilla magna in odio semper iaculis.",
//        )
//    )
//}