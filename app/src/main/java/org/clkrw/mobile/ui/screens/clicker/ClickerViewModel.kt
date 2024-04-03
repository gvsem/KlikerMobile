package org.clkrw.mobile.ui.screens.clicker

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.clkrw.mobile.domain.repository.PresentationRepository
import org.clkrw.mobile.domain.repository.ShowingRepository
import javax.inject.Inject

@HiltViewModel
class ClickerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val presentationRepository: PresentationRepository,
    private val showingRepository: ShowingRepository,
) : ViewModel() {
    private val presentationId: Int = checkNotNull(savedStateHandle.get<String>("presentationId")).toInt()

    // Fetch the relevant user information from the data layer,
    // ie. userInfoRepository, based on the passed userId argument
    val state: StateFlow<ClickerUiState> = showingRepository
        .getShowing(presentationId)
        .map {
            val presentation = presentationRepository.getPresentationById(it.presentationId)
            ClickerUiState.Loaded(presentation, it.slideNumber, it.displaysCount)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ClickerUiState.Loading
        )


    fun onEvent(event: ClickerUiEvent) {
        when (event) {
            ClickerUiEvent.NextSlide -> {
                viewModelScope.launch {
                    val currentState = state.value
                    if (currentState is ClickerUiState.Loaded) {
                        showingRepository.nextSlide(presentationId)
                    }
                }
            }

            ClickerUiEvent.PrevSlide -> {
                viewModelScope.launch {
                    val currentState = state.value
                    if (currentState is ClickerUiState.Loaded) {
                        showingRepository.prevSlide(presentationId)
                    }
                }
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
