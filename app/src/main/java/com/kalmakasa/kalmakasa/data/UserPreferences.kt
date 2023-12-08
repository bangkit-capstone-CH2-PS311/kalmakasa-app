package com.kalmakasa.kalmakasa.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kalmakasa.kalmakasa.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreferences(private val dataStore: DataStore<Preferences>) {

    suspend fun setSession(user: User) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = user.id
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[ACCESS_TOKEN_KEY] = user.accessToken
            preferences[REFRESH_TOKEN_KEY] = user.accessToken
            preferences[IS_LOGIN_KEY] = true
        }
    }


    fun getSession(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[ID_KEY] ?: "",
                preferences[NAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[ACCESS_TOKEN_KEY] ?: "",
                preferences[REFRESH_TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false,
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val ID_KEY = stringPreferencesKey("id")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access-token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh-token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
    }
}
