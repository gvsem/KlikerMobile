package org.clkrw.mobile.ui.screens.clicker

import org.clkrw.mobile.domain.model.Presentation

sealed interface ClickerUiState {
    data object Loading : ClickerUiState
    data class Loaded(
        val presentation: Presentation,
        val slideNumber: Int,
        val displaysCount: Int,
    ) : ClickerUiState
}
