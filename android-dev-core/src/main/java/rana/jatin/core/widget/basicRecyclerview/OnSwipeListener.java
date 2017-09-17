package rana.jatin.core.widget.basicRecyclerview;

import android.view.View;

/**
 * Created by jatin on 6/8/2017.
 */

public interface OnSwipeListener {
    void onSwipe(View view, int position, int direction);
    void onItemMove(int fromPosition, int toPosition);
}
