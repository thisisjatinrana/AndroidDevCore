package rana.jatin.core.util.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import rana.jatin.core.R;
import rana.jatin.core.adapter.recyclerview.BaseRecyclarAdapter;
import rana.jatin.core.base.BaseViewHolder;
import rana.jatin.core.widget.BasicTextView;


/**
 * Created by jatin on 1/3/2017.
 */

public class ListDialogAdapter extends BaseRecyclarAdapter {

    private Context mContext;
    private ArrayList<DialogItem> dialogItems = new ArrayList<>();

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListDialogAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_dialog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dialogItems.size();
    }

    public void setData(ArrayList<DialogItem> mDialogItems) {
        dialogItems.addAll(mDialogItems);
        notifyDataSetChanged();
    }

    public void insertItem(DialogItem mDialogItems) {
        dialogItems.add(mDialogItems);
        notifyItemInserted(dialogItems.size() - 1);
    }

    public void deleteItem(int position) {
        dialogItems.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends BaseViewHolder {

        private ImageView ivItemImage;
        private BasicTextView tvItemName;
        private View divider;

        private ViewHolder(View view) {
            super(view);

        }

        @Override
        public void onBind(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view, itemView, getAdapterPosition());
                }
            });

            if (dialogItems.get(position).getTxtColor() != -1) {
                try {
                    tvItemName.setTextColor(ContextCompat.getColor(mContext, dialogItems.get(position).getTxtColor()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tvItemName.setText(dialogItems.get(position).getName());

            if (dialogItems.get(position).getResId() != -1) {
                try {
                    ivItemImage.setImageResource(dialogItems.get(position).getResId());
                    ivItemImage.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    ivItemImage.setVisibility(View.GONE);

                }
            } else {
                ivItemImage.setVisibility(View.GONE);
            }

            if (dialogItems.get(position).isUnderLine())
                tvItemName.setBackgroundResource(R.drawable.underline_blue);
            else
                tvItemName.setBackgroundResource(R.drawable.underline_transparent);

            if (dialogItems.get(position).isDivider())
                divider.setVisibility(View.VISIBLE);
            else
                divider.setVisibility(View.GONE);

        }

        @Override
        public void findViews(View rootView) {
            ivItemImage = rootView.findViewById(R.id.iv_item_image);
            tvItemName = rootView.findViewById(R.id.tv_item_name);
            divider = rootView.findViewById(R.id.divider);
        }

        @Override
        public void onDetached() {

        }

        @Override
        public void onViewRecycled() {

        }
    }

}
