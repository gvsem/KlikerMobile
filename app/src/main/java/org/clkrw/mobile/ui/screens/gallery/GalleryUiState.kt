package org.clkrw.mobile.ui.screens.gallery

import org.clkrw.mobile.domain.model.Show
import org.clkrw.mobile.domain.model.User

sealed interface GalleryUiState {
    data object Loading : GalleryUiState
    data class Loaded(
        val presentations: List<Show>,
        val currentUser: User,
    ) : GalleryUiState
}