package rana.jatin.core.widget.nestedScrollView;

import androidx.core.widget.NestedScrollView;

/**
 * Created by jatin on 6/8/2017.
 */

public interface LazyLoadListener {
    boolean onScrollNext(int page, int totalItemsCount);
    boolean onScrollPrev(int page, int totalItemsCount);

    void onScrolling(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
}