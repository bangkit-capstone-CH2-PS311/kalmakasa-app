package com.kalmakasa.kalmakasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kalmakasa.kalmakasa.ui.KalmakasaApp
import com.kalmakasa.kalmakasa.ui.theme.KalmakasaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        var uiState: SessionState by mutableStateOf(SessionState.Loading)

        // Get the Session
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sessionState.onEach {
                    uiState = it
                }.collect()
            }
        }

        // Hold the Splash Screen when getting the Session
        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                is SessionState.Loading -> true
                is SessionState.Success -> false
            }
        }

        setContent {
            KalmakasaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KalmakasaApp(uiState)
                }
            }
        }
    }
}
