package id.codeid.pokemon.data.local

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_NAME = "key_name"
        private const val KEY_ADDRESS = "key_address"
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }

    fun saveUserSession(name: String, address: String) {
        prefs.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_ADDRESS, address)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun getUserName(): String? = prefs.getString(KEY_NAME, null)

    fun getUserAddress(): String? = prefs.getString(KEY_ADDRESS, null)

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}