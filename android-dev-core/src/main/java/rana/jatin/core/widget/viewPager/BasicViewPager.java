package rana.jatin.core.widget.viewPager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;

public class BasicViewPager extends ViewPager {
    private boolean swipeEnabled = true;
    private float mStartDragX;
    private OnSwipeListener mOnSwipeListener;

    public BasicViewPager(Context context) {
        super(context);
    }

    public BasicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSwipeOutListener(OnSwipeListener listener) {
        mOnSwipeListener = listener;
    }

    private void onSwipeFirst() {
        if (mOnSwipeListener !=null) {
            mOnSwipeListener.onSwipeFirst();
        }
    }

    private void onSwipeLast() {
        if (mOnSwipeListener !=null) {
            mOnSwipeListener.onSwipeLast();
        }
    }

    private void onTouchAction(MotionEvent event) {
        if (mOnSwipeListener != null) {
            mOnSwipeListener.onTouchAction(event);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!this.swipeEnabled)
        return false;
        switch(ev.getAction() & MotionEventCompat.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                mStartDragX = ev.getX();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        if (!this.swipeEnabled)
            return false;
        onTouchAction(ev);
        if(getCurrentItem()==0 || getCurrentItem()==getAdapter().getCount()-1){
            final int action = ev.getAction();
            float x = ev.getX();
            switch(action & MotionEventCompat.ACTION_MASK){
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    if (getCurrentItem()==0 && x>mStartDragX) {
                        onSwipeFirst();
                    }
                    if (getCurrentItem()==getAdapter().getCount()-1 && x<mStartDragX){
                        onSwipeLast();
                    }
                    break;
            }
        }else{
            mStartDragX=0;
        }
        return super.onTouchEvent(ev);

    }

    public interface OnSwipeListener {
        void onSwipeFirst();
        void onSwipeLast();

        void onTouchAction(MotionEvent event);
    }

    public boolean isSwipeEnabled() {
        return swipeEnabled;
    }


    public void setSwipeEnabled(boolean enabled) {
        this.swipeEnabled = enabled;
    }
}