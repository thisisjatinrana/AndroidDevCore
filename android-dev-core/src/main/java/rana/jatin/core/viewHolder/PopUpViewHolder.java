package rana.jatin.core.viewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import rana.jatin.core.R;
import rana.jatin.core.adapter.GenericRecyclerViewAdapter;
import rana.jatin.core.adapter.GenericViewHolder;
import rana.jatin.core.util.dialog.DialogData;
import rana.jatin.core.widget.BaseTextView;

public class PopUpViewHolder extends GenericViewHolder {

    private ImageView ivItemImage;
    private BaseTextView tvItemName;

    public PopUpViewHolder(View view, GenericRecyclerViewAdapter adapter) {
        super(view, adapter);
    }

    @Override
    public void onBind(int position) {

        DialogData dialogData = (DialogData) adapter.getItem(position);

        tvItemName.setText(dialogData.getName());

        if (dialogData.getTxtColor() != -1) {
            tvItemName.setTextColor(ContextCompat.getColor(adapter.getContext(), dialogData.getTxtColor()));
        }

        if (dialogData.getResId() != -1) {
            ivItemImage.setImageResource(dialogData.getResId());
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