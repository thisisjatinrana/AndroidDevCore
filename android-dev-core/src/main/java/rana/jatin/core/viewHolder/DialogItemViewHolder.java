package rana.jatin.core.viewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import rana.jatin.core.R;
import rana.jatin.core.adapter.GenericRecyclerViewAdapter;
import rana.jatin.core.adapter.GenericViewHolder;
import rana.jatin.core.util.dialog.DialogData;
import rana.jatin.core.widget.BaseTextView;

public class DialogItemViewHolder extends GenericViewHolder {

    private ImageView ivItemImage;
    private BaseTextView tvItemName;
    private View divider;

    public DialogItemViewHolder(View view, GenericRecyclerViewAdapter genericRecyclerViewAdapter) {
        super(view, genericRecyclerViewAdapter);

    }

    @Override
    public void onBind(int position) {
        DialogData dialogData = (DialogData) adapter.getItem(position);

        if (dialogData.getTxtColor() != -1) {
            try {
                tvItemName.setTextColor(ContextCompat.getColor(adapter.getContext(), dialogData.getTxtColor()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tvItemName.setText(dialogData.getName());

        if (dialogData.getResId() != -1) {
            try {
                ivItemImage.setImageResource(dialogData.getResId());
                ivItemImage.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                ivItemImage.setVisibility(View.GONE);
            }
        } else {
            ivItemImage.setVisibility(View.GONE);
        }

        if (dialogData.isUnderLine())
            tvItemName.setBackgroundResource(dialogData.getUnderlineRes());
        else
            tvItemName.setBackgroundResource(R.drawable.underline_transparent);

        if (dialogData.isDivider())
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