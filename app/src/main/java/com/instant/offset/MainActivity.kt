package com.instant.offset

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(
                modifier = Modifier.systemBarsPadding()
                    .fillMaxSize()
                    .padding(16.dp).verticalScroll(rememberScrollState())
            ) {
                FineTuningExample()
                Spacer(modifier = Modifier.height(40.dp))
                OverlappingExample()
                Spacer(modifier = Modifier.height(40.dp))
                AnimateXOffsetExample()
                Spacer(modifier = Modifier.height(40.dp))
                AnimateYOffsetExample()
                Spacer(modifier = Modifier.height(40.dp))
                AnimateBothAxesExample()
                Spacer(modifier = Modifier.height(40.dp))
                SlidingDrawerExample()
                Spacer(modifier = Modifier.height(40.dp))
                FABMovementExample()
                Spacer(modifier = Modifier.height(40.dp))
                PeekAndPopEffectExample()
                Spacer(modifier = Modifier.height(40.dp))
                DraggableOffsetExample()
                Spacer(modifier = Modifier.height(40.dp))
                ComplexTransitionScreen()
                Spacer(modifier = Modifier.height(40.dp))
                FadeInMoveScaleRotateScreen()
                Spacer(modifier = Modifier.height(40.dp))
                ComplexTransitionScreen()
                Spacer(modifier = Modifier.height(40.dp))
                FadeInMoveScaleRotateScreen()
                Spacer(modifier = Modifier.height(40.dp))
                SlidingCardsExample()
                Spacer(modifier = Modifier.height(40.dp))

            }
        }
    }
}

@Composable
fun FineTuningExample() {
    Text(
        text = "Hello, Compose!",
        modifier = Modifier
            .offset(x = 4.dp, y = (-2).dp)
            .background(Color.LightGray)
            .padding(8.dp)
    )
}

@Composable
fun OverlappingExample() {
    Box {
        Box(
            modifier = Modifier
                .offset(x = (-10).dp, y = (-10).dp)
                .size(80.dp)
                .background(Color.Blue)
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Red)
        )
    }
}

@Composable
fun AnimateXOffsetExample() {
    var expanded by remember { mutableStateOf(false) }
    val offsetX = animateDpAsState(targetValue = if (expanded) 100.dp else 0.dp, label = "")

    Box(
        modifier = Modifier
            .offset(x = offsetX.value)
            .size(50.dp)
            .background(Color.Green)
            .clickable { expanded = !expanded }
    )
}

@Composable
fun AnimateYOffsetExample() {
    var floating by remember { mutableStateOf(false) }
    val offsetY = animateDpAsState(targetValue = if (floating) (-20).dp else 0.dp, label = "")

    Box(
        modifier = Modifier
            .offset(y = offsetY.value)
            .size(60.dp)
            .background(Color.Cyan)
            .clickable { floating = !floating }
    )
}

@Composable
fun AnimateBothAxesExample() {
    var moved by remember { mutableStateOf(false) }
    val offsetX = animateDpAsState(targetValue = if (moved) 50.dp else 0.dp)
    val offsetY = animateDpAsState(targetValue = if (moved) 30.dp else 0.dp)

    Box(
        modifier = Modifier
            .offset(x = offsetX.value, y = offsetY.value)
            .size(70.dp)
            .background(Color.Magenta)
            .clickable { moved = !moved }
    )
}

@Composable
fun SlidingDrawerExample() {
    var isDrawerOpen by remember { mutableStateOf(false) }
    val drawerOffsetX = animateDpAsState(targetValue = if (isDrawerOpen) 0.dp else (-300).dp,
        label = ""
    )

    Box(
        modifier = Modifier
            .offset(x = drawerOffsetX.value)
            .size(300.dp, 600.dp)
            .background(Color.Gray)
            .clickable { isDrawerOpen = !isDrawerOpen }
    )
}

