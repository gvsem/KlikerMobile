package org.clkrw.mobile.ui.screens.clicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.clkrw.mobile.domain.repository.ShowRepository
import javax.inject.Inject

@HiltViewModel
class ClickerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val showingRepository: ShowRepository,
) : ViewModel() {
    private val showId: String = checkNotNull(savedStateHandle["presentationId"])

    // Fetch the relevant user information from the data layer,
    // ie. userInfoRepository, based on the passed userId argument
    var state: ClickerUiState by mutableStateOf(ClickerUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            val show = showingRepository.getShow(showId)
            state = ClickerUiState.Loaded(show)
        }
    }


    fun onEvent(event: ClickerUiEvent) {
        when (event) {
            ClickerUiEvent.NextSlide -> {
                viewModelScope.launch {
                    val currentState = state
                    if (currentState is ClickerUiState.Loaded) {
                        showingRepository.nextSlide(showId)
                    }
                }
            }

            ClickerUiEvent.PrevSlide -> {
                viewModelScope.launch {
                    val currentState = state
                    if (currentState is ClickerUiState.Loaded) {
                        showingRepository.prevSlide(showId)
                    }
                }
            }
        }
    }
}
