package rana.jatin.core.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import rana.jatin.core.R;
import rana.jatin.core.util.Util;

/**
 * A super powered action bar which supports left,right,center text view with drawables
 */

public class ActionBar extends RelativeLayout {

    private String leftTypeFace = "";
    private int mLeftIcon;
    private int mLeftIconSize;
    private int mLeftTextSize;
    private String lefticonText;
    private ColorStateList leftTextColor = ContextCompat.getColorStateList(getContext(), R.color.black);

    private String rightTypeFace = "";
    private int mRightIcon;
    private int mRightIconSize;
    private int mRightTextSize;
    private String righticonText;
    private ColorStateList rightTextColor = ContextCompat.getColorStateList(getContext(), R.color.black);

    private String centerTypeFace = "";
    private int mcenterIcon;
    private int mcenterIconSize;
    private int mCenterTextSize;
    private String centericonText;
    private ColorStateList centerTextColor = ContextCompat.getColorStateList(getContext(), R.color.black);

    private int padding = 20;
    private AppCompatImageView ivRight;
    private TextView tvRight;
    private TextView tvCenter;
    private AppCompatImageView ivLeft;
    private AppCompatImageView ivCenter;
    private TextView tvLeft;

    public ActionBar(Context context) {
        super(context);
    }

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ActionBar);

        for (int i = 0; i < array.getIndexCount(); ++i) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.ActionBar_leftIconSrc)
                mLeftIcon = array.getResourceId(attr, 0);
            else if (attr == R.styleable.ActionBar_leftIconSize)
                mLeftIconSize = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.ActionBar_leftText)
                lefticonText = array.getString(attr);
            else if (attr == R.styleable.ActionBar_leftTextColor)
                leftTextColor = array.getColorStateList(attr);
            else if (attr == R.styleable.ActionBar_leftTextSize)
                mLeftTextSize = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.ActionBar_rightIconSrc)
                mRightIcon = array.getResourceId(attr, 0);
            else if (attr == R.styleable.ActionBar_rightIconSize)
                mRightIconSize = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.ActionBar_rightText)
                righticonText = array.getString(attr);
            else if (attr == R.styleable.ActionBar_rightTextColor)
                rightTextColor = array.getColorStateList(attr);
            else if (attr == R.styleable.ActionBar_rightTextSize)
                mRightTextSize = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.ActionBar_centerIconSrc)
                mcenterIcon = array.getResourceId(attr, 0);
            else if (attr == R.styleable.ActionBar_centerIconSize)
                mcenterIconSize = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.ActionBar_centerText)
                centericonText = array.getString(attr);
            else if (attr == R.styleable.ActionBar_centerTextColor)
                centerTextColor = array.getColorStateList(attr);
            else if (attr == R.styleable.ActionBar_centerTextSize)
                mCenterTextSize = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.ActionBar_centerTextTypeFace)
                centerTypeFace = array.getString(attr);
            else if (attr == R.styleable.ActionBar_leftTextTypeFace)
                leftTypeFace = array.getString(attr);
            else if (attr == R.styleable.ActionBar_rightTextTypeFace)
                rightTypeFace = array.getString(attr);
        }
        array.recycle();
        setView();
    }

    private void setView() {

        tvCenter = new TextView(getContext());
        tvCenter.setText(centericonText);
        tvCenter.setTextColor(centerTextColor);
        tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCenterTextSize);
        tvCenter.setId(Util.getInstance().generateViewId());

        if (!centerTypeFace.isEmpty()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/" + centerTypeFace);
            tvCenter.setTypeface(tf);
        }

        LayoutParams p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        tvCenter.setLayoutParams(p);
        this.addView(tvCenter);

        ivCenter = new AppCompatImageView(getContext());
        ivCenter.setImageResource(mcenterIcon);
        ivCenter.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new LayoutParams(mcenterIconSize,
                mcenterIconSize);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        ivCenter.setLayoutParams(p);
        this.addView(ivCenter);

        ivLeft = new AppCompatImageView(getContext());
        ivLeft.setImageResource(mLeftIcon);
        ivLeft.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ivLeft.setId(Util.getInstance().generateViewId());
        p = new LayoutParams(mLeftIconSize,
                mLeftIconSize);
        p.addRule(RelativeLayout.ALIGN_PARENT_START);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        ivLeft.setLayoutParams(p);
        this.addView(ivLeft);


        tvLeft = new TextView(getContext());
        tvLeft.setText(lefticonText);
        tvLeft.setTextColor(leftTextColor);
        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);

        if (!leftTypeFace.isEmpty()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/" + leftTypeFace);
            tvLeft.setTypeface(tf);
        }

        p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.END_OF, ivLeft.getId());
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.setMarginStart(padding);
        tvLeft.setLayoutParams(p);
        this.addView(tvLeft);


        ivRight = new AppCompatImageView(getContext());
        ivRight.setImageResource(mRightIcon);
        ivRight.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ivRight.setId(Util.getInstance().generateViewId());
        p = new LayoutParams(mRightIconSize,
                mRightIconSize);
        p.addRule(RelativeLayout.ALIGN_PARENT_END);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        ivRight.setLayoutParams(p);
        this.addView(ivRight);

        tvRight = new TextView(getContext());
        tvRight.setText(righticonText);
        tvRight.setTextColor(rightTextColor);
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);

        if (!rightTypeFace.isEmpty()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/" + rightTypeFace);
            tvRight.setTypeface(tf);
        }

        p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.START_OF, ivRight.getId());
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.setMargins(0, 0, padding, 0);
        tvRight.setLayoutParams(p);
        this.addView(tvRight);

    }

    /*
    * set click listener on left view
    * @param onClick listener
    */
    public void setLeftClickListener(OnClickListener onClick) {
        ivLeft.setOnClickListener(onClick);
        tvLeft.setOnClickListener(onClick);
    }

    /*
    * set click listener on right view
    * @param onClick listener
    */
    public void setRightClickListener(OnClickListener onClick) {
        ivRight.setOnClickListener(onClick);
        tvRight.setOnClickListener(onClick);
    }

    /*
    * set click listener of center view
    * @param onClick listener
    */
    public void setCenterClickListener(OnClickListener onClick) {
        ivCenter.setOnClickListener(onClick);
        tvCenter.setOnClickListener(onClick);
    }

    public void setCenterText(String text) {
        tvCenter.setText(text);
    }

    public void setLeftText(String text) {
        tvLeft.setText(text);
    }

    public void setRightText(String text) {
        tvRight.setText(text);
    }

    public void setCenterTextColor(int color) {
        tvCenter.setTextColor(color);
    }

    public void setLeftTextColor(int color) {
        tvLeft.setTextColor(color);
    }

    public void setRightTextColor(int color) {
        tvRight.setTextColor(color);
    }

    public void setLeftIcon(int id) {
        ivLeft.setImageResource(id);
    }

    public void setRightIcon(int id) {
        ivRight.setImageResource(id);
    }

    public void setCenterIcon(int id) {
        ivCenter.setImageResource(id);
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public TextView getTvCenter() {
        return tvCenter;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }
}
