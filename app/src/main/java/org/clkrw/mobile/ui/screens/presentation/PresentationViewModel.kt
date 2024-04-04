package org.clkrw.mobile.ui.screens.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.clkrw.mobile.domain.repository.RolesRepository
import org.clkrw.mobile.domain.repository.ShowRepository
import javax.inject.Inject


@HiltViewModel
class PresentationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val showRepository: ShowRepository,
    private val rolesRepository: RolesRepository,
) : ViewModel() {
    private val showId: String = checkNotNull(savedStateHandle["showId"])
    var state by mutableStateOf<PresentationUiState>(PresentationUiState.Loading)


    init {
        viewModelScope.launch {
            val show = showRepository.getShow(showId)
            state = PresentationUiState.Loaded(show)
        }
    }


    fun onEvent(event: PresentationUiEvent) {
        when (event) {
            is PresentationUiEvent.GrantAccess -> {
                viewModelScope.launch {
                    rolesRepository.grantAccess(showId, event.userEmail)
                }
            }

            is PresentationUiEvent.RevokeAccess -> {
                viewModelScope.launch {
                    rolesRepository.revokeAccess(showId, event.userEmail)
                }
            }
        }
    }
}
