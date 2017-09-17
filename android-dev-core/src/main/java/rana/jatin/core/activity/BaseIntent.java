package rana.jatin.core.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import java.io.Serializable;
import rana.jatin.core.model.Model;

/*
*  BaseIntent is a super-powered {@link android.content.Intent Intent}
*  to be used with {@link rana.jatin.core.activity.BaseActivity}
*/
public class BaseIntent extends Intent {

    /**
     * Same as {@link #BaseIntent(Activity, Class, boolean)} class will be taken from activity. Used to pass parameters to self.
     *
     * @param activity context
     */
    public BaseIntent(Activity activity) {
        super(activity, activity.getClass());
    }

    public BaseIntent(Activity activity, Class<?> cls, boolean finish) {
        super(activity, cls);
        if (finish)
            activity.finish();
    }

    /**
     * @param model class which implements {@link Serializable} passed as extras to activity
     * @return self
     */
    public BaseIntent setModel(Serializable model) {
        putExtra(Extras.MODEL.name(), model);
        return this;
    }

    /**
     * @param model class which extends {@link Model}passed as extras to activity
     * @return self
     */
    public BaseIntent setModel(Model model) {
        putExtra(Extras.MODEL.name(), model);
        return this;
    }

    /**
     * @param fragment class of fragment that will be pushed
     * @param container id in which fragment to be pushed
     * @return self
     */
    public BaseIntent fragment(Class<? extends Fragment> fragment, int container) {
        putExtra(Extras.CONTAINER.name(), container);
        putExtra(Extras.FRAGMENT.name(), fragment);
        return this;
    }

    /**
     * @param fragment  class of fragment that will be pushed
     * @param model     setModel object to be passed to fragment
     * @param container id in which fragment to be pushed
     * @return self
     */
    public BaseIntent fragment(Class<? extends Fragment> fragment, Model model, int container) {
        fragment(fragment, container);
        setModel(model);
        return this;
    }

    /**
     * @param fragment  class of fragment that will be pushed
     * @param model     class which implements serializable, setModel object to be passed to fragment
     * @param container id in which fragment to be pushed
     * @return self
     */
    public BaseIntent fragment(Class<? extends Fragment> fragment, Serializable model, int container) {
        fragment(fragment, container);
        setModel(model);
        return this;
    }

    /**
     * If set will replace fragment using {@link android.app.FragmentTransaction} instead of adding fragment on top
     * @return self
     */
    public BaseIntent replace() {
        putExtra(Extras.REPLACE.name(), true);
        return this;
    }

    /**
     * If set fragment will not be added to back stack
     * @return self
     */
    public BaseIntent skipStack() {
        putExtra(Extras.SKIP_STACK.name(), true);
        return this;
    }

    /**
     * Combination of {@link #replace()} and {@link #skipStack()} to use fragment as root of hierarchy.
     * @return self
     */
    public BaseIntent asRoot() {
        replace();
        skipStack();
        return this;
    }

    /**
     * If set fragment will be refreshed if already in back stack
     * @return self
     */
    public BaseIntent refresh() {
        putExtra(Extras.REFRESH.name(), true);
        return this;
    }

    /**
     * If set fragment's back stack will be cleared before adding new fragment
     * @return self
     */
    public BaseIntent clearStack() {
        putExtra(Extras.CLEAR_STACK.name(), true);
        return this;
    }
}
