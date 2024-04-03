package org.clkrw.mobile.ui.screens.clicker

sealed interface ClickerUiEvent {
    data object NextSlide : ClickerUiEvent
    data object PrevSlide : ClickerUiEvent
}