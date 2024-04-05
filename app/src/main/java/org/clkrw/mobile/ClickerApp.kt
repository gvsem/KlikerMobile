package org.clkrw.mobile

import androidx.annotation.StringRes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.clkrw.mobile.ui.screens.clicker.ClickerScreen
import org.clkrw.mobile.ui.screens.gallery.GalleryScreen
import org.clkrw.mobile.ui.screens.login.LoginScreen
import org.clkrw.mobile.ui.screens.presentation.PresentationScreen

@Composable
fun ClickerApp() {
    val navController = rememberNavController()

    var titles = remember {
        mutableStateListOf("")
    }

    Scaffold(
        topBar = {
            ClickerAppBar(
                currentScreenTitle = titles.last(),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    titles.removeLast()
                    navController.navigateUp()
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.name,
        ) {
            composable(route = Screen.Login.name) {
                titles.add(stringResource(id = Screen.Gallery.title))
                LoginScreen(
                    viewModel = hiltViewModel(),
                    navigateCallback = {
                        navController.navigate(Screen.Gallery.name) {
                            popUpTo(navController.graph.id) { inclusive = true }
//                         titles.add(stringResource(id = Screen.Gallery.title))
//                         navController.navigate(Screen.Gallery.name) {
//                             titles.removeLast()
//                             popUpTo(Screen.Login.name) {
//                                 inclusive = true
//                             }
                        }
                    },
                    modifier = Modifier.padding(paddingValues),
                )
            }
            composable(route = Screen.Gallery.name) {
                GalleryScreen(
                    viewModel = hiltViewModel(),
                    modifier = Modifier.padding(paddingValues),
                    onClickEditPresentation = { show ->
                        titles.add("${show.presentation.title} access rights")
                        navController.navigate("${Screen.Presentation.name}/${show.id}")
                    },
                    onClickStartPresentation = { show ->
                        titles.add("${show.presentation.title} clicker")
                        navController.navigate("${Screen.Clicker.name}/${show.id}")
                    }
                )
            }
            composable(route = "${Screen.Presentation.name}/{showId}") {
                PresentationScreen(
                    viewModel = hiltViewModel(),
                    modifier = Modifier.padding(paddingValues)
                )
            }
            composable(route = "${Screen.Clicker.name}/{showId}") {
                ClickerScreen(
                    viewModel = hiltViewModel(),
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

enum class Screen(@StringRes val title: Int) {
    Login(title = R.string.app_name),
    Gallery(title = R.string.gallery),
    Presentation(title = R.string.presentation),
    Clicker(title = R.string.clicker),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClickerAppBar(
    currentScreenTitle: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scroll = rememberScrollState(0)

    CenterAlignedTopAppBar(
        title = {
            Text(
                currentScreenTitle,
                modifier = Modifier.horizontalScroll(scroll),
                maxLines = 1
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
    )
}