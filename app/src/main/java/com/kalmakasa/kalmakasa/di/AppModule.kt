package com.kalmakasa.kalmakasa.di

import android.content.Context
import com.kalmakasa.kalmakasa.data.UserPreferences
import com.kalmakasa.kalmakasa.data.dataStore
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.data.network.retrofit.RetrofitFactory
import com.kalmakasa.kalmakasa.data.repository.ConsultantRepository
import com.kalmakasa.kalmakasa.data.repository.UserRepositoryImpl
import com.kalmakasa.kalmakasa.domain.repository.DoctorRepository
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
    fun provideFakeApiService(): ApiService {
        return RetrofitFactory.makeRetrofitService()
//        return FakeApiService()
    }

    @Provides
    @Singleton
    fun provideUserRepository(pref: UserPreferences, apiService: ApiService): UserRepository {
        return UserRepositoryImpl(pref, apiService)
    }

    @Provides
    @Singleton
    fun provideDoctorRepository(apiService: ApiService): DoctorRepository {
        return ConsultantRepository(apiService)
    }
}
