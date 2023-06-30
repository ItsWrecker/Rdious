package com.wrecker.rdious

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.wrecker.rdious.presantation.FacilityViewModel
import com.wrecker.rdious.presantation.MainScreen
import com.wrecker.rdious.ui.theme.RdiousTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<FacilityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            viewModel.getData()
            RdiousTheme {
                MainScreen(state = state)
            }
        }
    }
}


