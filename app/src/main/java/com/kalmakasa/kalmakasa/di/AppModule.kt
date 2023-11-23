package com.kalmakasa.kalmakasa.di

import android.content.Context
import com.kalmakasa.kalmakasa.data.UserPreferences
import com.kalmakasa.kalmakasa.data.dataStore
import com.kalmakasa.kalmakasa.data.repository.FakeUserRepository
import com.kalmakasa.kalmakasa.data.repository.UserRepository
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
    fun provideUserRepository(pref: UserPreferences): UserRepository {
        return FakeUserRepository(pref)
    }
}