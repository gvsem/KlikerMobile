package org.clkrw.mobile.ui.screens.clicker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.clkrw.mobile.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource

class LaserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : SurfaceView(context, attrs, defStyleAttr, defStyleRes) {

    private var active = false
    private var touchX = 0f
    private var touchY = 0f

    init {
        setWillNotDraw(false)
        setBackgroundColor(resources.getColor(R.color.yellow))
    }

    private val paint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(android.R.color.holo_red_light)
    }

    override fun onDraw(canvas: Canvas) {
        if (active) {
            canvas.drawCircle(touchX, touchY, 50f, paint)
        }
        super.onDraw(canvas)
    }

    fun start() {
        active = true
    }

    fun touch(x: Float, y: Float) {
        touchX = x
        touchY = y
        invalidate()
    }

    fun end() {
        if (active) {
            active = false
            invalidate()
        }
    }

}


class LatencyEventTracker<Event>(
    private val latency: Duration = DEFAULT_LATENCY,
    private val callback: (Event) -> Unit
) {
    private var lastTimeMs = TimeSource.Monotonic.markNow()

    fun track(event: Event) {
        val timeNow = TimeSource.Monotonic.markNow()
        if (timeNow - lastTimeMs > latency) {
            callback(event)
            lastTimeMs = timeNow
        }
    }

    companion object {
        val DEFAULT_LATENCY = (1000 / 25).milliseconds
    }
}

@Composable
fun LaserViewComposable(
    modifier : Modifier,
    callback : (Boolean, Float, Float) -> Unit
) {

    // Adds view to Compose
    AndroidView(
        modifier = modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
        factory = { context ->

            val tracker = LatencyEventTracker<PointF> { callback(true, minOf(1.0f, maxOf(0.0f, it.x)), minOf(1.0f, maxOf(0.0f, it.y)))}
            val surface = LaserView(context).apply {
                var isInAction = false

                setOnTouchListener { v, event ->
                    when (event.actionMasked) {
                        MotionEvent.ACTION_DOWN -> {
                            this.start()
                            this.touch(event.x, event.y)
                            isInAction = true
                            tracker.track(PointF(event.x / this.width, event.y / this.height))
                        }

                        MotionEvent.ACTION_MOVE -> {
                            this.touch(event.x, event.y)
                            if (isInAction) {
                                tracker.track(PointF(event.x / this.width, event.y / this.height))
                            }
                        }
                        MotionEvent.ACTION_UP -> {
                            this.end()
                            isInAction = false
                            callback(false, 0.0f, 0.0f)
                        }
                    }
                    true
                }
            }

            surface

        }
    )
}