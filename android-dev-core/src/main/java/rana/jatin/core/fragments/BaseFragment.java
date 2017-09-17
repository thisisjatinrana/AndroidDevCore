package rana.jatin.core.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.reactivex.functions.Consumer;
import rana.jatin.core.R;
import rana.jatin.core.RxEventBus.RxBus;
import rana.jatin.core.activity.Extras;
import rana.jatin.core.etc.HelperContainer;
import rana.jatin.core.model.Event;

/*
*  BaseFragment is a super-powered {@link android.support.v4.app.Fragment Fragment}
*  to be used with {@link rana.jatin.core.etc.FragmentHelper}
*/
public abstract class BaseFragment extends Fragment {

    private HelperContainer helper;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new HelperContainer((AppCompatActivity)getActivity(),this);
        subscribeRefresh();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(this);
        helper.onDestroy();
        helper = null;
    }

   /*
   * return arguments passed to the fragment using
   * {@link rana.jatin.core.etc.FragmentHelper#setModel(Serializable object) setModel}
   * and {@link rana.jatin.core.etc.FragmentHelper#setModel(Model object) setModel} methods.
   */
    public <T> T getModel() {
        Bundle args = getArguments();
        if (args == null)
            return null;

        return (T) args.getSerializable(Extras.MODEL.name());
    }

    /*
    * Subscribe for RxBus REFRESH event.
    * see {@link rana.jatin.core.model.Event#REFRESH}
    */
    private void subscribeRefresh(){
        RxBus.getInstance().subscribe(Event.REFRESH.name(), this, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                   onRefresh(o);
            }
        });
    }

    /*
    * return HelperContainer. see {@link rana.jatin.core.etc.HelperContainer}
    */
    public HelperContainer helper() {
        return helper;
    }

    public abstract void onBackPress(int fragmentCount);

    public abstract void onRefresh(Object model);
}
