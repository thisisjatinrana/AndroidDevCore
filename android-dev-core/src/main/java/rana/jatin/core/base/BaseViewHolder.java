
package rana.jatin.core.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import rana.jatin.core.model.Model;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private Model model;

    public BaseViewHolder(View view) {
        super(view);
        findViews(view);
    }

    public abstract void findViews(View rootView);

    public abstract void onBind(int position);

    public abstract void onDetached();

    public abstract void onViewRecycled();

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
