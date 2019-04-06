package rana.jatin.core.widget.recyclerview;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by jatin on 2/23/2017.
 */

public class OnSwipeHelper extends ItemTouchHelper.SimpleCallback {
    private OnSwipeListener onSwipeListener;
    private boolean isSwipeEnabled=false;
    private boolean isOnMove=false;

    public OnSwipeHelper(OnSwipeListener listeners) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.onSwipeListener = listeners;
        this.isSwipeEnabled=true;
        this.isOnMove=true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //Remove item
        if(isOnMove){
            onSwipeListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            return true;
        }
        else
            return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Remove item
        if(isSwipeEnabled)
        onSwipeListener.onSwipe(viewHolder.itemView,viewHolder.getAdapterPosition(),direction);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(isSwipeEnabled) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }else {
            return makeMovementFlags(0, 0);
        }
    }

    public void setSwipeEnabled(boolean isSwipeEnabled){
        this.isSwipeEnabled=isSwipeEnabled;
    }

    public boolean isSwipeEnabled() {
        return isSwipeEnabled;
    }

    public boolean isLongPressDragEnabled() {
        return isOnMove;
    }

    public void isLongPressDragEnabled(boolean onMove) {
        isOnMove = onMove;
    }
}
