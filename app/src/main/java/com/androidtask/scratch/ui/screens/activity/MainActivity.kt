package com.androidtask.scratch.ui.screens.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.androidtask.scratch.navigation.NavigationStack
import com.androidtask.scratch.ui.theme.ScratchCardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContent {
            ScratchCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavigationStack()
                }
            }
        }
    }
}
