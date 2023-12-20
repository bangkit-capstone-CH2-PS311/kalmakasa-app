package com.kalmakasa.kalmakasa.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kalmakasa.kalmakasa.data.UserPreferences
import com.kalmakasa.kalmakasa.data.dataStore
import com.kalmakasa.kalmakasa.data.database.KalmakasaRoomDatabase
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.data.network.retrofit.MachineLearningService
import com.kalmakasa.kalmakasa.data.network.retrofit.RetrofitFactory
import com.kalmakasa.kalmakasa.data.repository.ArticleRepositoryImpl
import com.kalmakasa.kalmakasa.data.repository.ConsultantRepositoryImpl
import com.kalmakasa.kalmakasa.data.repository.HealthTestRepositoryImpl
import com.kalmakasa.kalmakasa.data.repository.JournalRepositoryImpl
import com.kalmakasa.kalmakasa.data.repository.MessageRepositoryImpl
import com.kalmakasa.kalmakasa.data.repository.ReservationRepositoryImpl
import com.kalmakasa.kalmakasa.data.repository.UserRepositoryImpl
import com.kalmakasa.kalmakasa.domain.repository.ArticleRepository
import com.kalmakasa.kalmakasa.domain.repository.ConsultantRepository
import com.kalmakasa.kalmakasa.domain.repository.HealthTestRepository
import com.kalmakasa.kalmakasa.domain.repository.JournalRepository
import com.kalmakasa.kalmakasa.domain.repository.MessageRepository
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
    fun provideApiService(pref: UserPreferences): ApiService {
        return RetrofitFactory.makeBackend(pref)
    }

    @Provides
    @Singleton
    fun provideMLService(): MachineLearningService {
        return RetrofitFactory.makeMachineLearning()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): KalmakasaRoomDatabase {
        return Room.databaseBuilder(
            app, KalmakasaRoomDatabase::class.java, "kalmakasa_db",
        )
            .fallbackToDestructiveMigration()
            .build()
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
    fun provideHealthTestRepository(
        roomDatabase: KalmakasaRoomDatabase,
        apiService: ApiService,
    ): HealthTestRepository {
        return HealthTestRepositoryImpl(roomDatabase.healthTestDao(), apiService)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(
        roomDatabase: KalmakasaRoomDatabase,
        apiService: MachineLearningService,
    ): MessageRepository {
        return MessageRepositoryImpl(roomDatabase.messageDao(), apiService)
    }

    @Provides
    @Singleton
    fun provideJournalRepository(
        apiService: ApiService,
        mlService: MachineLearningService,
    ): JournalRepository {
        return JournalRepositoryImpl(apiService, mlService)
    }

    @Provides
    @Singleton
    fun provideReservationRepository(apiService: ApiService): ReservationRepository {
        return ReservationRepositoryImpl(apiService)
    }
}
