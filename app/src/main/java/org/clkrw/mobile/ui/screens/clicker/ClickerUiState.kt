package org.clkrw.mobile.ui.screens.clicker

import org.clkrw.mobile.domain.model.Show

sealed interface ClickerUiState {
    data object Loading : ClickerUiState
    data class Loaded(
        val show: Show,
    ) : ClickerUiState
}
