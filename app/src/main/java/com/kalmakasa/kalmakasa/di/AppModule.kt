package com.kalmakasa.kalmakasa.di

import android.content.Context
import com.kalmakasa.kalmakasa.data.UserPreferences
import com.kalmakasa.kalmakasa.data.dataStore
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.data.network.retrofit.RetrofitFactory
import com.kalmakasa.kalmakasa.data.repository.ArticleRepositoryImpl
import com.kalmakasa.kalmakasa.data.repository.ConsultantRepositoryImpl
import com.kalmakasa.kalmakasa.data.repository.JournalRepositoryImpl
import com.kalmakasa.kalmakasa.data.repository.ReservationRepositoryImpl
import com.kalmakasa.kalmakasa.data.repository.UserRepositoryImpl
import com.kalmakasa.kalmakasa.domain.repository.ArticleRepository
import com.kalmakasa.kalmakasa.domain.repository.ConsultantRepository
import com.kalmakasa.kalmakasa.domain.repository.JournalRepository
import com.kalmakasa.kalmakasa.domain.repository.ReservationRepository
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPref(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context.dataStore)
    }


    @Provides
    @Singleton
    fun provideFakeApiService(pref: UserPreferences): ApiService {
        return RetrofitFactory.makeRetrofitService(pref)
    }

    @Provides
    @Singleton
    fun provideUserRepository(pref: UserPreferences, apiService: ApiService): UserRepository {
        return UserRepositoryImpl(pref, apiService)
    }

    @Provides
    @Singleton
    fun provideDoctorRepository(apiService: ApiService): ConsultantRepository {
        return ConsultantRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(): ArticleRepository {
        return ArticleRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideJournalRepository(apiService: ApiService): JournalRepository {
        return JournalRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideReservationRepository(apiService: ApiService): ReservationRepository {
        return ReservationRepositoryImpl(apiService)
    }
}
