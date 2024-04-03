package org.clkrw.mobile

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.HiltAndroidApp
import org.clkrw.mobile.domain.auth.AuthService

@HiltAndroidApp
class ClickerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // TODO: AAAAAAAAAAAAAAAAAAAAAA
        AuthService().setAuthToken(this, null)

    }
}