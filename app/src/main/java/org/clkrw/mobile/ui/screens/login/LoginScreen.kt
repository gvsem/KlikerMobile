package org.clkrw.mobile.ui.screens.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.clkrw.mobile.R
import org.clkrw.mobile.Screen
import org.clkrw.mobile.domain.auth.AuthService

@Composable
fun LoginScreen(
    navigateCallback: () -> Unit,
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    if (AuthService().getAuthToken(LocalContext.current) != null) {
        navigateCallback()
    }


    Column ( modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(
            painter = painterResource(R.drawable.iphone_clkr),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.heightIn(min=200.dp, max = 400.dp)
        )
        Text(
            text="Hi!"
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
