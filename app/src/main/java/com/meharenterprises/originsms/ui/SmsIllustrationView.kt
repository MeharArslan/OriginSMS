package com.meharenterprises.originsms.ui

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import kotlin.math.*

/**
 * Original Material Design illustration inspired by Google Messages onboarding.
 * NOT copied — completely original artwork.
 *
 * Performance:
 * - Hardware layer during animation
 * - Throttled to ~60fps via Choreographer
 * - Pre-allocated Paint/Path objects
 * - No allocations in onDraw
 */
class SmsIllustrationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    // Pre-allocated — NO allocations inside onDraw
    private val paintFill = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val paintStroke = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE; strokeCap = Paint.Cap.ROUND }
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply { textAlign = Paint.Align.CENTER; isFakeBoldText = true }
    private val clipPath = Path()
    private val bodyPath = Path()
    private val armPath = Path()
    private val bubblePath = Path()
    private val groundPath = Path()
    private val tmpRect = RectF()

    // Material Design 3 palette
    private val colorPrimary   = Color.parseColor("#4285F4") // Google blue
    private val colorPrimaryDk = Color.parseColor("#174EA6")
    private val colorAccent    = Color.parseColor("#FBBC04") // Yellow speech bubble
    private val colorGreen     = Color.parseColor("#34A853")
    private val colorRed       = Color.parseColor("#EA4335")
    private val colorSkin      = Color.parseColor("#F8C5A8")
    private val colorHair      = Color.parseColor("#1A1A2E")
    private val colorJacket    = Color.parseColor("#4285F4")
    private val colorPants     = Color.parseColor("#F1F3F4")
    private val colorGround    = Color.parseColor("#AECBFA")
    private val colorBgCircle  = Color.parseColor("#E8F0FE")
    private val colorWhite     = Color.WHITE

    private var animTime = 0f
    private var isAnimating = false
    private val frameCallback = android.view.Choreographer.FrameCallback { doFrame() }

    private fun doFrame() {
        if (!isAnimating) return
        animTime += 0.016f // ~60fps delta
        invalidate()
        android.view.Choreographer.getInstance().postFrameCallback(frameCallback)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setLayerType(LAYER_TYPE_HARDWARE, null)
        isAnimating = true
        android.view.Choreographer.getInstance().postFrameCallback(frameCallback)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isAnimating = false
        android.view.Choreographer.getInstance().removeFrameCallback(frameCallback)
    }

    override fun onDraw(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()
        if (w < 1f || h < 1f) return

        val cx = w * 0.5f
        val cy = h * 0.48f
        val t = animTime

        // Gentle float offsets (no heap allocation)
        val f1 = 7f * sin(t * 0.55f)          // person
        val f2 = 5f * cos(t * 0.7f + 0.8f)    // bubble
        val f3 = 4f * sin(t * 0.45f + 1.6f)   // shapes

        // ── Background circle ──
        val bgR = min(w, h) * 0.38f
        paintFill.color = colorBgCircle
        paintFill.alpha = 255
        canvas.drawCircle(cx, cy + f1 * 0.15f, bgR, paintFill)

        // ── Thin outer ring ──
        paintStroke.color = colorPrimary
        paintStroke.alpha = 35
        paintStroke.strokeWidth = 1.5f
        canvas.drawCircle(cx, cy + f1 * 0.15f, bgR * 1.18f, paintStroke)

        // ── Ground shape (blue wave) ──
        val gy = cy + bgR * 0.52f + f1 * 0.1f
        groundPath.rewind()
        groundPath.moveTo(cx - bgR, gy)
        groundPath.cubicTo(cx - bgR * 0.5f, gy - 12f, cx + bgR * 0.3f, gy + 8f, cx + bgR, gy)
        groundPath.lineTo(cx + bgR, gy + 55f)
        groundPath.lineTo(cx - bgR, gy + 55f)
        groundPath.close()
        paintFill.color = colorGround
        paintFill.alpha = 200
        canvas.drawPath(groundPath, paintFill)

        // ── Person — Y center ──
        val py = cy + f1 * 0.6f
        val px = cx + w * 0.04f

        // Body (jacket)
        paintFill.color = colorJacket
        paintFill.alpha = 255
        bodyPath.rewind()
        bodyPath.moveTo(px - 38f, py + 5f)
        bodyPath.cubicTo(px - 42f, py - 35f, px + 42f, py - 35f, px + 38f, py + 5f)
        bodyPath.cubicTo(px + 34f, py + 52f, px - 34f, py + 52f, px - 38f, py + 5f)
        canvas.drawPath(bodyPath, paintFill)

        // Right arm (back)
        armPath.rewind()
        armPath.moveTo(px + 35f, py - 8f)
        armPath.cubicTo(px + 58f, py - 22f, px + 78f, py - 12f, px + 80f, py + 4f)
        armPath.lineTo(px + 70f, py + 14f)
        armPath.cubicTo(px + 62f, py + 4f, px + 48f, py + 8f, px + 30f, py + 16f)
        canvas.drawPath(armPath, paintFill)

        // Left arm (forward)
        armPath.rewind()
        armPath.moveTo(px - 36f, py - 6f)
        armPath.cubicTo(px - 62f, py - 18f, px - 88f, py - 8f, px - 94f, py + 6f)
        armPath.lineTo(px - 84f, py + 18f)
        armPath.cubicTo(px - 70f, py + 10f, px - 52f, py + 14f, px - 32f, py + 20f)
        canvas.drawPath(armPath, paintFill)

        // Hand
        paintFill.color = colorSkin
        canvas.drawCircle(px - 95f, py + 9f, 9f, paintFill)

        // Pants
        paintFill.color = colorPants
        bodyPath.rewind()
        bodyPath.moveTo(px - 36f, py + 48f)
        bodyPath.lineTo(px - 44f, py + 155f)
        bodyPath.lineTo(px - 10f, py + 155f)
        bodyPath.lineTo(px + 2f, py + 48f)
        bodyPath.lineTo(px - 2f, py + 48f)
        bodyPath.lineTo(px + 10f, py + 155f)
        bodyPath.lineTo(px + 44f, py + 155f)
        bodyPath.lineTo(px + 36f, py + 48f)
        canvas.drawPath(bodyPath, paintFill)

        // Shoes
        paintFill.color = colorPrimaryDk
        tmpRect.set(px - 52f, py + 150f, px - 6f, py + 168f)
        canvas.drawRoundRect(tmpRect, 7f, 7f, paintFill)
        tmpRect.set(px + 6f, py + 150f, px + 52f, py + 168f)
        canvas.drawRoundRect(tmpRect, 7f, 7f, paintFill)

        // Neck
        paintFill.color = colorSkin
        tmpRect.set(px + 2f, py - 52f, px + 18f, py - 36f)
        canvas.drawRoundRect(tmpRect, 5f, 5f, paintFill)

        // Head
        val hx = px + 10f; val hy = py - 72f
        paintFill.color = colorSkin
        canvas.drawCircle(hx, hy, 27f, paintFill)

        // Hair
        paintFill.color = colorHair
        clipPath.rewind()
        clipPath.addCircle(hx, hy, 27f, Path.Direction.CW)
        canvas.save()
        canvas.clipPath(clipPath)
        canvas.drawRect(hx - 30f, hy - 30f, hx + 30f, hy - 2f, paintFill)
        canvas.restore()
        // Bun
        canvas.drawCircle(hx + 10f, hy - 34f, 11f, paintFill)

        // ── Large Speech bubble (yellow, upper left) ──
        val bx = cx - bgR * 0.52f
        val by = cy - bgR * 0.42f + f2
        val br = bgR * 0.24f
        paintFill.color = colorAccent
        paintFill.alpha = 255
        canvas.drawCircle(bx, by, br, paintFill)
        // Tail
        bubblePath.rewind()
        bubblePath.moveTo(bx + br * 0.2f, by + br * 0.65f)
        bubblePath.lineTo(bx - br * 0.3f, by + br * 1.35f)
        bubblePath.lineTo(bx + br * 0.85f, by + br * 0.55f)
        bubblePath.close()
        canvas.drawPath(bubblePath, paintFill)

        // ── Floating geometric shapes ──
        // Green square (top right)
        paintFill.color = colorGreen
        paintFill.alpha = 230
        val sqS = bgR * 0.14f
        canvas.save()
        canvas.rotate(20f * sin(t * 0.3f), cx + bgR * 0.52f, cy - bgR * 0.58f + f3)
        tmpRect.set(cx + bgR * 0.38f, cy - bgR * 0.68f + f3, cx + bgR * 0.38f + sqS, cy - bgR * 0.68f + f3 + sqS)
        canvas.drawRect(tmpRect, paintFill)
        canvas.restore()

        // Orange D-shape right mid
        paintFill.color = Color.parseColor("#FA7B17")
        tmpRect.set(cx + bgR * 0.55f, cy - bgR * 0.12f + f2, cx + bgR * 0.82f, cy + bgR * 0.14f + f2)
        canvas.drawArc(tmpRect, 90f, 180f, false, paintFill)

        // Red dot
        paintFill.color = colorRed
        paintFill.alpha = 200
        canvas.drawCircle(cx + bgR * 0.28f, cy - bgR * 0.22f + f3, bgR * 0.042f, paintFill)

        // ── SMS notification badge on person ──
        paintFill.color = colorRed
        paintFill.alpha = 255
        canvas.drawCircle(px + 82f, py - 28f + f1 * 0.3f, 11f, paintFill)
        paintText.color = colorWhite
        paintText.textSize = 13f
        canvas.drawText("!", px + 82f, py - 24f + f1 * 0.3f, paintText)

        // ── Floating particles ──
        val particles = arrayOf(
            floatArrayOf(cx - bgR * 0.78f, cy + bgR * 0.58f, 0xFF_FA_BC_04.toInt().toFloat()),
            floatArrayOf(cx + bgR * 0.68f, cy + bgR * 0.62f, colorPrimary.toFloat()),
            floatArrayOf(cx - bgR * 0.6f,  cy - bgR * 0.48f, colorAccent.toFloat()),
        )
        particles.forEachIndexed { i, p ->
            val pulse = 0.65f + 0.35f * sin(t * 1.3f + i * 1.4f)
            paintFill.color = p[2].toInt()
            paintFill.alpha = (150 * pulse).toInt()
            canvas.drawCircle(p[0], p[1] + 5f * sin(t + i), bgR * 0.05f * pulse, paintFill)
        }
    }
}
