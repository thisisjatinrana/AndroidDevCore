package rana.jatin.core.util.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import rana.jatin.core.R;
import rana.jatin.core.base.BaseViewHolder;
import rana.jatin.core.widget.BaseTextView;
import rana.jatin.core.widget.recyclerview.BaseRecyclerViewAdapter;


/**
 * Created by jatin on 1/3/2017.
 */

public class PopUpWindowAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;
    private ArrayList<DialogData> dialogData = new ArrayList<>();

    // Provide a suitable constructor (depends on the kind of dataset)
    public PopUpWindowAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_pop_up_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dialogData.size();
    }

    public void setData(ArrayList<DialogData> mDialogData) {
        dialogData.addAll(mDialogData);
        notifyDataSetChanged();
    }

    public void insertItem(DialogData mDialogItems) {
        dialogData.add(mDialogItems);
        notifyItemInserted(dialogData.size() - 1);
    }

    public void deleteItem(int position) {
        dialogData.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends BaseViewHolder {

        private ImageView ivItemImage;
        private BaseTextView tvItemName;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBind(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(v, itemView, getAdapterPosition());
                }
            });

            tvItemName.setText(dialogData.get(position).getName());

            if (dialogData.get(position).getTxtColor() != -1) {
                tvItemName.setTextColor(ContextCompat.getColor(mContext, dialogData.get(position).getTxtColor()));
            }

            if (dialogData.get(position).getResId() != -1) {
                ivItemImage.setImageResource(dialogData.get(position).getResId());
            } else {
                ivItemImage.setVisibility(View.GONE);
            }
        }

        public void findViews(View rootView) {
            ivItemImage = rootView.findViewById(R.id.iv_item_image);
            tvItemName = rootView.findViewById(R.id.tv_item_name);
        }

        @Override
        public void onDetached() {

        }

        @Override
        public void onViewRecycled() {

        }

    }

}