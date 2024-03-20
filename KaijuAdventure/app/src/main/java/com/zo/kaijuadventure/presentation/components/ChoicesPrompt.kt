package com.zo.kaijuadventure.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zo.kaijuadventure.R
import com.zo.kaijuadventure.data.model.StoryChoice

@Composable
fun ChoicesPrompt(
    choices: List<StoryChoice>,
    onChoiceClick: (StoryChoice) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(
                Color.DarkGray.copy(alpha = 0.95f),
                shape = RoundedCornerShape(corner = CornerSize(20.dp))
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedWaveText(text = stringResource(R.string.user_prompt_message))
        
        Spacer(modifier = Modifier.height(36.dp))
        choices.forEachIndexed { index, it ->
            Text(
                modifier = Modifier.clickable {
                    onChoiceClick(choices[index])
                },
                text = it.choiceText,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier.height(
                    if (index == choices.size - 1) 36.dp else 30.dp
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewChoicesPrompt() {
    Box {
        ChoicesPrompt(
            choices = listOf()
        ) { }
    }
}