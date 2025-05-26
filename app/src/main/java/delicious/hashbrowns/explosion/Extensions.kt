package delicious.hashbrowns.explosion

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import kotlin.random.Random

fun randomInRange(min: Float, max: Float) = min + (max - min) * Random.Default.nextFloat()

fun Float.mapInRange(
    inMin: Float,
    inMax: Float,
    outMin: Float,
    outMax: Float,
) = outMin + ((this - inMin) / (inMax - inMin)) * (outMax - outMin)

fun Dp.toPx() = value * Resources.getSystem().displayMetrics.density