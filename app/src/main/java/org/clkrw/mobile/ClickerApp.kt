package org.clkrw.mobile

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.clkrw.mobile.ui.screens.clicker.ClickerScreen
import org.clkrw.mobile.ui.screens.gallery.GalleryScreen
import org.clkrw.mobile.ui.screens.login.LoginScreen
import org.clkrw.mobile.ui.screens.presentation.PresentationScreen

@Composable
fun ClickerApp() {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route?.substringBefore('/') ?: Screen.Gallery.name
    )

    Scaffold(
        topBar = {
            ClickerAppBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Gallery.name
        ) {
            composable(route = Screen.Login.name) {
                LoginScreen(
                    modifier = Modifier.padding(paddingValues)
                )

            }
            composable(route = Screen.Gallery.name) {
                GalleryScreen(
                    viewModel = hiltViewModel(),
                    modifier = Modifier.padding(paddingValues),
                    onClickEditPresentation = { show ->
                        navController.navigate("${Screen.Presentation.name}/${show.id}")
                    },
                    onClickStartPresentation = { show -> navController.navigate("${Screen.Clicker.name}/${show.id}") }
                )

            }
            composable(route = "${Screen.Presentation.name}/{presentationId}") {
                PresentationScreen(
                    modifier = Modifier.padding(paddingValues)
                )

            }
            composable(route = "${Screen.Clicker.name}/{presentationId}") { backStackEntry ->
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
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreenTitle)) },
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
        }
    )
}