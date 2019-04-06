package rana.jatin.core.widget.recyclerview.swipeScrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import rana.jatin.core.R;
import rana.jatin.core.widget.recyclerview.swipeScrollview.transform.SwipeScrollItemTransformer;
import rana.jatin.core.widget.recyclerview.swipeScrollview.util.ScrollListenerAdapter;


@SuppressWarnings("unchecked")
public class SwipeScrollView extends RecyclerView {

    public static final int NO_POSITION = SwipeScrollLayoutManager.NO_POSITION;
    private static final int DEFAULT_ORIENTATION = rana.jatin.core.widget.recyclerview.swipeScrollview.Orientation.HORIZONTAL.ordinal();
    private int lastItemThreshold = 0;
    private int firstItemThreshold = 0;
    private int pageFirst = 0, pageLast = 1;
    private boolean canLoadPrev = true, canLoadNext = true;
    private boolean isLoadingPrev, isLoadingNext;
    private SwipeScrollLayoutManager layoutManager;

    private List<ScrollStateChangeListener> scrollStateChangeListeners;
    private List<OnItemChangedListener> onItemChangedListeners;

    public SwipeScrollView(Context context) {
        super(context);
        init(null);
    }

    public SwipeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SwipeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        scrollStateChangeListeners = new ArrayList<>();
        onItemChangedListeners = new ArrayList<>();

