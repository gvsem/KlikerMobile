package org.clkrw.mobile.domain.auth

import android.content.Context
import androidx.core.content.ContextCompat.getString
import org.clkrw.mobile.R

class AuthService {

    fun getAuthToken(context : Context) : String? {
        val param = getString(context, R.string.sp_access_token)
        val sharedPref = context.getSharedPreferences(param, Context.MODE_PRIVATE)
        return sharedPref.getString(param, null)
    }

    fun setAuthToken(context : Context, token : String?) {
        val param = getString(context, R.string.sp_access_token)
        val sharedPref = context.getSharedPreferences(param, Context.MODE_PRIVATE)
        sharedPref.edit().putString(param, token).apply()
    }

}