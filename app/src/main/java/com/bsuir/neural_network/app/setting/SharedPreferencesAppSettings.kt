package com.bsuir.neural_network.app.setting

import android.content.Context

class SharedPreferencesAppSettings(
    appContext: Context
) : AppSettings {

    private val sharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    // Token

    override fun setCurrentToken(token: String?) {
        val editor = sharedPreferences.edit()
        if (token == null)
            editor.remove(PREF_CURRENT_ACCOUNT_TOKEN)
        else
            editor.putString(PREF_CURRENT_ACCOUNT_TOKEN, token)
        editor.apply()
    }

    override fun getCurrentToken(): String? =
        sharedPreferences.getString(PREF_CURRENT_ACCOUNT_TOKEN, null)

    // Username

    override fun getCurrentUsername(): String? =
        sharedPreferences.getString(PREF_CURRENT_ACCOUNT_USERNAME, null)


    override fun setCurrentUsername(username: String?) {
        val editor = sharedPreferences.edit()
        if (username == null)
            editor.remove(PREF_CURRENT_ACCOUNT_USERNAME)
        else
            editor.putString(PREF_CURRENT_ACCOUNT_USERNAME, username)
        editor.apply()
    }

    // Role

    override fun setCurrentRole(role: String?) {
        val editor = sharedPreferences.edit()
        if (role == null)
            editor.remove(PREF_CURRENT_ACCOUNT_ROLE)
        else
            editor.putString(PREF_CURRENT_ACCOUNT_ROLE, role)
        editor.apply()
    }

    override fun getCurrentRole(): String? = sharedPreferences.getString(PREF_CURRENT_ACCOUNT_ROLE, null)


    companion object {
        private const val PREF_CURRENT_ACCOUNT_TOKEN = "currentToken"
        private const val PREF_CURRENT_ACCOUNT_USERNAME = "currentUsername"
        private const val PREF_CURRENT_ACCOUNT_ROLE = "currentRole"
    }

}