package com.zo.kaijuadventure.presentation.base

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zo.kaijuadventure.presentation.components.ErrorDialog
import com.zo.kaijuadventure.presentation.components.ErrorHandling
import com.zo.kaijuadventure.presentation.components.LargeLoading

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    loading: Boolean,
    errorHandling: ErrorHandling?,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            content(it)
        }

        errorHandling?.let { safeErrorHandling ->
            ErrorDialog(errorHandling = safeErrorHandling)
        }

        if (loading) {
            LargeLoading(fullscreen = true)
        }
    }
}