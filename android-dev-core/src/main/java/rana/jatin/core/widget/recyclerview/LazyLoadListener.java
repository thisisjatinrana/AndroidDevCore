package rana.jatin.core.widget.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by jatin on 6/8/2017.
 */

public interface LazyLoadListener {
    boolean onScrollNext(int page, int totalItemsCount);
    boolean onScrollPrev(int page, int totalItemsCount);

    void onScrolling(RecyclerView recyclerView, int dx, int dy);

    void onScrollingStateChanged(RecyclerView view, int scrollState);
}