@Composable
fun FABMovementExample() {
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val fabOffsetY = animateDpAsState(targetValue = if (isKeyboardVisible) (-100).dp else 0.dp,
        label = ""
    )

    FloatingActionButton(
        onClick = { isKeyboardVisible = !isKeyboardVisible },
        modifier = Modifier.offset(y = fabOffsetY.value),
        shape = CircleShape
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@Composable
fun PeekAndPopEffectExample() {
    var isHovered by remember { mutableStateOf(false) }
    val popOffset = animateDpAsState(targetValue = if (isHovered) 4.dp else 0.dp, label = "")

    Box(
        modifier = Modifier
            .offset(y = popOffset.value)
            .size(100.dp)
            .background(Color.LightGray)
            .clickable { isHovered = !isHovered }
    )
}

@Composable
fun DraggableOffsetExample() {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .offset(x = offsetX.dp, y = offsetY.dp)
            .size(100.dp)
            .background(Color.Blue)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x / density
                    offsetY += dragAmount.y / density
                }
            }
    )
}


@Composable
fun SlidingCardsExample() {
    var leftCardVisible by remember { mutableStateOf(false) }
    var rightCardVisible by remember { mutableStateOf(false) }

    // State to control the scale of the cards when clicked
    var leftCardScale by remember { mutableFloatStateOf(1f) }
    var rightCardScale by remember { mutableFloatStateOf(1f) }

    // Animating the X-axis offset for both cards with a slow tween animation
    val leftCardOffsetX = animateDpAsState(
        targetValue = if (leftCardVisible) 0.dp else (-1000).dp,
        animationSpec = tween(durationMillis = 1500), label = ""
    )
    val rightCardOffsetX = animateDpAsState(
        targetValue = if (rightCardVisible) 40.dp else 1000.dp, // Adjusted to make them partially visible
        animationSpec = tween(durationMillis = 1500), label = ""
    )

    // Adding rotation animations using Float values
    val leftCardRotation = animateFloatAsState(
        targetValue = if (leftCardVisible) 0f else 20f,
        animationSpec = tween(durationMillis = 1500), label = ""
    )
    val rightCardRotation = animateFloatAsState(
        targetValue = if (rightCardVisible) 0f else -20f,
        animationSpec = tween(durationMillis = 1500), label = ""
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // Left card with rotation and scaling effects, adjusted for partial overlap
        Box(
            modifier = Modifier
                .offset(x = leftCardOffsetX.value, y = (-10).dp) // Adjusted Y offset
                .size(150.dp, 200.dp)
                .graphicsLayer(
                    rotationZ = leftCardRotation.value,
                    scaleX = leftCardScale,
                    scaleY = leftCardScale
                )
                .background(Color.Blue)
                .clickable {
                    // Scale up the left card when clicked
                    leftCardScale = if (leftCardScale == 1f) 1.2f else 1f
                }
        )
        // Right card with rotation and scaling effects, adjusted for partial overlap
        Box(
            modifier = Modifier
                .offset(x = rightCardOffsetX.value, y = 10.dp) // Adjusted Y offset
                .size(150.dp, 200.dp)
                .graphicsLayer(
                    rotationZ = rightCardRotation.value,
                    scaleX = rightCardScale,
                    scaleY = rightCardScale
                )
                .background(Color.Red)
                .clickable {
                    // Scale up the right card when clicked
                    rightCardScale = if (rightCardScale == 1f) 1.2f else 1f
                }
        )
    }

    // Delay the start of the animation for a dramatic entrance
    LaunchedEffect(Unit) {
        delay(500) // Delay before starting the animation
        leftCardVisible = true
        rightCardVisible = true
    }
}


