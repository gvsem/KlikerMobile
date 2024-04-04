package org.clkrw.mobile.ui.screens.clicker

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.WindowManager
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.clkrw.mobile.R
import org.clkrw.mobile.domain.model.Presentation
import org.clkrw.mobile.domain.model.ShortUrl
import org.clkrw.mobile.domain.model.Show
import org.clkrw.mobile.domain.model.User
import org.clkrw.mobile.ui.theme.Typography

@Composable
fun KeepScreenOn() {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val window = context.findActivity()?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

@Composable
fun ClickerScreen(
    viewModel: ClickerViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state
    val countsState = viewModel.countsState

    KeepScreenOn()

    if (state is ClickerUiState.Loaded) {
        val focusRequester = remember { FocusRequester() }
        var hasFocus by remember { mutableStateOf(false) }

        ClickerView(
            state,
            countsState,
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
fun ClickerView(
    state: ClickerUiState.Loaded,
    countsState: ClickerCountsUiState,
    modifier: Modifier = Modifier,
) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextLoad(countsState.slideNo?.toString(), style = Typography.displaySmall)
                Text(text = "/", style = Typography.displaySmall)
                TextLoad(countsState.totalSlides?.toString(), style = Typography.displaySmall)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextLoad(
                    text = countsState.displaysCount?.toString(),
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

@Composable
fun TextLoad(text: String?, modifier: Modifier = Modifier, style: TextStyle = TextStyle.Default) {
    if (text == null) {
        CircularProgressIndicator(
            modifier = Modifier.width(with(LocalDensity.current) { style.fontSize.toDp() }),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    } else {
        Text(text = text, style = style, modifier = modifier)
    }
}

@Preview
@Composable
fun ClickerViewPreview() {
    val show = Show(
        "1",
        Presentation(
            "1",
            "1",
            "Alias Analysis & IFDS Refactor",
            "data:image/png;base64, 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"
        ),
        0,
        10,
        "123",
        listOf(
            ShortUrl("123")
        ),
        owner = User(
            id = "1",
            firstName = "Loler",
            lastName = "Laler",
            email = "lol@gmail.com",
            createdAt = "2024-04-03T16:16:26.000Z",
            pictureURL = "",
            googleId = ""
        )
    )
    ClickerView(
        ClickerUiState.Loaded(show),
        ClickerCountsUiState(null, 5, null),
    )
}
