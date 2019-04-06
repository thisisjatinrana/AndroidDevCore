package rana.jatin.core.widget.recyclerview;


import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class LazyLoadingHelper extends RecyclerView.OnScrollListener {

    private String TAG = LazyLoadingHelper.class.getSimpleName();
    private int prevPage = 1;
    private int nextPage =1;
    private boolean isLoading = false;
    private boolean prevScrollEnabled = false;
    private boolean nextScrollEnabled = false;
    private LazyLoadListener lazyLoadListener;

    public LazyLoadingHelper(LazyLoadListener listeners) {
        this.lazyLoadListener = listeners;
        this.prevScrollEnabled = true;
        this.nextScrollEnabled = true;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        lazyLoadListener.onScrolling(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();

        int pastVisiblesItems=-1;
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] firstVisibleItems = manager.findFirstVisibleItemPositions(null);
            if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                pastVisiblesItems = firstVisibleItems[0];
            }
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            pastVisiblesItems = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        } else {
            pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        }

        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount && !isLoading && nextScrollEnabled) {
            isLoading = true;
            nextPage++;
            isLoading = lazyLoadListener.onScrollNext(nextPage, totalItemCount);
            Log.d(TAG, " onScrollNext : Page= " + String.valueOf(nextPage) + " totalItemCount=" + String.valueOf(totalItemCount));
        }

        if ( pastVisiblesItems==0 && !isLoading && prevScrollEnabled) {
            isLoading = true;
            prevPage++;
            isLoading = lazyLoadListener.onScrollPrev(prevPage, totalItemCount);
            Log.d(TAG, " onScrollPrev : Page= " + String.valueOf(prevPage) + " pastVisiblesItems =" + String.valueOf(pastVisiblesItems));
        }

    }

    // Defines the process for actually isProgress more data based on page
    // Returns true if more data is being loaded; returns false if there is no more data to load.


    @Override
    public void onScrollStateChanged(RecyclerView view, int scrollState) {
        // Don't take any action on changed
        lazyLoadListener.onScrollingStateChanged(view, scrollState);
    }

    public void reset(){
        this.nextPage =1;
        this.prevPage =1;
        this.isLoading =false;
        this.prevScrollEnabled=true;
        this.nextScrollEnabled=true;
    }

    public int getPrevPage() {
        return this.prevPage;
    }

    public void setPrevPage(int page) {
        this.prevPage = page;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(int page) {
        this.nextPage = page;
    }

    public boolean isPrevScrollEnabled() {
        return this.prevScrollEnabled;
    }

    public void setPrevScroll(boolean scrollingEnabled) {
        this.prevScrollEnabled = scrollingEnabled;
    }

    public boolean isNextScrollEnabled() {
        return this.nextScrollEnabled;
    }

    public void setNextScroll(boolean scrollingEnabled) {
        this.nextScrollEnabled = scrollingEnabled;
    }
    public boolean isLoading() {
        return this.isLoading;
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }
}