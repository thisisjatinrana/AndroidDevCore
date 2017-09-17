package rana.jatin.core.etc;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import rana.jatin.core.activity.BaseIntent;
import rana.jatin.core.util.FieldsValidator;
import rana.jatin.core.util.dialog.DialogHelper;

/**
 * Created by jatin on 8/26/2017.
 * Container class for holding all the available helper classes to avoid initialising them separately
 */

public class HelperContainer {
    private AppCompatActivity activity;
    private Fragment fragment;
    private ViewHelper viewHelper;
    private PermissionHelper permissionHelper;
    private FragmentHelper fragmentHelper;

    public HelperContainer(AppCompatActivity context) {
        this.activity = context;
    }

    public HelperContainer(AppCompatActivity context, Fragment fragment) {
        this.activity = context;
        this.fragment = fragment;
    }

    public void onDestroy() {
        activity = null;
        fragment = null;
        viewHelper = null;
        permissionHelper = null;
        fragmentHelper = null;
        permissionHelper = null;
    }

    public ViewHelper viewHelper() {
        if (viewHelper == null)
            viewHelper = new ViewHelper(activity, fragment);

        return viewHelper;
    }

    public FragmentHelper fragmentHelper() {
        if (fragmentHelper == null)
            fragmentHelper = new FragmentHelper(activity.getSupportFragmentManager());
        return fragmentHelper;

    }

    public PermissionHelper permissionHelper() {
        if (permissionHelper == null)
            if (fragment != null)
                permissionHelper = PermissionHelper.getInstance(fragment.getActivity());
            else
                permissionHelper = PermissionHelper.getInstance(activity);
        return permissionHelper;
    }

    public BaseIntent modelIntent(){
        return new BaseIntent(activity);
    }

    public BaseIntent modelIntent(Class<?> cls, boolean finish){
        return new BaseIntent(activity,cls,finish);
    }
}
