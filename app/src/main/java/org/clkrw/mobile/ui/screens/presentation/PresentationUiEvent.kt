package org.clkrw.mobile.ui.screens.presentation

import androidx.compose.runtime.MutableState
import org.clkrw.mobile.ui.screens.OpState


sealed interface PresentationUiEvent {
    data class GrantAccess(
        val emailInputState: MutableState<String>,
        val grantAccessOpState: MutableState<OpState>,
    ) : PresentationUiEvent

    data class RevokeAccess(
        val userEmail: String,
        val revokeAccessOpState: MutableState<OpState>,
    ) : PresentationUiEvent
}