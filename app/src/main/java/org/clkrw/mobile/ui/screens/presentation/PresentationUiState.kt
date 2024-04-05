package org.clkrw.mobile.ui.screens.presentation

import org.clkrw.mobile.domain.model.Show


sealed interface PresentationUiState {
    data object Loading : PresentationUiState
    data class Loaded(
        val show: Show,
    ) : PresentationUiState
}
