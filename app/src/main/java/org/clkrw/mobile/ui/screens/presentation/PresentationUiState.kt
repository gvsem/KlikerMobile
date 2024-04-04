package org.clkrw.mobile.ui.screens.presentation

import org.clkrw.mobile.domain.model.Grant
import org.clkrw.mobile.domain.model.Show
import org.clkrw.mobile.ui.screens.gallery.GalleryUiState


sealed interface PresentationUiState {
    data object Loading : PresentationUiState
    data class Loaded(
        val show: Show,
    ) : PresentationUiState
}
