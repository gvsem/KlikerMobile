package org.clkrw.mobile.ui.screens.clicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.clkrw.mobile.domain.bus.ShowSseBus
import org.clkrw.mobile.domain.repository.ShowRepository
import javax.inject.Inject

@HiltViewModel
class ClickerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val showingRepository: ShowRepository,
    private val showSseBus: ShowSseBus,
) : ViewModel() {
    private val showId: String = checkNotNull(savedStateHandle["showId"])

    var state: ClickerUiState by mutableStateOf(ClickerUiState.Loading)
        private set

    var countsState: ClickerCountsUiState by mutableStateOf(ClickerCountsUiState())
        private set

    init {
        loadState()
    }

    private fun loadState() {
        viewModelScope.launch {
            val show = showingRepository.getShow(showId)
            countsState = countsState.copy(
                slideNo = show.currentSlideNo,
                totalSlides = show.maxSlidesCount
            )
            state = ClickerUiState.Loaded(show)
        }
    }


    init {
        showSseBus
            .getClickerEvents(showId)
            .onEach {
                countsState =
                    ClickerCountsUiState(it.slideNo, it.totalSlides, it.displaysOnline)
            }
            .launchIn(
                viewModelScope,
            )
    }


    fun onEvent(event: ClickerUiEvent) {
        when (event) {
            ClickerUiEvent.NextSlide -> {
                viewModelScope.launch {
                    val currentState = state
                    if (currentState is ClickerUiState.Loaded) {
                        countsState = countsState.invalidate()
                        showingRepository.nextSlide(showId)
                    }
                }
            }

            ClickerUiEvent.PrevSlide -> {
                viewModelScope.launch {
                    val currentState = state
                    if (currentState is ClickerUiState.Loaded) {
                        countsState = countsState.invalidate()
                        showingRepository.prevSlide(showId)
                    }
                }
            }
        }
    }
}
