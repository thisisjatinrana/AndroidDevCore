package rana.jatin.core.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;

/*
*  BasicRecyclerView is a super-powered {@link android.support.v7.widget.RecyclerView}
*/
public class BasicRecyclerView extends RecyclerView {

    private View emptyView;
    private OnSwipeHelper onSwipeHelper;
    private LazyLoadingHelper lazyLoadingHelper;
    final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkIfEmpty();
        }
    };

    public BasicRecyclerView(Context context) {
        super(context);
    }

    public BasicRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    void checkIfEmpty() {
        if (emptyView != null && getAdapter() != null) {
            emptyView.setVisibility(getAdapter().getItemCount() > 0 ? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    public void setEmptyView(@Nullable View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener)
    {
        onSwipeHelper = new OnSwipeHelper(onSwipeListener);
        ItemTouchHelper helper = new ItemTouchHelper(onSwipeHelper);
        helper.attachToRecyclerView(this);
    }

    public void setLazyLoadListener(LazyLoadListener lazyLoadListener){
        lazyLoadingHelper =new LazyLoadingHelper(lazyLoadListener);
    }

    public void resetLazyLoadListener() {
        lazyLoadingHelper.reset();
    }

    public void isSwipeEnabled(boolean isEnable){
        onSwipeHelper.setSwipeEnabled(isEnable);
    }
    public void isLongPressDragEnabled(Boolean isEnable){
        onSwipeHelper.isLongPressDragEnabled(isEnable);
    }

    public void isScrollingEnabled(boolean isEnable){
        lazyLoadingHelper.setPrevScroll(isEnable);
    }
}
