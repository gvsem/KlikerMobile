package org.clkrw.mobile.ui.screens.gallery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.clkrw.mobile.domain.repository.ShowRepository
import org.clkrw.mobile.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val presentationRepository: ShowRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    var state by mutableStateOf<GalleryUiState>(GalleryUiState.Loading)

    init {
        viewModelScope.launch {
            val shows = presentationRepository.getShows()
            val user = userRepository.getCurrentUser()
            state = GalleryUiState.Loaded(shows, user)
        }
    }
}
