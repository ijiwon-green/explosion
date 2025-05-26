package delicious.hashbrowns.explosion

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.roundToInt

@Composable
internal fun Explosion(
    progress: Float,
    bound: Rect,
    modifier: Modifier = Modifier,
) {
    val colors = listOf(0xFFEA4335, 0xFF4285F4, 0xFFFBBC05, 0xFF34a853)

    val particles = remember {
        List(150) {
            with(bound) {
                Particle(
                    Color(colors.random()),
                    startXPosition = center.x.roundToInt(),
                    startYPosition = center.y.roundToInt(),
                    maxVerticalDisplacement = height * randomInRange(-0.2F, 0.8F),
                    maxHorizontalDisplacement = width * randomInRange(-0.9F, 0.9F),
                )
            }
        }
    }

    Canvas(modifier) {
        particles.forEach { particle ->
            with(particle) {
                updateProgress(progress)

                drawCircle(
                    color = color,
                    radius = radius,
                    center = Offset(x, y),
                    alpha = alpha,
                )
            }
        }
    }
}

class Particle(
    val color: Color,
    val startXPosition: Int,
    val startYPosition: Int,
    val maxHorizontalDisplacement: Float,
    val maxVerticalDisplacement: Float,
) {
    private val velocity = 4 * maxVerticalDisplacement
    private val acceleration = -2 * velocity
    private val visibilityThresholdLow = randomInRange(0F, 0.14F)
    private val visibilityThresholdHigh = randomInRange(0F, 0.4F)
    private val initialXDisplacement = 12.dp.toPx() * randomInRange(-1F, 1F)
    private val initialYDisplacement = 12.dp.toPx() * randomInRange(-1F, 1F)
    private val minRadius = 2.dp.toPx()
    private val maxRadius = minRadius + 4.dp.toPx() * randomInRange(0F, 1F)

    var x = 0F
    var y = 0F
    var radius = 0F
    var alpha = 0F

    fun updateProgress(progress: Float) {
        if (progress < visibilityThresholdLow || progress > (1 - visibilityThresholdHigh)) {
            alpha = 0F

            return
        }

        val trajectoryProgress = (progress - visibilityThresholdLow).mapInRange(
            0F,
            1F - visibilityThresholdHigh - visibilityThresholdLow,
            0F,
            1F
        )

        alpha = if (trajectoryProgress < 0.7F) {
            1F
        } else {
            (trajectoryProgress - 0.7F).mapInRange(
                0F,
                0.3F,
                1F,
                0F
            )
        }

        radius = minRadius + (maxRadius - minRadius) * trajectoryProgress

        val time = trajectoryProgress.mapInRange(0F, 1F, 0F, 1.4F)

        val verticalDisplacement = (time * velocity + 0.5 * acceleration * time.toDouble().pow(2)).toFloat()

        x = startXPosition + initialXDisplacement + maxHorizontalDisplacement * trajectoryProgress
        y = startYPosition + initialYDisplacement - verticalDisplacement
    }
}