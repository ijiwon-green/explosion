package delicious.hashbrowns.explosion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ExplosionController {

    private val _request = MutableSharedFlow<ExplosionRequest>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    internal val request: SharedFlow<ExplosionRequest> = _request

    fun explode() {
        _request.tryEmit(ExplosionRequest.EXPLODE)
    }

    fun reset() {
        _request.tryEmit(ExplosionRequest.RESET)
    }
}

internal enum class ExplosionRequest {
    EXPLODE, RESET
}

@Composable
fun rememberExplosionController() = remember {
    ExplosionController()
}