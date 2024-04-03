package org.clkrw.mobile.data.auth

import android.content.Context
import androidx.core.content.ContextCompat.getString
import org.clkrw.mobile.R
import org.clkrw.mobile.domain.auth.AuthService

class AuthServiceImpl(
    private val context: Context
) : AuthService {
    override fun getAuthToken(): String? {
        val param = getString(context, R.string.sp_access_token)
        val sharedPref = context.getSharedPreferences(param, Context.MODE_PRIVATE)
        return sharedPref.getString(param, null)
    }

    override fun setAuthToken(token: String?) {
        val param = getString(context, R.string.sp_access_token)
        val sharedPref = context.getSharedPreferences(param, Context.MODE_PRIVATE)
        sharedPref.edit().putString(param, token).apply()
    }
}
