package com.meharenterprises.originsms.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.sin
import kotlin.math.cos

/**
 * Very subtle floating blob background — inspired by Google Messages clean style.
 * Extremely low opacity to not distract from illustration.
 */
class BlobAnimationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var time = 0f

    private data class Blob(val bx: Float, val by: Float, val r: Float, val color: Int, val alpha: Float, val spd: Float)

    private val blobs = listOf(
        Blob(0.12f, 0.18f, 110f, Color.parseColor("#C5CAE9"), 0.55f, 0.25f),
        Blob(0.82f, 0.14f, 85f,  Color.parseColor("#FFCCBC"), 0.45f, 0.35f),
        Blob(0.72f, 0.78f, 100f, Color.parseColor("#C5CAE9"), 0.40f, 0.20f),
        Blob(0.10f, 0.72f, 75f,  Color.parseColor("#FFCCBC"), 0.35f, 0.30f),
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        time += 0.008f

        blobs.forEach { b ->
            val x = w * b.bx + 25f * sin(time * b.spd + b.bx * 8f)
            val y = h * b.by + 20f * cos(time * b.spd + b.by * 8f)
            paint.color = b.color
            paint.alpha = (b.alpha * 255).toInt()
            canvas.drawCircle(x, y, b.r, paint)
        }

        postInvalidateOnAnimation()
    }

    fun startAnimation() { invalidate() }
}
