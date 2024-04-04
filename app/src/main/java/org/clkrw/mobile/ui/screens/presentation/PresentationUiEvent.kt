package org.clkrw.mobile.ui.screens.presentation


sealed interface PresentationUiEvent {
    data class GrantAccess(val userEmail: String) : PresentationUiEvent
    data class RevokeAccess(val userEmail: String) : PresentationUiEvent
}