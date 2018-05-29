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

public class ListDialogAdapter extends BaseRecyclerViewAdapter {

    private Context mContext;
    private ArrayList<DialogData> dialogData = new ArrayList<>();

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListDialogAdapter(Context context) {
        mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        private View divider;

        private ViewHolder(View view) {
            super(view);

        }

        @Override
        public void onBind(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(view, itemView, getAdapterPosition());
                }
            });

            if (dialogData.get(position).getTxtColor() != -1) {
                try {
                    tvItemName.setTextColor(ContextCompat.getColor(mContext, dialogData.get(position).getTxtColor()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tvItemName.setText(dialogData.get(position).getName());

            if (dialogData.get(position).getResId() != -1) {
                try {
                    ivItemImage.setImageResource(dialogData.get(position).getResId());
                    ivItemImage.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    ivItemImage.setVisibility(View.GONE);
                }
            } else {
                ivItemImage.setVisibility(View.GONE);
            }

            if (dialogData.get(position).isUnderLine())
                tvItemName.setBackgroundResource(dialogData.get(position).getUnderlineRes());
            else
                tvItemName.setBackgroundResource(R.drawable.underline_transparent);

            if (dialogData.get(position).isDivider())
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
