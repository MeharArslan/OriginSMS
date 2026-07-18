package com.meharenterprises.originsms.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SmsIllustrationView.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010&\u001a\u00020'H\u0002J\b\u0010(\u001a\u00020'H\u0014J\b\u0010)\u001a\u00020'H\u0014J\u0010\u0010*\u001a\u00020'2\u0006\u0010+\u001a\u00020,H\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/meharenterprises/originsms/ui/SmsIllustrationView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "animTime", "", "armPath", "Landroid/graphics/Path;", "bodyPath", "bubblePath", "clipPath", "colorAccent", "", "colorBgCircle", "colorGreen", "colorGround", "colorHair", "colorJacket", "colorPants", "colorPrimary", "colorPrimaryDk", "colorRed", "colorSkin", "colorWhite", "frameCallback", "Landroid/view/Choreographer$FrameCallback;", "groundPath", "isAnimating", "", "paintFill", "Landroid/graphics/Paint;", "paintStroke", "paintText", "tmpRect", "Landroid/graphics/RectF;", "doFrame", "", "onAttachedToWindow", "onDetachedFromWindow", "onDraw", "canvas", "Landroid/graphics/Canvas;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SmsIllustrationView extends View {
    private float animTime;
    private final Path armPath;
    private final Path bodyPath;
    private final Path bubblePath;
    private final Path clipPath;
    private final int colorAccent;
    private final int colorBgCircle;
    private final int colorGreen;
    private final int colorGround;
    private final int colorHair;
    private final int colorJacket;
    private final int colorPants;
    private final int colorPrimary;
    private final int colorPrimaryDk;
    private final int colorRed;
    private final int colorSkin;
    private final int colorWhite;
    private final Choreographer.FrameCallback frameCallback;
    private final Path groundPath;
    private boolean isAnimating;
    private final Paint paintFill;
    private final Paint paintStroke;
    private final Paint paintText;
    private final RectF tmpRect;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SmsIllustrationView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ SmsIllustrationView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmsIllustrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Paint $this$paintFill_u24lambda_u240 = new Paint(1);
        $this$paintFill_u24lambda_u240.setStyle(Paint.Style.FILL);
        this.paintFill = $this$paintFill_u24lambda_u240;
        Paint $this$paintStroke_u24lambda_u241 = new Paint(1);
        $this$paintStroke_u24lambda_u241.setStyle(Paint.Style.STROKE);
        $this$paintStroke_u24lambda_u241.setStrokeCap(Paint.Cap.ROUND);
        this.paintStroke = $this$paintStroke_u24lambda_u241;
        Paint $this$paintText_u24lambda_u242 = new Paint(1);
        $this$paintText_u24lambda_u242.setTextAlign(Paint.Align.CENTER);
        $this$paintText_u24lambda_u242.setFakeBoldText(true);
        this.paintText = $this$paintText_u24lambda_u242;
        this.clipPath = new Path();
        this.bodyPath = new Path();
        this.armPath = new Path();
        this.bubblePath = new Path();
        this.groundPath = new Path();
        this.tmpRect = new RectF();
        this.colorPrimary = Color.parseColor("#4285F4");
        this.colorPrimaryDk = Color.parseColor("#174EA6");
        this.colorAccent = Color.parseColor("#FBBC04");
        this.colorGreen = Color.parseColor("#34A853");
        this.colorRed = Color.parseColor("#EA4335");
        this.colorSkin = Color.parseColor("#F8C5A8");
        this.colorHair = Color.parseColor("#1A1A2E");
        this.colorJacket = Color.parseColor("#4285F4");
        this.colorPants = Color.parseColor("#F1F3F4");
        this.colorGround = Color.parseColor("#AECBFA");
        this.colorBgCircle = Color.parseColor("#E8F0FE");
        this.colorWhite = -1;
        this.frameCallback = new Choreographer.FrameCallback() { // from class: com.meharenterprises.originsms.ui.SmsIllustrationView$$ExternalSyntheticLambda0
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                SmsIllustrationView.frameCallback$lambda$3(SmsIllustrationView.this, j);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void frameCallback$lambda$3(SmsIllustrationView this$0, long it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.doFrame();
    }

    private final void doFrame() {
        if (this.isAnimating) {
            this.animTime += 0.016f;
            invalidate();
            Choreographer.getInstance().postFrameCallback(this.frameCallback);
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(2, null);
        this.isAnimating = true;
        Choreographer.getInstance().postFrameCallback(this.frameCallback);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isAnimating = false;
        Choreographer.getInstance().removeFrameCallback(this.frameCallback);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        SmsIllustrationView smsIllustrationView = this;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        float w = getWidth();
        float h = getHeight();
        if (w >= 1.0f && h >= 1.0f) {
            float cx = w * 0.5f;
            float cy = h * 0.48f;
            float t = smsIllustrationView.animTime;
            float f1 = ((float) Math.sin(t * 0.55f)) * 7.0f;
            float f2 = ((float) Math.cos((0.7f * t) + 0.8f)) * 5.0f;
            float f3 = ((float) Math.sin((0.45f * t) + 1.6f)) * 4.0f;
            float bgR = Math.min(w, h) * 0.38f;
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorBgCircle);
            smsIllustrationView.paintFill.setAlpha(255);
            canvas.drawCircle(cx, (0.15f * f1) + cy, bgR, smsIllustrationView.paintFill);
            smsIllustrationView.paintStroke.setColor(smsIllustrationView.colorPrimary);
            smsIllustrationView.paintStroke.setAlpha(35);
            smsIllustrationView.paintStroke.setStrokeWidth(1.5f);
            canvas.drawCircle(cx, (0.15f * f1) + cy, 1.18f * bgR, smsIllustrationView.paintStroke);
            float gy = (0.1f * f1) + (bgR * 0.52f) + cy;
            smsIllustrationView.groundPath.rewind();
            smsIllustrationView.groundPath.moveTo(cx - bgR, gy);
            smsIllustrationView.groundPath.cubicTo(cx - (0.5f * bgR), gy - 12.0f, cx + (bgR * 0.3f), gy + 8.0f, cx + bgR, gy);
            smsIllustrationView.groundPath.lineTo(cx + bgR, gy + 55.0f);
            smsIllustrationView.groundPath.lineTo(cx - bgR, 55.0f + gy);
            smsIllustrationView.groundPath.close();
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorGround);
            smsIllustrationView.paintFill.setAlpha(200);
            canvas.drawPath(smsIllustrationView.groundPath, smsIllustrationView.paintFill);
            float py = cy + (0.6f * f1);
            float px = cx + (0.04f * w);
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorJacket);
            smsIllustrationView.paintFill.setAlpha(255);
            smsIllustrationView.bodyPath.rewind();
            smsIllustrationView.bodyPath.moveTo(px - 38.0f, py + 5.0f);
            smsIllustrationView.bodyPath.cubicTo(px - 42.0f, py - 35.0f, px + 42.0f, py - 35.0f, px + 38.0f, py + 5.0f);
            smsIllustrationView.bodyPath.cubicTo(px + 34.0f, py + 52.0f, px - 34.0f, py + 52.0f, px - 38.0f, py + 5.0f);
            canvas.drawPath(smsIllustrationView.bodyPath, smsIllustrationView.paintFill);
            smsIllustrationView.armPath.rewind();
            smsIllustrationView.armPath.moveTo(px + 35.0f, py - 8.0f);
            smsIllustrationView.armPath.cubicTo(px + 58.0f, py - 22.0f, px + 78.0f, py - 12.0f, px + 80.0f, py + 4.0f);
            smsIllustrationView.armPath.lineTo(px + 70.0f, 14.0f + py);
            smsIllustrationView.armPath.cubicTo(px + 62.0f, py + 4.0f, px + 48.0f, py + 8.0f, px + 30.0f, py + 16.0f);
            canvas.drawPath(smsIllustrationView.armPath, smsIllustrationView.paintFill);
            smsIllustrationView.armPath.rewind();
            smsIllustrationView.armPath.moveTo(px - 36.0f, py - 6.0f);
            smsIllustrationView.armPath.cubicTo(px - 62.0f, py - 18.0f, px - 88.0f, py - 8.0f, px - 94.0f, py + 6.0f);
            smsIllustrationView.armPath.lineTo(px - 84.0f, 18.0f + py);
            smsIllustrationView.armPath.cubicTo(px - 70.0f, py + 10.0f, px - 52.0f, py + 14.0f, px - 32.0f, py + 20.0f);
            canvas.drawPath(smsIllustrationView.armPath, smsIllustrationView.paintFill);
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorSkin);
            canvas.drawCircle(px - 95.0f, 9.0f + py, 9.0f, smsIllustrationView.paintFill);
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorPants);
            smsIllustrationView.bodyPath.rewind();
            smsIllustrationView.bodyPath.moveTo(px - 36.0f, py + 48.0f);
            smsIllustrationView.bodyPath.lineTo(px - 44.0f, py + 155.0f);
            smsIllustrationView.bodyPath.lineTo(px - 10.0f, py + 155.0f);
            smsIllustrationView.bodyPath.lineTo(px + 2.0f, py + 48.0f);
            smsIllustrationView.bodyPath.lineTo(px - 2.0f, py + 48.0f);
            smsIllustrationView.bodyPath.lineTo(px + 10.0f, py + 155.0f);
            smsIllustrationView.bodyPath.lineTo(px + 44.0f, 155.0f + py);
            smsIllustrationView.bodyPath.lineTo(px + 36.0f, 48.0f + py);
            canvas.drawPath(smsIllustrationView.bodyPath, smsIllustrationView.paintFill);
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorPrimaryDk);
            smsIllustrationView.tmpRect.set(px - 52.0f, 150.0f + py, px - 6.0f, py + 168.0f);
            canvas.drawRoundRect(smsIllustrationView.tmpRect, 7.0f, 7.0f, smsIllustrationView.paintFill);
            smsIllustrationView.tmpRect.set(px + 6.0f, 150.0f + py, px + 52.0f, 168.0f + py);
            canvas.drawRoundRect(smsIllustrationView.tmpRect, 7.0f, 7.0f, smsIllustrationView.paintFill);
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorSkin);
            smsIllustrationView.tmpRect.set(px + 2.0f, py - 52.0f, px + 18.0f, py - 36.0f);
            canvas.drawRoundRect(smsIllustrationView.tmpRect, 5.0f, 5.0f, smsIllustrationView.paintFill);
            float hx = px + 10.0f;
            float hy = py - 72.0f;
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorSkin);
            canvas.drawCircle(hx, hy, 27.0f, smsIllustrationView.paintFill);
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorHair);
            smsIllustrationView.clipPath.rewind();
            smsIllustrationView.clipPath.addCircle(hx, hy, 27.0f, Path.Direction.CW);
            canvas.save();
            canvas.clipPath(smsIllustrationView.clipPath);
            canvas.drawRect(hx - 30.0f, hy - 30.0f, hx + 30.0f, hy - 2.0f, smsIllustrationView.paintFill);
            canvas.restore();
            canvas.drawCircle(10.0f + hx, hy - 34.0f, 11.0f, smsIllustrationView.paintFill);
            float bx = cx - (bgR * 0.52f);
            float by = (cy - (0.42f * bgR)) + f2;
            float br = bgR * 0.24f;
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorAccent);
            smsIllustrationView.paintFill.setAlpha(255);
            canvas.drawCircle(bx, by, br, smsIllustrationView.paintFill);
            smsIllustrationView.bubblePath.rewind();
            smsIllustrationView.bubblePath.moveTo((0.2f * br) + bx, (0.65f * br) + by);
            smsIllustrationView.bubblePath.lineTo(bx - (br * 0.3f), (1.35f * br) + by);
            smsIllustrationView.bubblePath.lineTo((0.85f * br) + bx, by + (br * 0.55f));
            smsIllustrationView.bubblePath.close();
            canvas.drawPath(smsIllustrationView.bubblePath, smsIllustrationView.paintFill);
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorGreen);
            smsIllustrationView.paintFill.setAlpha(230);
            float sqS = bgR * 0.14f;
            canvas.save();
            canvas.rotate(((float) Math.sin(t * 0.3f)) * 20.0f, (0.52f * bgR) + cx, (cy - (0.58f * bgR)) + f3);
            smsIllustrationView.tmpRect.set((bgR * 0.38f) + cx, (cy - (0.68f * bgR)) + f3, cx + (0.38f * bgR) + sqS, (cy - (0.68f * bgR)) + f3 + sqS);
            canvas.drawRect(smsIllustrationView.tmpRect, smsIllustrationView.paintFill);
            canvas.restore();
            smsIllustrationView.paintFill.setColor(Color.parseColor("#FA7B17"));
            smsIllustrationView.tmpRect.set((0.55f * bgR) + cx, (cy - (0.12f * bgR)) + f2, (0.82f * bgR) + cx, (0.14f * bgR) + cy + f2);
            canvas.drawArc(smsIllustrationView.tmpRect, 90.0f, 180.0f, false, smsIllustrationView.paintFill);
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorRed);
            smsIllustrationView.paintFill.setAlpha(200);
            canvas.drawCircle((bgR * 0.28f) + cx, (cy - (0.22f * bgR)) + f3, 0.042f * bgR, smsIllustrationView.paintFill);
            smsIllustrationView.paintFill.setColor(smsIllustrationView.colorRed);
            smsIllustrationView.paintFill.setAlpha(255);
            canvas.drawCircle(px + 82.0f, (py - 28.0f) + (f1 * 0.3f), 11.0f, smsIllustrationView.paintFill);
            smsIllustrationView.paintText.setColor(smsIllustrationView.colorWhite);
            smsIllustrationView.paintText.setTextSize(13.0f);
            canvas.drawText("!", px + 82.0f, (py - 24.0f) + (0.3f * f1), smsIllustrationView.paintText);
            float[][] particles = {new float[]{cx - (bgR * 0.78f), (bgR * 0.58f) + cy, -345084.0f}, new float[]{(bgR * 0.68f) + cx, (0.62f * bgR) + cy, smsIllustrationView.colorPrimary}, new float[]{cx - (bgR * 0.6f), cy - (bgR * 0.48f), smsIllustrationView.colorAccent}};
            float[][] $this$forEachIndexed$iv = particles;
            int $i$f$forEachIndexed = 0;
            int index$iv = 0;
            int length = $this$forEachIndexed$iv.length;
            int i = 0;
            while (i < length) {
                Object item$iv = $this$forEachIndexed$iv[i];
                float[] p = (float[]) item$iv;
                float[][] particles2 = particles;
                float pulse = (((float) Math.sin((1.3f * t) + (index$iv * 1.4f))) * 0.35f) + 0.65f;
                smsIllustrationView.paintFill.setColor((int) p[2]);
                smsIllustrationView.paintFill.setAlpha((int) (150 * pulse));
                canvas.drawCircle(p[0], p[1] + (((float) Math.sin(index$iv + t)) * 5.0f), 0.05f * bgR * pulse, smsIllustrationView.paintFill);
                i++;
                smsIllustrationView = this;
                $i$f$forEachIndexed = $i$f$forEachIndexed;
                index$iv++;
                particles = particles2;
                $this$forEachIndexed$iv = $this$forEachIndexed$iv;
            }
        }
    }
}
