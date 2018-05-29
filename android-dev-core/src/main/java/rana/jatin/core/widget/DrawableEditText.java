package rana.jatin.core.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import rana.jatin.core.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by jatin on 7/17/2017.
 */

public class DrawableEditText extends LinearLayout implements View.OnClickListener {

    private String typeFace = "";
    private int inputType;
    private int mLeftIcon;
    private int mLeftIconPadding;
    private int mLeftIconSize;
    private String iconText;
    private String iconTextHint;
    private ColorStateList iconTextColor = ContextCompat.getColorStateList(getContext(), R.color.white);
    private ColorStateList iconTextHintColor = ContextCompat.getColorStateList(getContext(), R.color.white);
    private int iconTextSize;
    private int mRightIcon;
    private int mRightIconPadding;
    private int mRightIconSize;
    private String gravity = "center";
    private int maxLines = 0;
    private int maxLength = 0;
    private Drawable background;
    private int backgroundColor = -1;
    private AppCompatImageView leftImageView;
    private BaseEditText editText;
    private AppCompatImageView rightImageView;

    public DrawableEditText(Context context) {
        super(context);
        inflate();
    }

    public DrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        inflate();
    }

    public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        inflate();
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
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DrawableEditText);

        for (int i = 0; i < array.getIndexCount(); ++i) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.DrawableEditText_leftIconSrc)
                mLeftIcon = array.getResourceId(attr, 0);
            else if (attr == R.styleable.DrawableEditText_leftIconPadding)
                mLeftIconPadding = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.DrawableEditText_leftIconSize)
                mLeftIconSize = array.getDimensionPixelSize(attr, 0);

            if (attr == R.styleable.DrawableEditText_rightIconSrc)
                mRightIcon = array.getResourceId(attr, 0);
            else if (attr == R.styleable.DrawableEditText_rightIconPadding)
                mRightIconPadding = array.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.DrawableEditText_rightIconSize)
                mRightIconSize = array.getDimensionPixelSize(attr, 0);

            else if (attr == R.styleable.DrawableEditText_text)
                iconText = array.getString(attr);

            else if (attr == R.styleable.DrawableEditText_textColor)
                iconTextColor = array.getColorStateList(attr);

            else if (attr == R.styleable.DrawableEditText_maxLines)
                maxLines = array.getInt(attr, 0);

            else if (attr == R.styleable.DrawableEditText_maxLength)
                maxLength = array.getInt(attr, 0);

            else if (attr == R.styleable.DrawableEditText_textHint)
                iconTextHint = array.getString(attr);

            else if (attr == R.styleable.DrawableEditText_textHintColor)
                iconTextHintColor = array.getColorStateList(attr);

            else if (attr == R.styleable.DrawableEditText_textSize)
                iconTextSize = array.getDimensionPixelSize(attr, 0);

            else if (attr == R.styleable.DrawableEditText_textGravity)
                gravity = array.getString(attr);

            else if (attr == R.styleable.DrawableEditText_typeFace)
                typeFace = array.getString(attr);

            else if (attr == R.styleable.DrawableEditText_inputType)
                inputType = array.getInt(attr,0);

            else if (attr == R.styleable.DrawableEditText_backgroundSrc) {
                TypedValue colorValue = new TypedValue();
                array.getValue(
                        R.styleable.DrawableEditText_backgroundSrc,
                        colorValue);
                if (colorValue.type == TypedValue.TYPE_REFERENCE) {
                    background = array.getDrawable(attr);
                } else {
                    backgroundColor = array.getColor(attr, -1);
                }
            }
        }

        array.recycle();
    }

    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_icon_edittext, this);
        findViews();
        setView();
    }

    private void findViews() {
        leftImageView = findViewById(R.id.iv_left);
        editText = findViewById(R.id.et);
        rightImageView = findViewById(R.id.iv_right);
    }

    private void setView() {
        setEditText();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                requestInputFocus(editText);
            }
        });
    }

    /* Request soft keyboard focus*/
    private void requestInputFocus(final EditText editText) {

        Handler mHandler = new Handler();
        mHandler.post(
                new Runnable() {
                    public void run() {
                        InputMethodManager inputMethodManager = (InputMethodManager) DrawableEditText.this.getContext().getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                        editText.requestFocus();
                    }
                });
    }

    private void setEditText() {

        editText.setText(iconText);
        editText.setTextColor(iconTextColor);
        editText.setHint(iconTextHint);
        editText.setHintTextColor(iconTextHintColor);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize);

        if (!typeFace.isEmpty()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + typeFace);
            editText.setTypeface(tf);
        }

        editText.setInputType(inputType);

        if (gravity.equalsIgnoreCase("right"))
            editText.setGravity(Gravity.END);
        else if (gravity.equalsIgnoreCase("left"))
            editText.setGravity(Gravity.START);
        else
            editText.setGravity(Gravity.CENTER);

        if (maxLines > 0) {
            editText.setMaxLines(maxLines);
            editText.setEllipsize(TextUtils.TruncateAt.END);
        }
        if (maxLength > 0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        if (background != null)
            editText.setBackground(background);
        if (backgroundColor != -1)
            editText.setBackgroundColor(backgroundColor);

        LinearLayout.LayoutParams p = new LayoutParams(mLeftIconSize,
                mLeftIconSize);
        p.setMargins(0, 0, mLeftIconPadding, 0);
        leftImageView.setImageResource(mLeftIcon);
        leftImageView.setLayoutParams(p);

        p = new LayoutParams(mRightIconSize,
                mRightIconSize);
        p.setMargins(mRightIconPadding, 0, 0, 0);
        rightImageView.setImageResource(mRightIcon);
        rightImageView.setLayoutParams(p);
    }

    public String getText() {
        if (editText != null)
            return editText.getText().toString();
        else
            return "";
    }

    public void setText(String text) {
        if (editText != null)
            editText.setText(text);
    }

    public void setFilter(InputFilter[] inputFilters) {
        editText.setFilters(inputFilters);
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(BaseEditText editText) {
        this.editText = editText;
    }

    public void setLeftIconClickListener(OnClickListener onClick) {
        leftImageView.setOnClickListener(onClick);
    }

    public void setRightIconClickListener(OnClickListener onClick) {
        rightImageView.setOnClickListener(onClick);
    }

    public AppCompatImageView getLeftImageView() {
        return leftImageView;
    }

    public AppCompatImageView getRightImageView() {
        return rightImageView;
    }

    @Override
    public void onClick(View v) {

    }
}
