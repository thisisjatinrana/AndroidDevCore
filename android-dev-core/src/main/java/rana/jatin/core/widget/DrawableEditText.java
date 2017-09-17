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
import android.text.InputType;
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

    private String typeFace="GothamBook.ttf";
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
    private AppCompatImageView ivLeft;
    private BasicEditText et;
    private AppCompatImageView ivRight;

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
        ivLeft = (AppCompatImageView) findViewById(R.id.iv_left);
        et = (BasicEditText) findViewById(R.id.et);
        ivRight = (AppCompatImageView) findViewById(R.id.iv_right);
    }

    private void setView() {
        setEditText();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                requestInputFocus(et);
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
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/"+ typeFace);
        et.setText(iconText);
        et.setTextColor(iconTextColor);
        et.setHint(iconTextHint);
        et.setHintTextColor(iconTextHintColor);
        et.setTextSize(TypedValue.COMPLEX_UNIT_PX, iconTextSize);
        et.setTypeface(tf);
        et.setInputType(inputType);

        if (gravity.equalsIgnoreCase("right"))
            et.setGravity(Gravity.END);
        else if (gravity.equalsIgnoreCase("left"))
            et.setGravity(Gravity.START);
        else
            et.setGravity(Gravity.CENTER);

        if (maxLines > 0) {
            et.setMaxLines(maxLines);
            et.setEllipsize(TextUtils.TruncateAt.END);
        }
        if (maxLength > 0) {
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        if (background != null)
            et.setBackground(background);
        if (backgroundColor != -1)
            et.setBackgroundColor(backgroundColor);

        LinearLayout.LayoutParams p = new LayoutParams(mLeftIconSize,
                mLeftIconSize);
        p.setMargins(0, 0, mLeftIconPadding, 0);
        ivLeft.setImageResource(mLeftIcon);
        ivLeft.setLayoutParams(p);

        p = new LayoutParams(mRightIconSize,
                mRightIconSize);
        p.setMargins(mRightIconPadding, 0, 0, 0);
        ivRight.setImageResource(mRightIcon);
        ivRight.setLayoutParams(p);
    }

    public String getText() {
        if (et != null)
            return et.getText().toString();
        else
            return "";
    }

    public void setText(String text) {
        if (et != null)
            et.setText(text);
    }

    public void setFilter(InputFilter[] inputFilters) {
        et.setFilters(inputFilters);
    }

    public EditText getEditText() {
        return et;
    }

    public void setEditText(BasicEditText editText) {
        this.et = editText;
    }

    public void setLeftOnClick(OnClickListener onClick) {
        ivLeft.setOnClickListener(onClick);
    }

    public void setRightOnClick(OnClickListener onClick) {
        ivRight.setOnClickListener(onClick);
    }

    @Override
    public void onClick(View v) {

    }
}
