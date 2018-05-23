package rana.jatin.core.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/*
 *  BaseFragment is a super-powered {@link android.support.v4.app.Fragment Fragment}
 *  to be used with {@link rana.jatin.core.etc.FragmentHelper}
 */
public abstract class BaseFragment extends Fragment {

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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

    public abstract void onBackPress(int fragmentCount);
}