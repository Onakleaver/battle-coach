package com.battlecoach.util.performance

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerformanceMonitor @Inject constructor() {
    private val metrics = mutableMapOf<String, MetricData>()

    fun startOperation(name: String) {
        metrics[name] = MetricData(
            startTime = SystemClock.elapsedRealtime(),
            memoryBefore = Runtime.getRuntime().totalMemory()
        )
    }

    fun endOperation(name: String) {
        metrics[name]?.let { data ->
            val duration = SystemClock.elapsedRealtime() - data.startTime
            val memoryUsed = Runtime.getRuntime().totalMemory() - data.memoryBefore
            
            Timber.d("""
                Operation: $name
                Duration: $duration ms
                Memory Used: ${memoryUsed / 1024 / 1024} MB
            """.trimIndent())
        }
    }
}

private data class MetricData(
    val startTime: Long,
    val memoryBefore: Long
)

@Composable
fun MeasureComposablePerformance(
    name: String,
    monitor: PerformanceMonitor
) {
    val operationName = remember { "compose_$name" }
    
    DisposableEffect(Unit) {
        monitor.startOperation(operationName)
        onDispose {
            monitor.endOperation(operationName)
        }
    }
} 