package rana.jatin.core.widget.recyclerview;

/**
 * Created by jatin on 6/8/2017.
 */

public interface LazyLoadListener {
    boolean onScrollNext(int page, int totalItemsCount);
    boolean onScrollPrev(int page, int totalItemsCount);
}
