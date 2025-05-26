package green.ijiwon.explosion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ExplosionController {

    private val _explosionRequests = MutableSharedFlow<ExplosionRequest>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    internal val explosionRequests: SharedFlow<ExplosionRequest> = _explosionRequests

    fun explode() {
        _explosionRequests.tryEmit(ExplosionRequest.EXPLODE)
    }

    fun reset() {
        _explosionRequests.tryEmit(ExplosionRequest.RESET)
    }
}

internal enum class ExplosionRequest {
    EXPLODE, RESET
}

@Composable
fun rememberExplosionController() = remember {
    ExplosionController()
}