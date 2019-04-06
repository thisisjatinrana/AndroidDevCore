package rana.jatin.core.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class GenericRecyclerViewAdapter<T> extends RecyclerView.Adapter<GenericViewHolder> {

    private final Context mContext;

    private List<T> mItems;
    private String adapterTag;
    private GenericRecyclerViewAdapterBridge adapterBridge;


    public GenericRecyclerViewAdapter(String adapterTag, Context context, List<T> items, GenericRecyclerViewAdapterBridge adapterBridge) {
        if (items == null) {
            items = new ArrayList<T>();
        }
        mItems = items;
        mContext = context;
        this.adapterBridge = adapterBridge;
        this.adapterTag = adapterTag;
    }

    public GenericRecyclerViewAdapter(String adapterTag, Context context, GenericRecyclerViewAdapterBridge adapterInterface) {
        mItems = new ArrayList<>();
        mContext = context;
        adapterBridge = adapterInterface;
        this.adapterTag = adapterTag;
    }

    @Override
    public int getItemViewType(int position) {
        return adapterBridge.getLayoutId(position, adapterTag);
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(viewType, viewGroup, false);
        return adapterBridge.createViewHolder(view, adapterTag, viewType, this);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder viewHolder, int position) {
        viewHolder.onBind(position);
    }

    public void add(T item, int position) {
        if (item == null) {
            return;
        }
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void add(List<T> items, int position) {
        if (items == null || items.isEmpty()) {
            return;
        }
        mItems.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    public void add(T item) {
        int position = mItems.size();
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void add(List<T> items) {
        if (items.isEmpty()) {
            return;
        }
        int position = mItems.isEmpty() ? 0 : mItems.size();
        mItems.addAll(position, items);
        notifyItemRangeInserted(position, items.size());
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    public List<T> getItems() {
        return mItems;
    }

    public int getItemCount() {
        return mItems.size();
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * Requires equals() and hashcode() to be implemented in T class.
     */
    public void remove(T item) {
        int position = mItems.indexOf(item);
        if (position == -1) {
            return;
        }
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(int position, int itemCount) {
        for (int i = position; i < itemCount; i++) {
            mItems.remove(i);
        }
        notifyItemRangeRemoved(position, itemCount);
    }

    public void replaceList(List<T> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public GenericRecyclerViewAdapterBridge getAdapterBridge() {
        return adapterBridge;
    }

    public String getAdapterTag() {
        return adapterTag;
    }

    public interface GenericRecyclerViewAdapterBridge {
        GenericViewHolder createViewHolder(View view, String tag, int layoutId, GenericRecyclerViewAdapter adapter);

        int getLayoutId(int position, String tag);

        void onViewHolderClick(String tag, int action, int position, View view, GenericViewHolder viewHolder);
    }
}