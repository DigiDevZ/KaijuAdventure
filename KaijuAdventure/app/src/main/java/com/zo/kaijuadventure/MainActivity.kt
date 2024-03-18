package com.zo.kaijuadventure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.zo.kaijuadventure.data.StoryChoice
import com.zo.kaijuadventure.data.StoryNode
import com.zo.kaijuadventure.presentation.play_screen.PlayScreen
import com.zo.kaijuadventure.presentation.play_screen.PlayScreenViewModel
import com.zo.kaijuadventure.ui.theme.KaijuAdventureTheme
import com.zo.kaijuadventure.util.baseLog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playScreenViewModel = PlayScreenViewModel()

        setContent {
            KaijuAdventureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlayScreen(viewModel = playScreenViewModel)
                }
            }
        }
    }
}


