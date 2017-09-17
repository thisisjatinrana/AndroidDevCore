package rana.jatin.core.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;

/*
*  BaseRecyclarAdapter is a super-powered {@link android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder> RecyclerView.Adapter}
*  to be used with {@link android.support.v7.widget.RecyclerView}
*/
public abstract class BaseRecyclarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public RecyclerClickListener listener;
    public void OnClickListener(RecyclerClickListener listener) {
        this.listener = listener;
    }
}
