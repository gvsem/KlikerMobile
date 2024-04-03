package org.clkrw.mobile.ui.screens.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.clkrw.mobile.domain.auth.AuthService
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authService: AuthService,
) : ViewModel() {

    fun goAuth(context: Context) {
        val url = "https://clkr.me/auth/google/android"
        val i = Intent(Intent.ACTION_VIEW)
        i.setData(Uri.parse(url))
        context.startActivity(i)
    }
}