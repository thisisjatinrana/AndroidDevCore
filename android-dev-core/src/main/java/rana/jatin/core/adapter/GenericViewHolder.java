package rana.jatin.core.adapter;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class GenericViewHolder extends RecyclerView.ViewHolder {

    private static final int ACTION_ITEM_VIEW_CLICK = 0;
    protected GenericRecyclerViewAdapter adapter;

    public GenericViewHolder(View view, GenericRecyclerViewAdapter adapter) {
        super(view);
        this.adapter = adapter;
        findViews(view);
        itemView.setOnClickListener(v -> adapter.getAdapterBridge().onViewHolderClick(adapter.getAdapterTag(), ACTION_ITEM_VIEW_CLICK, getAdapterPosition(), itemView, this));
    }

    public GenericRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public abstract void findViews(View rootView);

    public abstract void onBind(int position);

    public abstract void onDetached();

    public abstract void onViewRecycled();

}

