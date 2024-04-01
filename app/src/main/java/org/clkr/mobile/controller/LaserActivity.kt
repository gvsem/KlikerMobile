package org.clkr.mobile.controller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import org.clkr.mobile.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource

class LaserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laser)

        val surface = findViewById<LaserView>(R.id.laserView)
        val tracker = LatencyEventTracker<PointF> { Log.d("INFO", "tracked: $it") }

        surface.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int,
            ) {
                Log.d("INFO", "surface: $width x $height")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }
        })

        surface.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    surface.start()
                    surface.touch(event.x, event.y)
                    tracker.track(PointF(event.x, event.y))
                }

                MotionEvent.ACTION_MOVE -> {
                    surface.touch(event.x, event.y)
                    tracker.track(PointF(event.x, event.y))
                }
                MotionEvent.ACTION_UP -> {
                    surface.end()
                    tracker.track(PointF(event.x, event.y))
                }
            }
            true
        }
    }
}


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
        setBackgroundColor(Color.WHITE)
    }

    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
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
