package com.meharenterprises.originsms.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BlobAnimationView.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0013B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0014J\u0006\u0010\u0012\u001a\u00020\u000fR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/meharenterprises/originsms/ui/BlobAnimationView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "blobs", "", "Lcom/meharenterprises/originsms/ui/BlobAnimationView$Blob;", "paint", "Landroid/graphics/Paint;", "time", "", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "startAnimation", "Blob", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BlobAnimationView extends View {
    private final List<Blob> blobs;
    private final Paint paint;
    private float time;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BlobAnimationView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ BlobAnimationView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BlobAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        this.paint = new Paint(1);
        this.blobs = CollectionsKt.listOf((Object[]) new Blob[]{new Blob(0.12f, 0.18f, 110.0f, Color.parseColor("#C5CAE9"), 0.55f, 0.25f), new Blob(0.82f, 0.14f, 85.0f, Color.parseColor("#FFCCBC"), 0.45f, 0.35f), new Blob(0.72f, 0.78f, 100.0f, Color.parseColor("#C5CAE9"), 0.4f, 0.2f), new Blob(0.1f, 0.72f, 75.0f, Color.parseColor("#FFCCBC"), 0.35f, 0.3f)});
    }

    /* compiled from: BlobAnimationView.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003JE\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0007HÖ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006 "}, d2 = {"Lcom/meharenterprises/originsms/ui/BlobAnimationView$Blob;", "", "bx", "", "by", "r", TypedValues.Custom.S_COLOR, "", "alpha", "spd", "(FFFIFF)V", "getAlpha", "()F", "getBx", "getBy", "getColor", "()I", "getR", "getSpd", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* loaded from: classes3.dex */
    private static final /* data */ class Blob {
        private final float alpha;
        private final float bx;
        private final float by;
        private final int color;
        private final float r;
        private final float spd;

        public static /* synthetic */ Blob copy$default(Blob blob, float f, float f2, float f3, int i, float f4, float f5, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                f = blob.bx;
            }
            if ((i2 & 2) != 0) {
                f2 = blob.by;
            }
            float f6 = f2;
            if ((i2 & 4) != 0) {
                f3 = blob.r;
            }
            float f7 = f3;
            if ((i2 & 8) != 0) {
                i = blob.color;
            }
            int i3 = i;
            if ((i2 & 16) != 0) {
                f4 = blob.alpha;
            }
            float f8 = f4;
            if ((i2 & 32) != 0) {
                f5 = blob.spd;
            }
            return blob.copy(f, f6, f7, i3, f8, f5);
        }

        /* renamed from: component1, reason: from getter */
        public final float getBx() {
            return this.bx;
        }

        /* renamed from: component2, reason: from getter */
        public final float getBy() {
            return this.by;
        }

        /* renamed from: component3, reason: from getter */
        public final float getR() {
            return this.r;
        }

        /* renamed from: component4, reason: from getter */
        public final int getColor() {
            return this.color;
        }

        /* renamed from: component5, reason: from getter */
        public final float getAlpha() {
            return this.alpha;
        }

        /* renamed from: component6, reason: from getter */
        public final float getSpd() {
            return this.spd;
        }

        public final Blob copy(float bx, float by, float r, int color, float alpha, float spd) {
            return new Blob(bx, by, r, color, alpha, spd);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Blob)) {
                return false;
            }
            Blob blob = (Blob) other;
            return Float.compare(this.bx, blob.bx) == 0 && Float.compare(this.by, blob.by) == 0 && Float.compare(this.r, blob.r) == 0 && this.color == blob.color && Float.compare(this.alpha, blob.alpha) == 0 && Float.compare(this.spd, blob.spd) == 0;
        }

        public int hashCode() {
            return (((((((((Float.hashCode(this.bx) * 31) + Float.hashCode(this.by)) * 31) + Float.hashCode(this.r)) * 31) + Integer.hashCode(this.color)) * 31) + Float.hashCode(this.alpha)) * 31) + Float.hashCode(this.spd);
        }

        public String toString() {
            return "Blob(bx=" + this.bx + ", by=" + this.by + ", r=" + this.r + ", color=" + this.color + ", alpha=" + this.alpha + ", spd=" + this.spd + ")";
        }

        public Blob(float bx, float by, float r, int color, float alpha, float spd) {
            this.bx = bx;
            this.by = by;
            this.r = r;
            this.color = color;
            this.alpha = alpha;
            this.spd = spd;
        }

        public final float getAlpha() {
            return this.alpha;
        }

        public final float getBx() {
            return this.bx;
        }

        public final float getBy() {
            return this.by;
        }

        public final int getColor() {
            return this.color;
        }

        public final float getR() {
            return this.r;
        }

        public final float getSpd() {
            return this.spd;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        float w = getWidth();
        float h = getHeight();
        this.time += 0.008f;
        Iterable $this$forEach$iv = this.blobs;
        for (Object element$iv : $this$forEach$iv) {
            Blob b = (Blob) element$iv;
            float x = (b.getBx() * w) + (((float) Math.sin((this.time * b.getSpd()) + (b.getBx() * 8.0f))) * 25.0f);
            float y = (b.getBy() * h) + (((float) Math.cos((this.time * b.getSpd()) + (b.getBy() * 8.0f))) * 20.0f);
            this.paint.setColor(b.getColor());
            this.paint.setAlpha((int) (b.getAlpha() * 255));
            canvas.drawCircle(x, y, b.getR(), this.paint);
        }
        postInvalidateOnAnimation();
    }

    public final void startAnimation() {
        invalidate();
    }
}
