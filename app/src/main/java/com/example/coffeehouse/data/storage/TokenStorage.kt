package com.example.coffeehouse.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

interface TokenStorage {
    suspend fun getAccessToken(): String?
    suspend fun storeAccessToken(accessToken: String)
    suspend fun removeAccessToken()
}

class DataStoreTokenStorage(
    private val context: Context
) : TokenStorage {
    private val accessTokenKey = stringPreferencesKey(SHARED_PREFS_ACCESS_TOKEN)

    private suspend fun prefs() = context.dataStore.data.first()

    override suspend fun getAccessToken() = prefs()[accessTokenKey]

    override suspend fun storeAccessToken(accessToken: String) {
        context.dataStore.edit { prefs ->
            prefs[accessTokenKey] = accessToken
        }
    }

    override suspend fun removeAccessToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(accessTokenKey)
        }
    }

    companion object {
        private const val AUTH_SHARED_PREFS = "com.example.coffeehouse.auth.preferences"
        private const val SHARED_PREFS_ACCESS_TOKEN = "accessToken"

        private val Context.dataStore by preferencesDataStore(name = AUTH_SHARED_PREFS)
    }
}