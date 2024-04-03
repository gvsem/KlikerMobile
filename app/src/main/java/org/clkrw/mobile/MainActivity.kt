package org.clkrw.mobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import org.clkrw.mobile.domain.auth.AuthService
import org.clkrw.mobile.ui.theme.KlikerMobileTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val authService: AuthService = AuthService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authService.getAuthToken(this) == null) {
            if ("auth" == intent?.data?.host) {
                val jwt = intent?.data?.query
                authService.setAuthToken(this, jwt)
            }
        }

        setContent {
            KlikerMobileTheme {
                // A surface container using the 'background' color from the theme
                ClickerApp()
            }
        }

    }

}