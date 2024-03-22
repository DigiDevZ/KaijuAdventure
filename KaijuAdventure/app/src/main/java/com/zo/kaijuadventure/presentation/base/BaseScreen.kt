package com.zo.kaijuadventure.presentation.base

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.zo.kaijuadventure.data.model.ErrorHandling
import com.zo.kaijuadventure.presentation.components.ErrorDialog
import com.zo.kaijuadventure.presentation.components.LargeLoading
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    loadingTrigger: Boolean,
    errorHandling: ErrorHandling?,
    content: @Composable (PaddingValues) -> Unit
) {
    var displayLoading by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = loadingTrigger) {
        displayLoading = if (loadingTrigger) {
            true
        } else {
            delay(250)
            false
        }
    }

    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            content(it)

            AnimatedVisibility(visible = displayLoading) {
                LargeLoading(fullscreen = displayLoading)
            }
        }

        errorHandling?.let { safeErrorHandling ->
            ErrorDialog(errorHandling = safeErrorHandling)
        }
    }
}