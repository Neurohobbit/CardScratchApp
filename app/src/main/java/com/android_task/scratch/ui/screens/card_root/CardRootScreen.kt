package com.android_task.scratch.ui.screens.card_root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.android_task.scratch.R
import com.android_task.scratch.navigation.Screen
import com.android_task.scratch.ui.base.CardView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardRootScreen(navController: NavController) {

    val viewModel = hiltViewModel<CardRootViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.card_scratch_app)) },
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            CardView(
                id = uiState.cardUUID,
                isActivated = uiState.activated
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(start = 8.dp, end = 8.dp),
                onClick = {
                    navController.navigate(Screen.Scratch.route)
                },
                enabled = uiState.cardUUID == null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Blue.copy(alpha = 0.3f),
                    disabledContentColor = Color.White.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    text = stringResource(R.string.go_to_scratching),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(start = 8.dp, end = 8.dp),
                onClick = {
                    navController.navigate(Screen.Activation.route)
                },
                enabled = uiState.cardUUID != null && !uiState.activated,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Blue.copy(alpha = 0.3f),
                    disabledContentColor = Color.White.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    text = stringResource(R.string.go_to_activation),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
