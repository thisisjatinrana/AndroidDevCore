package rana.jatin.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import rana.jatin.core.R;


public class BaseButton extends AppCompatButton {

    private String typeFace = "";

    public BaseButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
        setTypeFace();
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
        setTypeFace();
    }

    public BaseButton(Context context) {
        super(context);
    }
    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BaseButton);

        for (int i = 0; i < array.getIndexCount(); ++i) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.BaseButton_typeFace)
                typeFace = array.getString(attr);
        }
        array.recycle();
    }

    private void setTypeFace() {
        if (!isInEditMode()) {
            if (!typeFace.isEmpty()) {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                        "fonts/" + typeFace);
                setTypeface(tf);
            }
        }
    }
}
