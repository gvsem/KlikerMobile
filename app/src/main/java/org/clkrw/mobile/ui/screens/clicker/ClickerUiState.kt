package org.clkrw.mobile.ui.screens.clicker

import org.clkrw.mobile.domain.model.Show

sealed interface ClickerUiState {

    data object Loading : ClickerUiState

    data class Loaded(
        val show: Show,
    ) : ClickerUiState
}

data class ClickerCountsUiState(
    val slideNo: Int? = null,
    val totalSlides: Int? = null,
    val displaysCount: Int? = null,
) {
    fun invalidate(): ClickerCountsUiState =
        copy(slideNo = null)
}
