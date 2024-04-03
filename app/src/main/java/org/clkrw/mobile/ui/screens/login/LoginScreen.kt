package org.clkrw.mobile.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.clkrw.mobile.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigateCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    if (viewModel.authService.getAuthToken() != null) {
        navigateCallback()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.iphone_clkr),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.heightIn(min = 200.dp, max = 400.dp)
        )
        Text(
            text = "Hi!"
        )
        Button(
            onClick = {
                viewModel.goAuth(context)
            },
            modifier = modifier,
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.brand),
                contentColor = colorResource(R.color.yellow)
            )
        ) {
            Text(text = "Sign in with Google", modifier = Modifier.padding(6.dp))
        }
    }
}
