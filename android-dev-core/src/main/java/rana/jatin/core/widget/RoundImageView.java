package rana.jatin.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import rana.jatin.core.R;

import static android.graphics.Shader.TileMode.CLAMP;

public class RoundImageView extends AppCompatImageView {
    private static final Bitmap.Config DEFAULT_BITMAP_CONFIG = Bitmap.Config.ARGB_8888;

    private Border border;

    private Canvas canvas = new Canvas();
    private Paint paint = new Paint();
    private Bitmap srcBitmap;

    public RoundImageView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private static Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        if (newWidth > 0 && newHeight > 0) {
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(newWidth, newHeight, DEFAULT_BITMAP_CONFIG);
            } else {
                int oldWidth = bitmap.getWidth();
                int oldHeight = bitmap.getHeight();
                boolean changed = newWidth != oldWidth || newHeight != oldHeight;

                if (changed) {
                    bitmap.recycle();
                    bitmap = Bitmap.createBitmap(newWidth, newHeight, DEFAULT_BITMAP_CONFIG);
                }
            }
        }

        return bitmap;
    }

    private void init(Context context, AttributeSet as) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint.setAntiAlias(true);

        if (as != null) {
            TypedArray ta = context.obtainStyledAttributes(as, R.styleable.RoundImageView);

            try {
                //int borderType = ta.getInt(R.styleable.RoundedImageView_type, 0);
                int cornerRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView_cornerRadius, 0);

                if (cornerRadius > 0) {
                    border = new RectBorder(cornerRadius);
                } else {
                    border = new CircularBorder();
                }

                border.shadowDx = ta.getDimensionPixelSize(R.styleable.RoundImageView_shadowDx, 0);
                border.shadowDy = ta.getDimensionPixelSize(R.styleable.RoundImageView_shadowDy, 0);
                border.shadowRadius = ta.getDimensionPixelSize(R.styleable.RoundImageView_shadowRadius, 0);
                border.shadowColor = ta.getColor(R.styleable.RoundImageView_shadowColor, Color.BLACK);

                border.width = ta.getDimensionPixelSize(R.styleable.RoundImageView_outerBorderWidth, 0);
                border.color = ta.getColor(R.styleable.RoundImageView_outerBorderColor, Color.BLACK);
            } finally {
                ta.recycle();
            }
        }
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        calculateMetrics();
        return changed;
    }

    @Override
    public boolean isOpaque() {
        boolean isOpaque = super.isOpaque();
        if (border != null) {
            isOpaque &= border.isImageOpaque();
        }

        return isOpaque;
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        calculateMetrics();
    }

    private void calculateMetrics() {
        if (border != null) {
            border.calculateMetrics(this);
        }

        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        srcBitmap = resizeBitmap(srcBitmap, width, height);
        if (canvas == null) {
            canvas = new Canvas();
        }
        canvas.setBitmap(srcBitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        //border
        if (border != null) {
            border.drawOuter(canvas, paint);
        }

        //background
        Drawable backgroundDrawable = getBackground();
        if (backgroundDrawable != null) {
            backgroundDrawable.setAlpha(Math.round(255 * getAlpha()));
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            if (backgroundDrawable instanceof ColorDrawable) {
                ColorDrawable backgroundColorDrawable = (ColorDrawable) backgroundDrawable;
                paint.setColor(backgroundColorDrawable.getColor());
            } else if (backgroundDrawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapBackgroundDrawable = (BitmapDrawable) backgroundDrawable;
                paint.setShader(new BitmapShader(bitmapBackgroundDrawable.getBitmap(), CLAMP, CLAMP));
            }

            border.drawInner(canvas, paint);
        }

        if (srcBitmap != null) {
            srcBitmap.eraseColor(Color.TRANSPARENT);
            super.draw(this.canvas);
            paint.setShader(new BitmapShader(srcBitmap, CLAMP, CLAMP));
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            border.drawInner(canvas, paint);
        }
    }

    private static abstract class Border {
        public int shadowDx;
        public int shadowDy;
        public int shadowRadius;
        public int shadowColor;
        public int color;
        public int width;

        public final void drawOuter(Canvas canvas, Paint paint) {
            paint.setShader(null);
            if (width > 0) {
                paint.setColor(color);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(width);
                paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
                drawOuterImpl(canvas, paint);
            }
        }

        public final void drawInner(Canvas canvas, Paint paint) {
            if (width > 0) {
                paint.clearShadowLayer();
            } else {
                paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
            }

            drawInnerImpl(canvas, paint);
        }

        public abstract void calculateMetrics(RoundImageView roundImageView);

        protected abstract void drawOuterImpl(Canvas canvas, Paint paint);

        protected abstract void drawInnerImpl(Canvas canvas, Paint paint);

        protected abstract boolean isImageOpaque();
    }

    private static class CircularBorder extends Border {
        public PointF center;
        public int outerRadius;
        public int innerRadius;

        private CircularBorder() {
            center = new PointF();
        }

        @Override
        public void calculateMetrics(RoundImageView civ) {
            int paddedWidth = civ.getMeasuredWidth() - civ.getPaddingLeft() - civ.getPaddingRight();
            int paddedHeight = civ.getMeasuredHeight() - civ.getPaddingTop() - civ.getPaddingBottom();

            outerRadius = Math.min(paddedWidth, paddedHeight) / 2 - width;
            center.x = paddedWidth / 2 + civ.getPaddingLeft();
            center.y = paddedHeight / 2 + civ.getPaddingTop();
            int shadowDxAbsolute = Math.abs(shadowDx);
            int shadowDyAbsolute = Math.abs(shadowDy);
            outerRadius -= (shadowDxAbsolute > shadowDyAbsolute ? shadowDxAbsolute : shadowDyAbsolute) + shadowRadius;
            innerRadius = outerRadius - width;
        }

        @Override
        protected void drawOuterImpl(Canvas canvas, Paint paint) {
            canvas.drawCircle(center.x, center.y, outerRadius, paint);
        }

        @Override
        protected void drawInnerImpl(Canvas canvas, Paint paint) {
            canvas.drawCircle(center.x, center.y, innerRadius, paint);
        }

        @Override
        protected boolean isImageOpaque() {
            return false;
        }
    }

    private static class RectBorder extends Border {
        public int cornerRadius;
        private RectF outerBounds = new RectF();
        private RectF innerBounds = new RectF();

        public RectBorder(int cornerRadius) {
            this.cornerRadius = cornerRadius;
        }

        @Override
        public void calculateMetrics(RoundImageView civ) {
            int paddedWidth = civ.getMeasuredWidth() - civ.getPaddingLeft() - civ.getPaddingRight();
            int paddedHeight = civ.getMeasuredHeight() - civ.getPaddingTop() - civ.getPaddingBottom();

            int originX = civ.getPaddingLeft();
            int originY = civ.getPaddingTop();
            outerBounds.set(originX, originY, originX + paddedWidth, originY + paddedHeight);
            outerBounds.inset(shadowRadius + Math.abs(shadowDx) + width, shadowRadius + Math.abs(shadowDy) + width);
            innerBounds.set(outerBounds);
            //=1px to hide shadow in the inner corners
            int innerBoundsInset = Math.max(width - 1, 0);
            innerBounds.inset(innerBoundsInset, innerBoundsInset);
        }

        @Override
        protected void drawOuterImpl(Canvas canvas, Paint paint) {
            canvas.drawRoundRect(outerBounds, cornerRadius, cornerRadius, paint);
        }

        @Override
        protected void drawInnerImpl(Canvas canvas, Paint paint) {
            int innerCornerRadius = Math.max(cornerRadius - width, 1);
            canvas.drawRoundRect(innerBounds, innerCornerRadius, innerCornerRadius, paint);
        }

        @Override
        protected boolean isImageOpaque() {
            return cornerRadius == 0;
        }
    }
}
