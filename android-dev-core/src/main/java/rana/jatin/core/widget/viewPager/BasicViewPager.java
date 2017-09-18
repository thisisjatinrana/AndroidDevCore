package rana.jatin.core.widget.viewPager;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class BasicViewPager extends ViewPager {
    private boolean enabled=true;
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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!this.enabled)
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
        if (!this.enabled)
            return false;
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
    }
}