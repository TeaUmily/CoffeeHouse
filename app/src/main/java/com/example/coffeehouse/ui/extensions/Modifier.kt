package com.example.coffeehouse.ui.extensions

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@JvmInline
value class ShimmerAnimationDuration(val millis: Int) {
    companion object {
        val Long = ShimmerAnimationDuration(1500)
        val Medium = ShimmerAnimationDuration(1200)
        val Short = ShimmerAnimationDuration(1000)
    }
}

@JvmInline
value class ShimmerGradientSize(val sizeDivider: Float) {
    companion object {
        val Quarter = ShimmerGradientSize(4.0f)
        val Half = ShimmerGradientSize(2.0f)
        val Full = ShimmerGradientSize(1.0f)
    }
}

/**
 * copied from https://github.com/philipplackner/ShimmerEffectCompose
 * video tutorial https://www.youtube.com/watch?v=NyO99OJPPec
 * */
fun Modifier.shimmerEffect(
    colors: ImmutableList<Color> = persistentListOf(
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.1f),
        Color.LightGray.copy(alpha = 0.2f),
    ),
    animationDuration: ShimmerAnimationDuration = ShimmerAnimationDuration.Short,
    shimmerGradientSize: ShimmerGradientSize = ShimmerGradientSize.Full
): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -3 * size.width.toFloat(),
        targetValue = 3 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration.millis)
        ),
        label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(startOffsetX, 0f),
            end = Offset(
                startOffsetX + size.width.toFloat() / shimmerGradientSize.sizeDivider,
                size.height.toFloat() / shimmerGradientSize.sizeDivider
            )
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}
