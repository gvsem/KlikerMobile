package org.clkrw.mobile.ui.screens.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.clkrw.mobile.domain.repository.ResponseCode
import org.clkrw.mobile.domain.repository.RolesRepository
import org.clkrw.mobile.domain.repository.ShowRepository
import org.clkrw.mobile.ui.screens.OpState
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
        loadShow()
    }


    private fun loadShow() {
        viewModelScope.launch {
            val show = showRepository.getShow(showId)
            state = PresentationUiState.Loaded(show)
        }
    }


    fun onEvent(event: PresentationUiEvent) {
        when (event) {
            is PresentationUiEvent.GrantAccess -> {
                viewModelScope.launch {
                    event.grantAccessOpState.value = OpState.PROCESSING

                    val result = rolesRepository.grantAccess(showId, event.emailInputState.value)
                    when (result) {
                        ResponseCode.CREATED, ResponseCode.REPEATED -> {
                            event.emailInputState.value = ""
                            event.grantAccessOpState.value = OpState.DONE
                            loadShow()
                        }

                        ResponseCode.NOT_FOUND, ResponseCode.UNKNOWN -> {
                            event.grantAccessOpState.value = OpState.ERROR
                        }
                    }
                }
            }

            is PresentationUiEvent.RevokeAccess -> {
                viewModelScope.launch {
                    event.revokeAccessOpState.value = OpState.PROCESSING

                    val result = rolesRepository.revokeAccess(showId, event.userEmail)
                    when (result) {
                        ResponseCode.CREATED, ResponseCode.REPEATED -> {
                            event.revokeAccessOpState.value = OpState.DONE
                            loadShow()
                        }

                        ResponseCode.NOT_FOUND, ResponseCode.UNKNOWN -> {
                            event.revokeAccessOpState.value = OpState.ERROR
                        }
                    }
                }
            }
        }
    }
}
