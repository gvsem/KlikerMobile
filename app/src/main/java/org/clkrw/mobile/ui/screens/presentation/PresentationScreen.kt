package org.clkrw.mobile.ui.screens.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.clkrw.mobile.R
import org.clkrw.mobile.domain.model.Grant
import org.clkrw.mobile.ui.screens.OpState
import org.clkrw.mobile.ui.theme.Typography

@Composable
fun PresentationScreen(
    viewModel: PresentationViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state

    if (state is PresentationUiState.Loaded) {
        PresentationView(state, modifier, viewModel)
    }
}


@Composable
fun PresentationView(
    state: PresentationUiState.Loaded,
    modifier: Modifier = Modifier,
    viewModel: PresentationViewModel,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        Text(
            text = state.show.presentation.title,
            style = Typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxHeight()) {
            GrantsView(state, viewModel = viewModel)
            EmailInputView(
                modifier = Modifier.align(Alignment.BottomStart),
                viewModel = viewModel,
            )
        }
    }
}


@Composable
fun GrantsView(
    state: PresentationUiState.Loaded,
    modifier: Modifier = Modifier,
    viewModel: PresentationViewModel,
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
    ) {
        items(state.show.grants) { grant ->
            GrantView(
                grant = grant,
                ownerId = state.show.owner.id,
                viewModel = viewModel,
            )
        }
        item { Spacer(modifier = Modifier.height(64.dp)) }
    }
}


@Composable
fun GrantView(
    grant: Grant,
    ownerId: String,
    viewModel: PresentationViewModel,
) {
    val user = grant.toWhomGranted
    val revokeAccessOpState = remember { mutableStateOf(OpState.DONE) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        shape = RoundedCornerShape(size = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.white))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = user.pictureURL,
                contentDescription = stringResource(id = R.string.preview),
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 22.sp,
                        letterSpacing = 0.5.sp,
                        color = colorResource(id = R.color.black),
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = user.email,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        letterSpacing = 0.5.sp,
                        color = colorResource(id = R.color.black),
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (user.id != ownerId) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(
                            PresentationUiEvent.RevokeAccess(
                                userEmail = user.email,
                                revokeAccessOpState = revokeAccessOpState
                            )
                        )
                    },
                    modifier = Modifier.size(32.dp),
                    enabled = (revokeAccessOpState.value != OpState.PROCESSING)
                ) {
                    val iconModifier = Modifier.size(24.dp)
                    if (revokeAccessOpState.value == OpState.PROCESSING) {
                        CircularProgressIndicator(modifier = iconModifier)
                    } else {
                        Icon(
                            modifier = iconModifier,
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.revoke_access),
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun EmailInputView(
    viewModel: PresentationViewModel,
    modifier: Modifier = Modifier,
) {
    val emailInputState = remember { mutableStateOf("") }
    val grantAccessOpState = remember { mutableStateOf(OpState.DONE) }

    Card(
        shape = RoundedCornerShape(size = 8.dp),
        modifier = modifier
            .padding(8.dp)
            .height(64.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(
                        id = if (grantAccessOpState.value == OpState.ERROR)
                            R.color.error
                        else
                            R.color.white
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val focusManager = LocalFocusManager.current
            TextField(
                modifier = Modifier.weight(1f),
                value = emailInputState.value,
                onValueChange = { newValue ->
                    emailInputState.value = newValue
                    grantAccessOpState.value = OpState.DONE
                },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email,
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.email_input_hint),
                        color = colorResource(id = R.color.black).copy(alpha = .5f)
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.brand),
                    disabledTextColor = colorResource(id = R.color.gray),
                    focusedTextColor = colorResource(id = R.color.black),
                    unfocusedTextColor = colorResource(id = R.color.black),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )

            Spacer(modifier = Modifier.width(16.dp))

            val buttonColor = colorResource(id = R.color.brand)
            val buttonModifier = Modifier.size(40.dp)
            if (grantAccessOpState.value == OpState.PROCESSING) {
                CircularProgressIndicator(
                    color = buttonColor,
                    modifier = buttonModifier,
                )
            } else {
                FloatingActionButton(
                    containerColor = buttonColor,
                    onClick = {
                        if (emailInputState.value.isBlank())
                            return@FloatingActionButton

                        emailInputState.value = emailInputState.value.trim()
                        viewModel.onEvent(
                            PresentationUiEvent.GrantAccess(
                                emailInputState,
                                grantAccessOpState,
                            )
                        )
                        focusManager.clearFocus()
                    },
                    shape = CircleShape,
                    modifier = buttonModifier,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.grant_access),
                        tint = colorResource(id = R.color.white),
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
