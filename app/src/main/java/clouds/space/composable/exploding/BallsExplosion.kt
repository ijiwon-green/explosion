package clouds.space.composable.exploding

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun BallsExplosion(
    controller: ExplosionController,
    modifier: Modifier = Modifier,
    manualProgress: Float? = null,
) {
    val coroutineScope = rememberCoroutineScope()

    val animatedProgress = remember {
        Animatable(0F)
    }

    val progress = manualProgress ?: animatedProgress.value

    Box(modifier) {
        Explosion(
            progress,
            Rect(
                Offset.Zero,
                Size(256.dp.toPx(), 256.dp.toPx())
            )
        )
    }

    LaunchedEffect(Unit) {
        controller.explosionRequests.collect { request ->
            when (request) {
                ExplosionRequest.EXPLODE -> {
                    coroutineScope.launch {
                        animatedProgress.snapTo(0F)
                        animatedProgress.animateTo(1F, animationSpec = tween(durationMillis = 5000))
                    }
                }

                ExplosionRequest.RESET -> {
                    animatedProgress.snapTo(0F)
                }
            }
        }
    }
}

@Preview
@Composable
private fun BallsExplosionPreview() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val explosionController = rememberExplosionController()

        var progress by remember {
            mutableFloatStateOf(0F)
        }

        BallsExplosion(
            controller = explosionController,
            modifier = Modifier
                .border(1.dp, Color.Red)
                .size(256.dp),
            // manualProgress = progress,
        )

        Slider(
            modifier = Modifier.padding(horizontal = 24.dp),
            value = progress,
            onValueChange = { progress = it },
            valueRange = 0f..1f
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .size(48.dp)
                .clickable {
                    explosionController.explode()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "\uD83D\uDD25",
                fontSize = 18.sp,
            )
        }
    }
}