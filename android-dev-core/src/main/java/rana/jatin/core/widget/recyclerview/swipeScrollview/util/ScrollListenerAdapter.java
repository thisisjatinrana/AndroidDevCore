package rana.jatin.core.widget.recyclerview.swipeScrollview.util;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import rana.jatin.core.widget.recyclerview.swipeScrollview.SwipeScrollView;


public class ScrollListenerAdapter<T extends RecyclerView.ViewHolder> implements SwipeScrollView.ScrollStateChangeListener<T> {

    private SwipeScrollView.ScrollListener<T> adaptee;

    public ScrollListenerAdapter(@NonNull SwipeScrollView.ScrollListener<T> adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void onScrollStart(@NonNull T currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScrollEnd(@NonNull T currentItemHolder, int adapterPosition) {

    }

    @Override
    public void onScroll(float scrollPosition, @NonNull T currentHolder, @NonNull T newCurrentHolder) {
        adaptee.onScroll(scrollPosition, currentHolder, newCurrentHolder);
    }

    @Override
    public boolean onScrollFirstItem(RecyclerView.ViewHolder holder, int page, int adapterPosition) {
        return true;
    }

    @Override
    public boolean onScrollLastItem(RecyclerView.ViewHolder holder, int page, int adapterPosition) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScrollListenerAdapter) {
            return adaptee.equals(((ScrollListenerAdapter) obj).adaptee);
        } else {
            return super.equals(obj);
        }
    }
}