        int orientation = DEFAULT_ORIENTATION;
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SwipeScrollView);
            orientation = ta.getInt(R.styleable.SwipeScrollView_ssv_orientation, DEFAULT_ORIENTATION);
            ta.recycle();
        }

        layoutManager = new SwipeScrollLayoutManager(
                getContext(), new ScrollStateListener(),
                rana.jatin.core.widget.recyclerview.swipeScrollview.Orientation.values()[orientation]);
        setLayoutManager(layoutManager);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof SwipeScrollLayoutManager) {
            super.setLayoutManager(layout);
        } else {
            // throw new IllegalArgumentException(getContext().getString(R.string.dsv_ex_msg_dont_set_lm));
        }
    }


    @Override
    public boolean fling(int velocityX, int velocityY) {
        boolean isFling = super.fling(velocityX, velocityY);
        if (isFling) {
            layoutManager.onFling(velocityX, velocityY);
        } else {
            layoutManager.returnToCurrentPosition();
        }
        return isFling;
    }

    @Nullable
    public ViewHolder getViewHolder(int position) {
        View view = layoutManager.findViewByPosition(position);
        return view != null ? getChildViewHolder(view) : null;
    }

    /**
     * @return adapter position of the current item or -1 if nothing is selected
     */
    public int getCurrentItem() {
        return layoutManager.getCurrentPosition();
    }

    public void setItemTransformer(SwipeScrollItemTransformer transformer) {
        layoutManager.setItemTransformer(transformer);
    }

    public void setItemTransitionTimeMillis(@IntRange(from = 10) int millis) {
        layoutManager.setTimeForItemSettle(millis);
    }

    public void setOrientation(rana.jatin.core.widget.recyclerview.swipeScrollview.Orientation orientation) {
        layoutManager.setOrientation(orientation);
    }

    public void setOffscreenItems(int items) {
        layoutManager.setOffscreenItems(items);
    }

    public void addScrollStateChangeListener(@NonNull ScrollStateChangeListener<?> scrollStateChangeListener) {
        scrollStateChangeListeners.add(scrollStateChangeListener);
    }

    public void addScrollListener(@NonNull ScrollListener<?> scrollListener) {
        addScrollStateChangeListener(new ScrollListenerAdapter(scrollListener));
    }

    public void addOnItemChangedListener(@NonNull OnItemChangedListener<?> onItemChangedListener) {
        onItemChangedListeners.add(onItemChangedListener);
    }

    public void removeScrollStateChangeListener(@NonNull ScrollStateChangeListener<?> scrollStateChangeListener) {
        scrollStateChangeListeners.remove(scrollStateChangeListener);
    }

    public void removeScrollListener(@NonNull ScrollListener<?> scrollListener) {
        removeScrollStateChangeListener(new ScrollListenerAdapter<>(scrollListener));
    }

    public void removeItemChangedListener(@NonNull OnItemChangedListener<?> onItemChangedListener) {
        onItemChangedListeners.remove(onItemChangedListener);
    }

    private void notifyScrollStart(ViewHolder holder, int current) {
        for (ScrollStateChangeListener listener : scrollStateChangeListeners) {
            listener.onScrollStart(holder, current);
        }
    }

    private void notifyScrollEnd(ViewHolder holder, int current) {
        for (ScrollStateChangeListener listener : scrollStateChangeListeners) {
            listener.onScrollEnd(holder, current);
        }
    }

    private void notifyScrolFirst(ViewHolder holder, int current) {
        for (ScrollStateChangeListener listener : scrollStateChangeListeners) {
            pageFirst++;
            isLoadingPrev = listener.onScrollFirstItem(holder, pageFirst, current);
        }
    }

    private void notifyScrollLast(ViewHolder holder, int current) {
        for (ScrollStateChangeListener listener : scrollStateChangeListeners) {
            pageLast++;
            isLoadingNext = listener.onScrollLastItem(holder, pageLast, current);
        }
    }

    private void notifyScroll(float position, ViewHolder currentHolder, ViewHolder newHolder) {
        for (ScrollStateChangeListener listener : scrollStateChangeListeners) {
            listener.onScroll(position, currentHolder, newHolder);
        }
    }

    private void notifyCurrentItemChanged(ViewHolder holder, int current) {
        for (OnItemChangedListener listener : onItemChangedListeners) {
            listener.onCurrentItemChanged(holder, current);
        }
    }

    private void notifyCurrentItemChanged() {
        if (onItemChangedListeners.isEmpty()) {
            return;
        }
        int current = layoutManager.getCurrentPosition();
        ViewHolder currentHolder = getViewHolder(current);
        notifyCurrentItemChanged(currentHolder, current);
    }

    public int getLastItemThreshold() {
        return this.lastItemThreshold;
    }

    public void setLastItemThreshold(int lastItemThreshold) {
        this.lastItemThreshold = lastItemThreshold;
    }

    public int getFirstItemThreshold() {
        return this.firstItemThreshold;
    }

    public void setFirstItemThreshold(int firstItemThreshold) {
        this.firstItemThreshold = firstItemThreshold;
    }

    public int getPageFirst() {
        return this.pageFirst;
    }

    public void setPageFirst(int pageFirst) {
        this.pageFirst = pageFirst;
    }

    public int getPageLast() {
        return this.pageLast;
    }

    public void setPageLast(int pageLast) {
        this.pageLast = pageLast;
    }

    public boolean canLoadPrev() {
        return this.canLoadPrev;
    }

    public void setCanLoadPrev(boolean canLoadPrev) {
        this.canLoadPrev = canLoadPrev;
    }

    public boolean canLoadNext() {
        return this.canLoadNext;
    }

    public void setCanLoadNext(boolean canLoadNext) {
        this.canLoadNext = canLoadNext;
    }

    public boolean isLoadingPrev() {
        return this.isLoadingPrev;
    }

    public void setIsLoadingPrev(boolean loadingPrev) {
        this.isLoadingPrev = loadingPrev;
    }

    public boolean isLoadingNext() {
        return this.isLoadingNext;
    }

    public void setisLoadingNext(boolean loadingNext) {
        this.isLoadingNext = loadingNext;
    }

    public void setLoading(boolean loading) {
        this.isLoadingNext = loading;
        this.isLoadingPrev = loading;
    }

    private void checkLazyLoading(ViewHolder holder, int current) {
        int visibleItemCount = getLayoutManager().getChildCount();
        int totalItemCount = getLayoutManager().getItemCount();
        int pastVisiblesItems = current;

        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount && isLoadingNext == false && canLoadNext == true) {
            notifyScrollLast(holder, current);
        }

        if (pastVisiblesItems == 0 && isLoadingPrev == false && canLoadPrev == true) {
            notifyScrolFirst(holder, current);
        }

    }

    public boolean isScrollingEnabled() {
        return layoutManager.isScrollingEnabled();
    }

    public void setScrollingEnabled(boolean scrollingEnabled) {
        layoutManager.setScrollingEnabled(scrollingEnabled);
    }

    public interface ScrollStateChangeListener<T extends ViewHolder> {

        void onScrollStart(@NonNull T currentItemHolder, int adapterPosition);

        void onScrollEnd(@NonNull T currentItemHolder, int adapterPosition);

        void onScroll(float scrollPosition, @NonNull T currentHolder, @NonNull T newCurrent);

        boolean onScrollFirstItem(ViewHolder holder, int page, int adapterPosition);

        boolean onScrollLastItem(ViewHolder holder, int page, int adapterPosition);

    }

    public interface ScrollListener<T extends ViewHolder> {

        void onScroll(float scrollPosition, @NonNull T currentHolder, @NonNull T newCurrent);
    }

    public interface OnItemChangedListener<T extends ViewHolder> {
        /*
         * This method will be also triggered when view appears on the screen for the first time.
         * If data set is empty, viewHolder will be null and adapterPosition will be NO_POSITION
         */
        void onCurrentItemChanged(@Nullable T viewHolder, int adapterPosition);
    }

    private class ScrollStateListener implements SwipeScrollLayoutManager.ScrollStateListener {

        @Override
        public void onIsBoundReachedFlagChange(boolean isBoundReached) {
            setOverScrollMode(isBoundReached ? OVER_SCROLL_ALWAYS : OVER_SCROLL_NEVER);
        }

        @Override
        public void onScrollStart() {
            if (scrollStateChangeListeners.isEmpty()) {
                return;
            }
            int current = layoutManager.getCurrentPosition();
            ViewHolder holder = getViewHolder(current);
            if (holder != null) {
                notifyScrollStart(holder, current);
            }
        }

        @Override
        public void onScrollEnd() {
            if (onItemChangedListeners.isEmpty() && scrollStateChangeListeners.isEmpty()) {
                return;
            }
            int current = layoutManager.getCurrentPosition();
            ViewHolder holder = getViewHolder(current);
            if (holder != null) {
                notifyScrollEnd(holder, current);
                notifyCurrentItemChanged(holder, current);
                checkLazyLoading(holder, current);
            }
        }

        @Override
        public void onScroll(float currentViewPosition) {
            if (scrollStateChangeListeners.isEmpty()) {
                return;
            }
            int current = getCurrentItem();
            ViewHolder currentHolder = getViewHolder(getCurrentItem());

            int newCurrent = current + (currentViewPosition < 0 ? 1 : -1);
            ViewHolder newCurrentHolder = getViewHolder(newCurrent);

            if (currentHolder != null && newCurrentHolder != null) {
                notifyScroll(currentViewPosition, currentHolder, newCurrentHolder);
            }
        }

        @Override
        public void onCurrentViewFirstLayout() {
            post(new Runnable() {
                @Override
                public void run() {
                    notifyCurrentItemChanged();
                }
            });
        }

        @Override
        public void onDataSetChangeChangedPosition() {
            notifyCurrentItemChanged();
        }
    }
}
