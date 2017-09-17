package rana.jatin.core.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import rana.jatin.core.R;

/**
 * Created by jatin on 7/17/2017.
 */

public class DrawableTextView extends RelativeLayout {

    private String typeFace="GothamBook.ttf";
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
    private boolean alignEnd;
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

            else if (attr == R.styleable.DrawableTextView_alignIconCorner)
                alignEnd = array.getBoolean(attr, false);

            else if (attr == R.styleable.DrawableTextView_textAlign)
                textAlign = array.getString(attr);
            else if (attr == R.styleable.DrawableTextView_typeFace)
                typeFace = array.getString(attr);
        }

        array.recycle();
        setView();
    }

    private void setView() {

        if (textAlign.equalsIgnoreCase("left")) {
            setGravityLeft();
        } else if (textAlign.equalsIgnoreCase("right")) {
            setGravityRight();
        } else {
            setGravityCenter();
        }
    }

    private void setGravityRight() {
        RelativeLayout.LayoutParams p;
        AppCompatImageView rightView = new AppCompatImageView(getContext());
        rightView.setImageResource(mRightIcon);
        rightView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        rightView.setId(R.id.image);
        p = new RelativeLayout.LayoutParams(mRightIconSize,
                mRightIconSize);
        p.addRule(RelativeLayout.ALIGN_PARENT_END);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        rightView.setLayoutParams(p);
        this.addView(rightView);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/"+ typeFace);
        textView = new TextView(getContext());
        textView.setText(iconText);
        textView.setTextColor(iconTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize);
        textView.setId(R.id.tv_textview);
        textView.setTypeface(tf);
        p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.START_OF, rightView.getId());
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.setMargins(mRightIconPadding, 0, 0, 0);
        textView.setLayoutParams(p);
        this.addView(textView);


        AppCompatImageView leftView = new AppCompatImageView(getContext());
        leftView.setImageResource(mLeftIcon);
        leftView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mLeftIconSize,
                mLeftIconSize);
        if (alignEnd)
            p.addRule(RelativeLayout.ALIGN_PARENT_START);
        else
            p.addRule(RelativeLayout.START_OF, textView.getId());
        p.setMargins(0, 0, mLeftIconPadding, 0);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        leftView.setLayoutParams(p);
        this.addView(leftView);

        AppCompatImageView topView = new AppCompatImageView(getContext());
        topView.setImageResource(mTopIcon);
        topView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mTopIconSize,
                mTopIconSize);
        if (alignEnd)
            p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        else
            p.addRule(RelativeLayout.ABOVE, textView.getId());
        p.setMargins(0, mTopIconPadding, 0, 0);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        topView.setLayoutParams(p);
        this.addView(topView);


        AppCompatImageView bottomView = new AppCompatImageView(getContext());
        bottomView.setImageResource(mBottomIcon);
        bottomView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mBottomIconSize,
                mBottomIconSize);
        if (alignEnd)
            p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        else
            p.addRule(RelativeLayout.BELOW, textView.getId());
        p.setMargins(0, 0, 0, mBottomIconPadding);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bottomView.setLayoutParams(p);
        this.addView(bottomView);
    }

    private void setGravityLeft() {
        RelativeLayout.LayoutParams p;
        AppCompatImageView leftView = new AppCompatImageView(getContext());
        leftView.setImageResource(mLeftIcon);
        leftView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        leftView.setId(R.id.image);
        p = new RelativeLayout.LayoutParams(mLeftIconSize,
                mLeftIconSize);
        p.addRule(RelativeLayout.ALIGN_PARENT_START);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        leftView.setLayoutParams(p);
        this.addView(leftView);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/"+ typeFace);
        textView = new TextView(getContext());
        textView.setText(iconText);
        textView.setTextColor(iconTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize);
        textView.setId(R.id.tv_textview);
        textView.setTypeface(tf);
        p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.END_OF, leftView.getId());
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        p.setMargins(mLeftIconPadding, 0, 0, 0);
        textView.setLayoutParams(p);
        this.addView(textView);


        AppCompatImageView rightView = new AppCompatImageView(getContext());
        rightView.setImageResource(mRightIcon);
        rightView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mRightIconSize,
                mRightIconSize);
        if (alignEnd)
            p.addRule(RelativeLayout.ALIGN_PARENT_END);
        else
            p.addRule(RelativeLayout.END_OF, textView.getId());
        p.setMargins(mRightIconPadding, 0, 0, 0);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        rightView.setLayoutParams(p);
        this.addView(rightView);

        AppCompatImageView topView = new AppCompatImageView(getContext());
        topView.setImageResource(mTopIcon);
        topView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mTopIconSize,
                mTopIconSize);
        if (alignEnd)
            p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        else
            p.addRule(RelativeLayout.ABOVE, textView.getId());
        p.setMargins(0, mTopIconPadding, 0, 0);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        topView.setLayoutParams(p);
        this.addView(topView);


        AppCompatImageView bottomView = new AppCompatImageView(getContext());
        bottomView.setImageResource(mBottomIcon);
        bottomView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mBottomIconSize,
                mBottomIconSize);
        if (alignEnd)
            p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        else
            p.addRule(RelativeLayout.BELOW, textView.getId());
        p.setMargins(0, 0, 0, mBottomIconPadding);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bottomView.setLayoutParams(p);
        this.addView(bottomView);
    }

    private void setGravityCenter() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/"+ typeFace);
        textView = new TextView(getContext());
        textView.setText(iconText);
        textView.setTextColor(iconTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize);
        textView.setId(R.id.tv_textview);
        textView.setTypeface(tf);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView.setLayoutParams(p);
        this.addView(textView);

        AppCompatImageView leftView = new AppCompatImageView(getContext());
        leftView.setImageResource(mLeftIcon);
        leftView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mLeftIconSize,
                mLeftIconSize);
        if (alignEnd)
            p.addRule(RelativeLayout.ALIGN_PARENT_START);
        else
            p.addRule(RelativeLayout.START_OF, R.id.tv_textview);
        p.setMargins(0, 0, mLeftIconPadding, 0);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        leftView.setLayoutParams(p);
        this.addView(leftView);

        AppCompatImageView rightView = new AppCompatImageView(getContext());
        rightView.setImageResource(mRightIcon);
        rightView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mRightIconSize,
                mRightIconSize);
        if (alignEnd)
            p.addRule(RelativeLayout.ALIGN_PARENT_END);
        else
            p.addRule(RelativeLayout.END_OF, R.id.tv_textview);
        p.setMargins(mRightIconPadding, 0, 0, 0);
        p.addRule(RelativeLayout.CENTER_VERTICAL);
        rightView.setLayoutParams(p);
        this.addView(rightView);

        AppCompatImageView topView = new AppCompatImageView(getContext());
        topView.setImageResource(mTopIcon);
        topView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mTopIconSize,
                mTopIconSize);
        if (alignEnd)
            p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        else
            p.addRule(RelativeLayout.ABOVE, textView.getId());
        p.setMargins(0, mTopIconPadding, 0, 0);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        topView.setLayoutParams(p);
        this.addView(topView);


        AppCompatImageView bottomView = new AppCompatImageView(getContext());
        bottomView.setImageResource(mBottomIcon);
        bottomView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p = new RelativeLayout.LayoutParams(mBottomIconSize,
                mBottomIconSize);
        if (alignEnd)
            p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        else
            p.addRule(RelativeLayout.BELOW, textView.getId());
        p.setMargins(0, 0, 0, mBottomIconPadding);
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bottomView.setLayoutParams(p);
        this.addView(bottomView);
    }

    public void setText(String text){
        textView.setText(text);
    }
}
