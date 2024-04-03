package org.clkrw.mobile.ui.screens.gallery

import org.clkrw.mobile.domain.model.Show

sealed interface GalleryUiState {
    data object Loading : GalleryUiState
    data class Loaded(
        val presentations: List<Show>
    ) : GalleryUiState
}