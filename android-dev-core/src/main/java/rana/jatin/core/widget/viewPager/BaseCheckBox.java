package rana.jatin.core.widget.viewPager;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

public class BaseCheckBox extends AppCompatCheckBox {

    private OnCheckedChangeListener listener;

    public BaseCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
        super.setOnCheckedChangeListener(listener);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.setOnCheckedChangeListener(null);
        super.onRestoreInstanceState(state);
        super.setOnCheckedChangeListener(listener);
    }

    public void setCheckedSilent(boolean checked) {
        super.setOnCheckedChangeListener(null);
        super.setChecked(checked);
        super.setOnCheckedChangeListener(listener);
    }
}
