package rana.jatin.core.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DimenRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import rana.jatin.core.R;
import rana.jatin.core.util.MiscUtils;

/**
 * Created by jatin on 7/17/2017.
 */

public class DrawableTextView extends RelativeLayout {

    private String typeFace = "";
    private int mLeftIcon;
    private int mLeftIconPadding;
    private int mLeftIconSize;
    private String iconText;
    private ColorStateList iconTextColor = ContextCompat.getColorStateList(getContext(), R.color.white);
    private int iconTextSize;
    private int mRightIcon;
    private int mRightIconPadding;
    private int mRightIconSize;
    private int mTopIcon;
    private int mTopIconPadding;
    private int mTopIconSize;
    private int mBottomIcon;
    private int mBottomIconPadding;
    private int mBottomIconSize;
    private boolean iconFitXY;
    private String textAlign = "center";
    private TextView textView;

    public DrawableTextView(Context context) {
        super(context);
    }


    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private AppCompatImageView rightImageView;

    private void setView() {

        if (textAlign.equalsIgnoreCase("left")) {
            setGravityLeft();
        } else if (textAlign.equalsIgnoreCase("right")) {
            setGravityRight();
        } else {
            setGravityCenter();
        }
    }

    private AppCompatImageView leftImageView;
    private AppCompatImageView bottomImageView;
    private AppCompatImageView topImageView;

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);

        for (int i = 0; i < array.getIndexCount(); ++i) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.DrawableTextView_leftIconSrc)
                mLeftIcon = array.getResourceId(attr, 0);
            else if (attr == R.styleable.DrawableTextView_leftIconPadding)
                mLeftIconPadding = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.DrawableTextView_leftIconSize)
                mLeftIconSize = array.getDimensionPixelSize(attr, 0);

            if (attr == R.styleable.DrawableTextView_rightIconSrc)
                mRightIcon = array.getResourceId(attr, 0);
            else if (attr == R.styleable.DrawableTextView_rightIconPadding)
                mRightIconPadding = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.DrawableTextView_rightIconSize)
                mRightIconSize = array.getDimensionPixelSize(attr, 0);

            if (attr == R.styleable.DrawableTextView_topIconSrc)
                mTopIcon = array.getResourceId(attr, 0);
            else if (attr == R.styleable.DrawableTextView_topIconPadding)
                mTopIconPadding = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.DrawableTextView_topIconSize)
                mTopIconSize = array.getDimensionPixelSize(attr, 0);

            if (attr == R.styleable.DrawableTextView_bottomIconSrc)
                mBottomIcon = array.getResourceId(attr, 0);
            else if (attr == R.styleable.DrawableTextView_bottomIconPadding)
                mBottomIconPadding = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.DrawableTextView_bottomIconSize)
                mBottomIconSize = array.getDimensionPixelSize(attr, 0);

            else if (attr == R.styleable.DrawableTextView_text)
                iconText = array.getString(attr);

            else if (attr == R.styleable.DrawableTextView_textColor)
                iconTextColor = array.getColorStateList(attr);

            else if (attr == R.styleable.DrawableTextView_textSize)
                iconTextSize = array.getDimensionPixelSize(attr, 0);

            else if (attr == R.styleable.DrawableTextView_iconFitXY)
                iconFitXY = array.getBoolean(attr, false);

            else if (attr == R.styleable.DrawableTextView_textAlign)
                textAlign = array.getString(attr);
            else if (attr == R.styleable.DrawableTextView_typeFace)
                typeFace = array.getString(attr);
        }

        array.recycle();
        setView();
    }

    private void setGravityRight() {
        RelativeLayout.LayoutParams p;
        rightImageView = new AppCompatImageView(getContext());
        rightImageView.setImageResource(mRightIcon);
        rightImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        rightImageView.setId(MiscUtils.generateViewId());
        p = new RelativeLayout.LayoutParams(mRightIconSize,
                mRightIconSize);
        p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        rightImageView.setLayoutParams(p);
        this.addView(rightImageView);

        textView = new TextView(getContext());
        textView.setText(iconText);
        textView.setTextColor(iconTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize);
        textView.setId(MiscUtils.generateViewId());

        if (!typeFace.isEmpty()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + typeFace);
            textView.setTypeface(tf);
        }

        p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.LEFT_OF, rightImageView.getId());
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.setMargins(mRightIconPadding, 0, 0, 0);
        textView.setLayoutParams(p);
        this.addView(textView);


        leftImageView = new AppCompatImageView(getContext());
        leftImageView.setImageResource(mLeftIcon);
        leftImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mLeftIconSize,
                mLeftIconSize);
        if (iconFitXY)
            p.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        else
            p.addRule(RelativeLayout.LEFT_OF, textView.getId());
        p.setMargins(0, 0, mLeftIconPadding, 0);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        leftImageView.setLayoutParams(p);
        this.addView(leftImageView);

        topImageView = new AppCompatImageView(getContext());
        topImageView.setImageResource(mTopIcon);
        topImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mTopIconSize,
                mTopIconSize);
        if (iconFitXY)
            p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        else
            p.addRule(RelativeLayout.ABOVE, textView.getId());
        p.setMargins(0, mTopIconPadding, 0, 0);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        topImageView.setLayoutParams(p);
        this.addView(topImageView);


        bottomImageView = new AppCompatImageView(getContext());
        bottomImageView.setImageResource(mBottomIcon);
        bottomImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mBottomIconSize,
                mBottomIconSize);
        if (iconFitXY)
            p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        else
            p.addRule(RelativeLayout.BELOW, textView.getId());
        p.setMargins(0, 0, 0, mBottomIconPadding);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bottomImageView.setLayoutParams(p);
        this.addView(bottomImageView);
    }

    private void setGravityLeft() {
        RelativeLayout.LayoutParams p;
        leftImageView = new AppCompatImageView(getContext());
        leftImageView.setImageResource(mLeftIcon);
        leftImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        leftImageView.setId(MiscUtils.generateViewId());
        p = new RelativeLayout.LayoutParams(mLeftIconSize,
                mLeftIconSize);
        p.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        leftImageView.setLayoutParams(p);
        this.addView(leftImageView);

        textView = new TextView(getContext());
        textView.setText(iconText);
        textView.setTextColor(iconTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize);
        textView.setId(MiscUtils.generateViewId());

        if (!typeFace.isEmpty()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + typeFace);
            textView.setTypeface(tf);
        }

        p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.RIGHT_OF, leftImageView.getId());
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.setMargins(mLeftIconPadding, 0, 0, 0);
        textView.setLayoutParams(p);
        this.addView(textView);


        rightImageView = new AppCompatImageView(getContext());
        rightImageView.setImageResource(mRightIcon);
        rightImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mRightIconSize,
                mRightIconSize);
        if (iconFitXY)
            p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        else
            p.addRule(RelativeLayout.RIGHT_OF, textView.getId());
        p.setMargins(mRightIconPadding, 0, 0, 0);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        rightImageView.setLayoutParams(p);
        this.addView(rightImageView);

        topImageView = new AppCompatImageView(getContext());
        topImageView.setImageResource(mTopIcon);
        topImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mTopIconSize,
                mTopIconSize);
        if (iconFitXY)
            p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        else
            p.addRule(RelativeLayout.ABOVE, textView.getId());
        p.setMargins(0, mTopIconPadding, 0, 0);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        topImageView.setLayoutParams(p);
        this.addView(topImageView);


        bottomImageView = new AppCompatImageView(getContext());
        bottomImageView.setImageResource(mBottomIcon);
        bottomImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mBottomIconSize,
                mBottomIconSize);
        if (iconFitXY)
            p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        else
            p.addRule(RelativeLayout.BELOW, textView.getId());
        p.setMargins(0, 0, 0, mBottomIconPadding);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bottomImageView.setLayoutParams(p);
        this.addView(bottomImageView);
    }

    private void setGravityCenter() {

        textView = new TextView(getContext());
        textView.setText(iconText);
        textView.setTextColor(iconTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize);
        textView.setId(MiscUtils.generateViewId());

        if (!typeFace.isEmpty()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + typeFace);
            textView.setTypeface(tf);
        }

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView.setLayoutParams(p);
        this.addView(textView);

        leftImageView = new AppCompatImageView(getContext());
        leftImageView.setImageResource(mLeftIcon);
        leftImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mLeftIconSize,
                mLeftIconSize);
        if (iconFitXY)
            p.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        else
            p.addRule(RelativeLayout.LEFT_OF, textView.getId());
        p.setMargins(0, 0, mLeftIconPadding, 0);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        leftImageView.setLayoutParams(p);
        this.addView(leftImageView);

        rightImageView = new AppCompatImageView(getContext());
        rightImageView.setImageResource(mRightIcon);
        rightImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mRightIconSize,
                mRightIconSize);
        if (iconFitXY)
            p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        else
            p.addRule(RelativeLayout.RIGHT_OF, textView.getId());
        p.setMargins(mRightIconPadding, 0, 0, 0);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        rightImageView.setLayoutParams(p);
        this.addView(rightImageView);

        topImageView = new AppCompatImageView(getContext());
        topImageView.setImageResource(mTopIcon);
        topImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mTopIconSize,
                mTopIconSize);
        if (iconFitXY)
            p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        else
            p.addRule(RelativeLayout.ABOVE, textView.getId());
        p.setMargins(0, mTopIconPadding, 0, 0);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        topImageView.setLayoutParams(p);
        this.addView(topImageView);


        bottomImageView = new AppCompatImageView(getContext());
        bottomImageView.setImageResource(mBottomIcon);
        bottomImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mBottomIconSize,
                mBottomIconSize);
        if (iconFitXY)
            p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        else
            p.addRule(RelativeLayout.BELOW, textView.getId());
        p.setMargins(0, 0, 0, mBottomIconPadding);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bottomImageView.setLayoutParams(p);
        this.addView(bottomImageView);
    }

    public void setText(String text){
        textView.setText(text);
    }

    public AppCompatImageView getRightImageView() {
        return rightImageView;
    }

    public AppCompatImageView getLeftImageView() {
        return leftImageView;
    }

    public AppCompatImageView getBottomImageView() {
        return bottomImageView;
    }

    public AppCompatImageView getTopImageView() {
        return topImageView;
    }

    public void setIconPadding(@DimenRes int paddingValue) {
        int padding = getContext().getResources().getDimensionPixelSize(paddingValue);
        this.topImageView.setPadding(padding, padding, padding, padding);
        this.bottomImageView.setPadding(padding, padding, padding, padding);
        this.leftImageView.setPadding(padding, padding, padding, padding);
        this.rightImageView.setPadding(padding, padding, padding, padding);
    }

    public void setLeftIconPadding(@DimenRes int paddingValue) {
        int padding = getContext().getResources().getDimensionPixelSize(paddingValue);
        this.leftImageView.setPadding(padding, padding, padding, padding);
    }

    public void setRightIconPadding(@DimenRes int paddingValue) {
        int padding = getContext().getResources().getDimensionPixelSize(paddingValue);
        this.rightImageView.setPadding(padding, padding, padding, padding);
    }

    public void setTopIconPadding(@DimenRes int paddingValue) {
        int padding = getContext().getResources().getDimensionPixelSize(paddingValue);
        this.topImageView.setPadding(padding, padding, padding, padding);
    }

    public void setBottomIconPadding(@DimenRes int paddingValue) {
        int padding = getContext().getResources().getDimensionPixelSize(paddingValue);
        this.bottomImageView.setPadding(padding, padding, padding, padding);
    }
}
