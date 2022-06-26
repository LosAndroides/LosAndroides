package com.losandroides.learn.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.losandroides.learn.ui.theme.LosAndroidesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LosAndroidesTheme {
                ItemListScreen(
                    viewModelState = mainViewModel.viewState.collectAsState().value,
                )
            }
        }
    }
}
