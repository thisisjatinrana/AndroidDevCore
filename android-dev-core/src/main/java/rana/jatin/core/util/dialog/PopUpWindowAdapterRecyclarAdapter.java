package rana.jatin.core.util.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import rana.jatin.core.R;
import rana.jatin.core.adapter.recyclerview.BaseRecyclarAdapter;


/**
 * Created by jatin on 1/3/2017.
 */

public class PopUpWindowAdapterRecyclarAdapter extends BaseRecyclarAdapter {

    private Context mContext;
    private ArrayList<DialogItem> dialogItems =new ArrayList<>();

    // Provide a suitable constructor (depends on the kind of dataset)
    public PopUpWindowAdapterRecyclarAdapter(Context context) {
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_pop_up_wndow_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ViewHolder holder = (ViewHolder) viewHolder;
        holder.bindView(holder, position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dialogItems.size();
    }

    public void setData(ArrayList<DialogItem> mDialogItems)
    {
        dialogItems.addAll(mDialogItems);
        notifyDataSetChanged();
    }

    public void insertItem(DialogItem mDialogItems)
    {
        dialogItems.add(mDialogItems);
        notifyItemInserted(dialogItems.size()-1);
    }

    public void deleteItem(int position)
    {
        dialogItems.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivItemImage;
        TextView tvItemName;
        public ViewHolder(View rootView) {
            super(rootView);
            ivItemImage = (ImageView)rootView.findViewById(R.id.iv_item_image);
            tvItemName = (TextView) rootView.findViewById(R.id.tv_item_name);
        }

        private void bindView(final RecyclerView.ViewHolder holder, final int position) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, itemView, holder.getAdapterPosition());
                }
            });

            tvItemName.setText(dialogItems.get(position).getName());

            if(dialogItems.get(position).getTxtColor()!=-1){
               tvItemName.setTextColor(ContextCompat.getColor(mContext, dialogItems.get(position).getTxtColor()));
            }

            if(dialogItems.get(position).getResId()!=-1) {
               ivItemImage.setImageResource(dialogItems.get(position).getResId());
            }else {
                ivItemImage.setVisibility(View.GONE);
            }
        }
    }

}