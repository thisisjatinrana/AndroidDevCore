package rana.jatin.core.widget.nestedScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.core.widget.NestedScrollView;
/*
*  BasicNestedScrollView is a super-powered {@link android.support.v4.widget.NestedScrollView}
*/
public class BasicNestedScrollView extends NestedScrollView {
    private String TAG= BasicNestedScrollView.class.getName();
    private int prevPage = 1;
    private int nextPage =1;
    private boolean prevScrollEnabled = false;
    private boolean nextScrollEnabled = false;
    private boolean isLoading;

    public BasicNestedScrollView(Context context) {
        super(context);
    }

    public BasicNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLazyLoadListener(final LazyLoadListener lazyLoadListener){
        this.setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                lazyLoadListener.onScrolling(v, scrollX, scrollY, oldScrollX, oldScrollY);

                if (scrollY > oldScrollY) {
                    Log.i(TAG, "Scroll DOWN");
                }
                if (scrollY < oldScrollY) {
                    Log.i(TAG, "Scroll UP");
                }

                if (scrollY == 0 && !isLoading&& prevScrollEnabled) {
                    Log.i(TAG, "TOP SCROLL");
                    isLoading = true;
                    prevPage++;
                    isLoading = lazyLoadListener.onScrollPrev(prevPage, scrollY);
                }

                if (!isLoading && nextScrollEnabled && scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    isLoading = true;
                    nextPage++;
                    isLoading = lazyLoadListener.onScrollNext(nextPage, scrollX);
                    Log.i(TAG, "BOTTOM SCROLL");
                }
            }
        });
    }

    public void reset(){
        this.nextPage =1;
        this.prevPage =1;
        this.isLoading =false;
        this.prevScrollEnabled=true;
        this.nextScrollEnabled=true;
    }

    public int getPrevPage() {
        return this.prevPage;
    }

    public void setPrevPage(int page) {
        this.prevPage = page;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(int page) {
        this.nextPage = page;
    }

    public boolean isPrevScrollEnabled() {
        return this.prevScrollEnabled;
    }

    public void setPrevScroll(boolean scrollingEnabled) {
        this.prevScrollEnabled = scrollingEnabled;
    }

    public boolean isNextScrollEnabled() {
        return this.nextScrollEnabled;
    }

    public void setNextScroll(boolean scrollingEnabled) {
        this.nextScrollEnabled = scrollingEnabled;
    }
    public boolean isLoading() {
        return this.isLoading;
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }

}