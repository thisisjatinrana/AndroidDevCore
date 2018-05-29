package rana.jatin.core.widget.recyclerview;

import android.support.v7.widget.RecyclerView;

import rana.jatin.core.base.BaseViewHolder;

/*
 *  BaseRecyclerViewAdapter is a super-powered {@link android.support.v7.widget.RecyclerView.Adapter<RecyclerView.ViewHolder> RecyclerView.Adapter}
*  to be used with {@link android.support.v7.widget.RecyclerView}
*/
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public RecyclerViewClickListener clickListener;

    public void OnItemClickListener(RecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
