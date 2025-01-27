package com.example.coffeehouse.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

interface UserInfoStorage {
    suspend fun storeUserName(userName: String)
    suspend fun getUserName(): String?
}

class DataStoreUserInfoStorage(
    private val context: Context
) : UserInfoStorage {
    private val userNameKey = stringPreferencesKey(SHARED_PREFS_USER_NAME)

    private suspend fun prefs() = context.dataStore.data.first()

    override suspend fun getUserName() = prefs()[userNameKey]

    override suspend fun storeUserName(userName: String) {
        context.dataStore.edit { prefs ->
            prefs[userNameKey] = userName
        }
    }

    companion object {
        private const val USER_SHARED_PREFS = "com.example.coffeehouse.user.preferences"
        private const val SHARED_PREFS_USER_NAME = "userName"

        private val Context.dataStore by preferencesDataStore(name = USER_SHARED_PREFS)
    }
}
