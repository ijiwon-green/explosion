### Explosion Composable

### [:link:](https://github.com/omkar-tenkale/ExplodingComposable)

<img src="https://raw.githubusercontent.com/space0clouds/explosion/main/arts/explosion.webp"/>

`updateProgress()`

```kotlin
fun updateProgress(progress: Float) {
    if (progress < visibilityThresholdLow || progress > (1 - visibilityThresholdHigh)) {
        alpha = 0F

        return
    }

    val trajectoryProgress = (progress - visibilityThresholdLow).mapInRange(
        inMin = 0F,
        inMax = 1F - visibilityThresholdHigh - visibilityThresholdLow,
        outMin = 0F,
        outMax = 1F
    )

    alpha = if (trajectoryProgress < 0.7F) {
        1F
    } else {
        (trajectoryProgress - 0.7F).mapInRange(
            inMin = 0F,
            inMax = 0.3F,
            outMin = 1F,
            outMax = 0F
        )
    }

    radius = minRadius + (maxRadius - minRadius) * trajectoryProgress

    val time = trajectoryProgress.mapInRange(0F, 1F, 0F, 1.4F)

    val verticalDisplacement = (time * velocity + 0.5 * acceleration * time.toDouble().pow(2)).toFloat()

    x = startXPosition + initialXDisplacement + maxHorizontalDisplacement * trajectoryProgress
    y = startYPosition + initialYDisplacement - verticalDisplacement
}

```
