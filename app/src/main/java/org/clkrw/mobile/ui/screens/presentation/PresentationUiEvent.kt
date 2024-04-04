package org.clkrw.mobile.ui.screens.presentation

import androidx.compose.runtime.MutableState


sealed interface PresentationUiEvent {
    data class GrantAccess(
        val emailInputState: MutableState<String>,
        val grantAccessErrorState: MutableState<Boolean>
    ) : PresentationUiEvent

    data class RevokeAccess(val userEmail: String) : PresentationUiEvent
}