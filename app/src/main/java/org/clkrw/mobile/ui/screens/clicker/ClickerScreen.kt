package org.clkrw.mobile.ui.screens.clicker

import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.clkrw.mobile.R
import org.clkrw.mobile.ui.theme.Typography

@Composable
fun ClickerScreen(
    viewModel: ClickerViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state

    if (state is ClickerUiState.Loaded) {
        val focusRequester = remember { FocusRequester() }
        var hasFocus by remember { mutableStateOf(false) }

        ClickerView(
            state,
            modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    hasFocus = it.hasFocus
                }
                .focusable()
                .onKeyEvent { keyEvent ->
                    if (keyEvent.type == KeyEventType.KeyDown) {
                        when (keyEvent.key) {
                            Key.VolumeDown -> viewModel.onEvent(ClickerUiEvent.PrevSlide)
                            Key.VolumeUp -> viewModel.onEvent(ClickerUiEvent.NextSlide)
                        }
                    }
                    true
                }
        )

        if (!hasFocus) {
            Log.d("FOCUS", hasFocus.toString())
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }
}

@Composable
fun ClickerView(state: ClickerUiState.Loaded, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        Text(
            state.show.presentation.title,
            style = Typography.displayLarge
        )
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "${state.show.currentSlideNo}/${state.show.maxSlidesCount}",
                style = Typography.displaySmall
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "CNT",
                    style = Typography.displaySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(R.string.back_button),
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}
