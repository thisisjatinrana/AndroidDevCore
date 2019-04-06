package rana.jatin.core.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import rana.jatin.core.R;
import rana.jatin.core.util.MiscUtils;

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
    private AppCompatImageView rightImageView;
    private TextView rightTextView;
    private TextView centerTextView;
    private AppCompatImageView leftImageView;
    private AppCompatImageView centerImageView;
    private TextView leftTextView;

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

        centerTextView = new TextView(getContext());
        centerTextView.setText(centericonText);
        centerTextView.setTextColor(centerTextColor);
        centerTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCenterTextSize);
        centerTextView.setId(MiscUtils.generateViewId());

        if (!centerTypeFace.isEmpty()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/" + centerTypeFace);
            centerTextView.setTypeface(tf);
        }

        LayoutParams p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        centerTextView.setLayoutParams(p);
        this.addView(centerTextView);

        centerImageView = new AppCompatImageView(getContext());
        centerImageView.setImageResource(mcenterIcon);
        centerImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new LayoutParams(mcenterIconSize,
                mcenterIconSize);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        centerImageView.setLayoutParams(p);
        this.addView(centerImageView);

        leftImageView = new AppCompatImageView(getContext());
        leftImageView.setImageResource(mLeftIcon);
        leftImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        leftImageView.setId(MiscUtils.generateViewId());
        p = new LayoutParams(mLeftIconSize,
                mLeftIconSize);
        p.addRule(RelativeLayout.ALIGN_PARENT_START);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        leftImageView.setLayoutParams(p);
        this.addView(leftImageView);


        leftTextView = new TextView(getContext());
        leftTextView.setText(lefticonText);
        leftTextView.setTextColor(leftTextColor);
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);

        if (!leftTypeFace.isEmpty()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/" + leftTypeFace);
            leftTextView.setTypeface(tf);
        }

        p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.END_OF, leftImageView.getId());
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            p.setMarginStart(padding);
        }
        leftTextView.setLayoutParams(p);
        this.addView(leftTextView);


        rightImageView = new AppCompatImageView(getContext());
        rightImageView.setImageResource(mRightIcon);
        rightImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        rightImageView.setId(MiscUtils.generateViewId());
        p = new LayoutParams(mRightIconSize,
                mRightIconSize);
        p.addRule(RelativeLayout.ALIGN_PARENT_END);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        rightImageView.setLayoutParams(p);
        this.addView(rightImageView);

        rightTextView = new TextView(getContext());
        rightTextView.setText(righticonText);
        rightTextView.setTextColor(rightTextColor);
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);

        if (!rightTypeFace.isEmpty()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/" + rightTypeFace);
            rightTextView.setTypeface(tf);
        }

        p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.START_OF, rightImageView.getId());
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.setMargins(0, 0, padding, 0);
        rightTextView.setLayoutParams(p);
        this.addView(rightTextView);

    }

    /*
    * set click listener on left view
    * @param onClick listener
    */
    public void setLeftViewClickListener(OnClickListener onClick) {
        leftImageView.setOnClickListener(onClick);
        leftTextView.setOnClickListener(onClick);
    }

    /*
    * set click listener on right view
    * @param onClick listener
    */
    public void setRightViewClickListener(OnClickListener onClick) {
        rightImageView.setOnClickListener(onClick);
        rightTextView.setOnClickListener(onClick);
    }

    /*
    * set click listener of center view
    * @param onClick listener
    */
    public void setCenterViewClickListener(OnClickListener onClick) {
        centerImageView.setOnClickListener(onClick);
        centerTextView.setOnClickListener(onClick);
    }

    public void setCenterText(String text) {
        centerTextView.setText(text);
    }

    public void setLeftText(String text) {
        leftTextView.setText(text);
    }

    public void setRightText(String text) {
        rightTextView.setText(text);
    }

    public void setCenterTextColor(int color) {
        centerTextView.setTextColor(color);
    }

    public void setLeftTextColor(int color) {
        leftTextView.setTextColor(color);
    }

    public void setRightTextColor(int color) {
        rightTextView.setTextColor(color);
    }

    public void setLeftIcon(int id) {
        leftImageView.setImageResource(id);
    }

    public void setRightIcon(int id) {
        rightImageView.setImageResource(id);
    }

    public void setCenterIcon(int id) {
        centerImageView.setImageResource(id);
    }

    public TextView getRightTextView() {
        return rightTextView;
    }

    public TextView getCenterTextView() {
        return centerTextView;
    }

    public TextView getLeftTextView() {
        return leftTextView;
    }

    public AppCompatImageView getRightImageView() {
        return rightImageView;
    }

    public AppCompatImageView getLeftImageView() {
        return leftImageView;
    }

    public AppCompatImageView getCenterImageView() {
        return centerImageView;
    }
}