@Composable
fun ComplexTransitionScreen() {
    // State variables to control visibility and interaction
    var isVisible by remember { mutableStateOf(false) }
    val transitionDuration = 1000 // Duration of the animation in milliseconds

    // Animating the offset from outside the screen to the center
    val offsetX = animateDpAsState(
        targetValue = if (isVisible) 0.dp else (-300).dp, // Move from left off-screen to center
        animationSpec = tween(durationMillis = transitionDuration)
    )

    // Animating the vertical movement
    val offsetY = animateDpAsState(
        targetValue = if (isVisible) 0.dp else 300.dp, // Move from bottom off-screen to center
        animationSpec = tween(durationMillis = transitionDuration), label = ""
    )

    // Animating the scaling effect
    val scale = animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.5f, // Scale up from 50% to 100%
        animationSpec = tween(durationMillis = transitionDuration), label = ""
    )

    // Animating the rotation effect
    val rotation = animateFloatAsState(
        targetValue = if (isVisible) 0f else 360f, // Rotate from 360 degrees to 0
        animationSpec = tween(durationMillis = transitionDuration), label = ""
    )

    // Animating the alpha (fade-in effect)
    val alpha = animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f, // Fade in from 0% to 100%
        animationSpec = tween(durationMillis = transitionDuration), label = ""
    )

    // Main box displaying the animated item
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Animated element
        Box(
            modifier = Modifier
                .offset(x = offsetX.value, y = offsetY.value) // Offset animation
                .graphicsLayer( // Combining rotation, scaling, and fading effects
                    scaleX = scale.value,
                    scaleY = scale.value,
                    rotationZ = rotation.value,
                    alpha = alpha.value
                )
                .size(100.dp) // Size of the animated box
                .background(Color.Blue, shape = CircleShape) // Applying a circular shape and color
                .clickable { isVisible = !isVisible } // Toggle animation on click
        )
    }

    // Trigger the animation with a delay to start automatically
    LaunchedEffect(Unit) {
        delay(500) // Initial delay before starting the animation
        isVisible = true // Set state to true to start all animations
    }
}



@Composable
fun FadeInMoveScaleRotateScreen() {
    // State to control visibility and start the animation
    var isVisible by remember { mutableStateOf(false) }
    val animationDuration = 1000 // Duration of the transition in milliseconds

    // Animating the X offset: moves the element from left to center
    val offsetX = animateDpAsState(
        targetValue = if (isVisible) 0.dp else (-200).dp, // Start off-screen on the left
        animationSpec = tween(durationMillis = animationDuration)
    )

    // Animating the Y offset: moves the element from bottom to center
    val offsetY = animateDpAsState(
        targetValue = if (isVisible) 0.dp else 200.dp, // Start off-screen below
        animationSpec = tween(durationMillis = animationDuration)
    )

    // Scaling animation: scales up from 0.5x to 1x
    val scale = animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.5f, // Scale from half-size to full-size
        animationSpec = tween(durationMillis = animationDuration)
    )

    // Rotation animation: rotates from 360 degrees to 0 degrees
    val rotation = animateFloatAsState(
        targetValue = if (isVisible) 0f else 360f, // Full circle rotation
        animationSpec = tween(durationMillis = animationDuration)
    )

    // Alpha animation: fades in from 0 (invisible) to 1 (fully visible)
    val alpha = animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f, // Fade in effect
        animationSpec = tween(durationMillis = animationDuration)
    )

    // Main container with content alignment at the center
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Adds padding around the main box
        contentAlignment = Alignment.Center // Centers content within the box
    ) {
        // Animated box that combines offset, scaling, rotation, and fading
        Box(
            modifier = Modifier
                .offset(x = offsetX.value, y = offsetY.value) // Applies animated X and Y offsets
                .graphicsLayer( // Applies scaling, rotation, and alpha
                    scaleX = scale.value,
                    scaleY = scale.value,
                    rotationZ = rotation.value,
                    alpha = alpha.value
                )
                .size(120.dp) // Defines the size of the animated element
                .background(Color.Cyan, shape = RoundedCornerShape(20.dp)) // Sets background color and shape
                .clickable { isVisible = !isVisible } // Toggle visibility state on click to replay the animation
        )
    }

    // Automatically starts the animation with a delay when the screen loads
    LaunchedEffect(Unit) {
        delay(500) // Initial delay to start the animation after the screen appears
        isVisible = true // Sets visibility to true, triggering all animations
    }
}


