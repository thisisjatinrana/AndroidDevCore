package rana.jatin.core.util;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;

import rana.jatin.core.R;
import rana.jatin.core.base.Extras;
import rana.jatin.core.model.Model;

/**
 * This class implements more simple way to utilize FragmentManager transactions
 */
public class FragmentUtil {
    private final FragmentManager fragmentManager;
    private String TAG = FragmentUtil.class.getName();
    private Fragment fragment;
    private int container;
    private boolean replace = false, addToStack = true, refresh = false;
    private int animExit = R.anim.fragment_out;
    private int animEnter = R.anim.fragment_in;
    private Bundle extras = null;
    private Serializable serializable;
    private Model model;

    public FragmentUtil(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    /**
     * Creating new {@link FragmentUtil} for fragmentManager
     * Usage: FragmentUtil.with(this).replace(fragment, R.setExtrasId.container).skipStack().commit();
     *
     * @return new object of {@link FragmentUtil}
     */
    private static FragmentUtil with(FragmentManager fragmentManager) {
        return new FragmentUtil(fragmentManager);
    }

    /**
     * See {@link #with(FragmentManager)}
     *
     * @return new object of {@link FragmentUtil}
     */
    public static FragmentUtil with(AppCompatActivity context) {
        return with(context.getSupportFragmentManager());
    }

    /**
     * See {@link #with(FragmentManager)}
     *
     * @return new object of {@link FragmentUtil}
     */
    public static FragmentUtil with(Fragment fragment) {
        return with(fragment.getFragmentManager());
    }

    /**
     * See {@link #with(FragmentManager)}
     *
     * @return new object of {@link FragmentUtil}
     */
    public static FragmentUtil with(android.app.Fragment fragment) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) fragment.getActivity();
        return with(appCompatActivity);
    }

    /**
     * Same as {@link #fragment(Fragment, int, boolean)} with replace = true
     *
     * @return self
     */
    public FragmentUtil replace(Fragment fragment, @IdRes int container) {
        fragment(fragment, container, true);

        return this;
    }

    /**
     * Same as {@link #fragment(Fragment, int, boolean)} with replace = false
     *
     * @return self
     */
    public FragmentUtil add(Fragment fragment, @IdRes int container) {
        fragment(fragment, container, false);

        return this;
    }

    /**
     * Set fragment to add in transaction
     *
     * @param fragment  fragment to add
     * @param container setExtrasId of view where to add fragment
     * @param replace   true to replace fragment, false to add fragment and hide current
     * @return self
     */
    public FragmentUtil fragment(Fragment fragment, @IdRes int container, boolean replace) {
        this.fragment = fragment;
        this.container = container;
        this.replace = replace;

        return this;
    }

    public FragmentUtil showDialogFragment(DialogFragment fragment) {
        fragment.show(fragmentManager, null);
        return this;
    }

    /**
     * Set flag to not add this transaction to back stack
     *
     * @param skipStack true to not add to back stack
     * @return self
     */
    public FragmentUtil skipStack(boolean skipStack) {
        this.addToStack = !skipStack;
        return this;
    }

    /**
     * Set flag to refresh fragment if already in container
     *
     * @param refresh true to reload fragment and refresh content if fragment is already in container
     * @return self
     */
    public FragmentUtil refresh(boolean refresh) {
        this.refresh = refresh;
        return this;
    }

    /**
     * Same as {@link #skipStack(boolean)} with true parameter
     *
     * @return self
     */
    public FragmentUtil skipStack() {
        skipStack(true);
        return this;
    }

    /**
     * @param model class which implements serializable passed as arguments to fragment
     * @return self
     */
    public FragmentUtil setModel(Serializable model) {
        this.serializable = model;
        return this;
    }

    /**
     * @param model class which extends {@link Model}passed as arguments to fragment
     * @return self
     */
    public FragmentUtil setModel(Model model) {
        this.model = model;
        return this;
    }

    /**
     * Additional extras to be put into fragment's arguments
     *
     * @param extras extras to add
     * @return self
     */
    public FragmentUtil extras(Bundle extras) {
        this.extras = extras;
        return this;
    }

    /**
     * set custom animations for fragment transactions
     *
     * @param enter animation resource id
     * @param exit  animation resource id
     * @return self
     */
    public FragmentUtil setCustomAnimations(int enter, int exit) {
        this.animEnter = enter;
        this.animExit = exit;
        return this;
    }

    /**
     * Compare two fragment to avoid adding the same fragment again
     *
     * @param left
     * @param right
     * @return true if fragment are equal
     */
    private boolean equal(Fragment left, Fragment right) {
        return (left != null && right != null && left.getClass().getName().equalsIgnoreCase(right.getClass().getName()));
    }

    /*
     * Create and commit new transaction executing all collected options.
     * @return true if fragment was added, else false
     */
    public boolean commit() {

        String backStateName = fragment.getClass().getName();
        Fragment current = fragmentManager.findFragmentById(container);
        if (equal(fragment, current)) {
            if (refresh)
                reLoadFragment(fragment);
            return false;
        }

        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }

        if (serializable != null)
            args.putSerializable(Extras.MODEL.name(), serializable);

        if (model != null)
            args.putSerializable(Extras.MODEL.name(), model);

        if (extras != null)
            args.putAll(extras);

        fragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (addToStack)
            transaction.setCustomAnimations(animEnter, animExit, animEnter, animExit);

        if (replace) {
            transaction.replace(container, fragment);
        } else {
            if (current != null)
                transaction.hide(current);
            transaction.add(container, fragment, String.valueOf(container));
        }

        if (addToStack)
            transaction.addToBackStack(backStateName);

        transaction.commitAllowingStateLoss();
        return true;
    }

    /*
     * remove fragment
     * @param fragment to be removed
     * return self
     */
    public FragmentUtil removeFragment(Fragment fragment) {
        if (fragment == null)
            return this;
        try {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        } catch (Exception e) {
        }

        return this;
    }

    /*
     * remove fragment from container
     * @param containerId from fragment to be removed
     * return self
     */
    public FragmentUtil removeFragFromContainer(int containerId) {
        try {
            Fragment fragment = fragmentManager.findFragmentById(container);
            removeFragment(fragment);
        } catch (Exception e) {

        }
        return this;
    }

    /*
     * reload fragment
     * @param fragment to reload
     * return self
     */
    public FragmentUtil reLoadFragment(Fragment fragment) {
        // Reload current fragment
        Fragment frg = null;
        frg = fragmentManager.findFragmentByTag(fragment.getClass().getName());
        frg.onDetach();

        fragmentManager.beginTransaction()
                .detach(frg)
                .commitNowAllowingStateLoss();

        fragmentManager.beginTransaction()
                .attach(frg)
                .commitAllowingStateLoss();
        return this;
    }

    /*
     * remove all the fragments from back stack
     * return self
     */
    public FragmentUtil clearBackStack() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return this;
    }
}
