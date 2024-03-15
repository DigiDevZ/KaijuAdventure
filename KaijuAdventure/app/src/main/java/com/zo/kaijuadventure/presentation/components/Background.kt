package com.zo.kaijuadventure.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zo.kaijuadventure.R
import com.zo.kaijuadventure.presentation.play_screen.KaijuEvents
import com.zo.kaijuadventure.presentation.play_screen.SceneEvents
import com.zo.kaijuadventure.ui.theme.DarkAtomicBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun Background(
    shakeScreen: Boolean,
    kaijuEvent: SceneEvents.KaijuEvent?,
    onKaijuIntroduced: () -> Unit,
    onScreenShakeFinished: () -> Unit,
) {
    //region Display Kaiju
    val displayKaiju = remember { mutableStateOf(false) }
    val displayKaijuSpecial = remember {
        mutableStateOf(false)
    }

    val introduceKaiju = remember {
        mutableStateOf(false)
    }
    val jumpKaiju = remember {
        mutableStateOf(false)
    }
    val stompKaiju = remember {
        mutableStateOf(false)
    }
    val exitKaiju = remember {
        mutableStateOf(false)
    }

    //Diffuse the kaijuEvent
    LaunchedEffect(key1 = kaijuEvent) {
        kaijuEvent?.let {
            when (it.event) {
                KaijuEvents.Introduce -> introduceKaiju.value = true
                KaijuEvents.Jump -> jumpKaiju.value = true
                KaijuEvents.Stomp -> stompKaiju.value = true
                KaijuEvents.Exit -> exitKaiju.value = true
                KaijuEvents.DisplaySpecial -> displayKaijuSpecial.value = true
            }
        }
    }

    LaunchedEffect(key1 = introduceKaiju.value) {
        if (introduceKaiju.value) {
            displayKaiju.value = true
            onKaijuIntroduced()
        }
    }
    //endregion

    //region Kaiju Animations
    val kaijuTranslationY = remember {
        Animatable(initialValue = 0f)
    }

    val kaijuTranslationX = remember {
        Animatable(initialValue = 0f)
    }

    val animationPlaying = remember { mutableStateOf(false) }

    //region Jump animation
    LaunchedEffect(key1 = jumpKaiju.value) {
        if (jumpKaiju.value) {
            animationPlaying.value = true
            launch {
                repeat(2) {
                    kaijuTranslationY.animateTo(
                        targetValue = 0f,
                        animationSpec = jumpAnimation
                    )
                    delay(200)
                }
            }
            kaijuTranslationY.snapTo(0f)
            animationPlaying.value = false
            jumpKaiju.value = false
        }
    }
    //endregion

    //region Stomp animation
    LaunchedEffect(key1 = stompKaiju.value) {
        if (stompKaiju.value) {
            animationPlaying.value = true
            launch {
                kaijuTranslationY.animateTo(
                    targetValue = 0f,
                    animationSpec = stompAnimation
                )
            }
            kaijuTranslationY.snapTo(0f)
            animationPlaying.value = false
            stompKaiju.value = false
        }
    }
    //endregion

    //region Freakout animation
    LaunchedEffect(key1 = animationPlaying.value) {
        //We want to roll dice every 2.5 seconds
        while (!animationPlaying.value) {
            delay(2000)

            val randomInt = Random.nextInt(1, 100)
            if (randomInt in 1..15) {
                animationPlaying.value = true
                launch {
                    kaijuTranslationY.animateTo(
                        targetValue = 0f,
                        animationSpec = freakoutYAnimation
                    )
                }
                launch {
                    kaijuTranslationX.animateTo(
                        targetValue = 0f,
                        animationSpec = freakoutXAnimation
                    )
                }
                kaijuTranslationY.snapTo(0f)
                animationPlaying.value = false
            }
        }
    }
    //endregion

    //region KaijuExit Animation
    //Animation states
    val initialSize = 1f
    val initialY = 0f
    val initialAlpha = 1f

    val targetSize = 0.5f
    val targetY = -100f
    val targetAlpha = 0f

    //Animation controllers
    val imageSize = remember {
        Animatable(initialSize)
    }
    val imageY = remember {
        Animatable(initialY)
    }
    val imageAlpha = remember {
        Animatable(initialAlpha)
    }
    val customTweenSpec = tween<Float>(durationMillis = 4000)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = exitKaiju.value) {
        if (exitKaiju.value) {
            animationPlaying.value = true
            coroutineScope.launch {
                imageSize.animateTo(targetValue = targetSize, animationSpec = customTweenSpec)
            }
            coroutineScope.launch {
                imageY.animateTo(targetValue = targetY, animationSpec = customTweenSpec)
            }
            coroutineScope.launch {
                imageAlpha.animateTo(targetValue = targetAlpha, animationSpec = customTweenSpec)
            }
            animationPlaying.value = false
            exitKaiju.value = false
        }
    }
    //endregion

    //endregion

    //region Screen shake anim
    val backgroundTranslationX = remember {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(key1 = shakeScreen) {
        if (shakeScreen) {
            val shakeAnimation: AnimationSpec<Float> = keyframes {
                durationMillis = 500
                -20f at 100
                20f at 200
                -20f at 300
                20f at 400
                0f at 500
            }

            launch {
                repeat(3) {
                    backgroundTranslationX.animateTo(
                        targetValue = 0f,
                        animationSpec = shakeAnimation
                    )
                    delay(500)
                }.also { onScreenShakeFinished() }
            }

            backgroundTranslationX.snapTo(0f)
        }
    }
    //endregion

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = backgroundTranslationX.value.roundToInt().dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.background_art),
            contentDescription = "Night time City",
            contentScale = ContentScale.FillBounds
        )

        AnimatedVisibility(visible = displayKaiju.value) {
            BoxWithConstraints {
                val ambientColor = if (displayKaijuSpecial.value) DarkAtomicBlue else Color.Transparent
                val spotColor = if (displayKaijuSpecial.value) DarkAtomicBlue else Color.Transparent
                val elevation = 75.dp
                Image(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = imageSize.value
                            scaleY = imageSize.value
                            translationY = imageY.value.dp.toPx()
                        }
                        .alpha(imageAlpha.value)
                        .fillMaxSize()
                        .offset(
                            x = kaijuTranslationX.value.roundToInt().dp,
                            y = kaijuTranslationY.value.roundToInt().dp
                        )
                        .shadow(
                            elevation,
                            shape = CircleShape,
                            clip = false,
                            ambientColor = ambientColor,
                            spotColor = spotColor
                        ),
                    painter = painterResource(id = if (displayKaijuSpecial.value) R.drawable.godzilla_charge
                    else R.drawable.godzilla_standard),
                    contentDescription = "Godzilla",
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        Background(
            false,
            SceneEvents.KaijuEvent(event = KaijuEvents.Introduce),
            onKaijuIntroduced = {},
        ) {}
    }
}

val jumpAnimation: AnimationSpec<Float> = keyframes {
    durationMillis = 500
    -30f at 100
    0f at 200
    -20f at 300
    0f at 400
    -40f at 500
}

val stompAnimation: AnimationSpec<Float> = keyframes {
    durationMillis = 1000
    -30f at 200
    -60f at 400
    -80f at 600
    -100f at 800
    0f at 1000
}

val freakoutYAnimation: AnimationSpec<Float> = keyframes {
    durationMillis = 1000
    -30f at 200
    30f at 300
    -60f at 400
    30f at 500
    -80f at 600
    -100f at 800
    0f at 1000
}

val freakoutXAnimation: AnimationSpec<Float> = keyframes {
    durationMillis = 1000
    -20f at 200
    20f at 250
    -20f at 300
    20f at 350
    -20f at 400
    20f at 450
    -20f at 500
    20f at 550
    -20f at 600
    20f at 650
    -20f at 700
    20f at 750
    -20f at 800
    20f at 850
    -20f at 900
    20f at 950
    0f at 1000
}

