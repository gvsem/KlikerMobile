package org.clkrw.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.clkrw.mobile.di.AppModule
import org.clkrw.mobile.ui.theme.KlikerMobileTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authService = AppModule.provideAuthService(applicationContext)

        if (authService.getAuthToken() == null) {
            if ("auth" == intent?.data?.host) {
                val jwt = intent?.data?.query
                authService.setAuthToken(jwt)
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