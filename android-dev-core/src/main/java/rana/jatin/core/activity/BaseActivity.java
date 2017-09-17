package rana.jatin.core.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

import io.reactivex.functions.Consumer;
import rana.jatin.core.R;
import rana.jatin.core.RxEventBus.RxBus;
import rana.jatin.core.etc.ContextWrapper;
import rana.jatin.core.etc.FragmentHelper;
import rana.jatin.core.etc.HelperContainer;
import rana.jatin.core.fragments.BaseFragment;
import rana.jatin.core.model.Event;

/*
*  BaseActivity is a super-powered {@link android.support.v7.app.AppCompatActivity AppCompatActivity}
*  to be used with {@link rana.jatin.core.activity.BaseIntent}
*/
public abstract class BaseActivity extends AppCompatActivity {

    private String TAG = BaseActivity.class.getName();
    private HelperContainer helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new HelperContainer(this);
        helper.viewHelper().setSoftInputListener();
        subscribeRefresh();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        onNewIntent(getIntent());
    }

    @Override
    protected final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        if (intent.getBooleanExtra(Extras.CLEAR_STACK.name(), false)) { //TODO fix animation
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        onNewIntentInternal(intent);
    }

    private void onNewIntentInternal(Intent intent) {
        onIntent(intent, null);
        if (intent.getData() != null)
            onDataIntent(intent);
        if (intent.hasExtra(Extras.FRAGMENT.name()))
            onFragmentIntent(intent);
    }

    /**
     * Called for any new incoming intent
     *
     * @param intent
     * @param savedInstanceState
     */
    public void onIntent(Intent intent, Bundle savedInstanceState) {

    }

    /**
     * Called when new intent contains data
     *
     * @param intent
     */
    public void onDataIntent(Intent intent) {

    }

    /*
    * return Extras passed to the activity using
    * {@link BaseIntent#setModel(Serializable object) setModel}
    * and {@link BaseIntent#setModel(Model object) setModel} methods.
    */
    public <T> T getModel() {
        Bundle args = getIntent().getExtras();
        if (args == null)
            return null;

        return (T) args.getSerializable(Extras.MODEL.name());
    }

    /**
     * Called when new intent contains fragment instructions. See {@link BaseIntent}
     *
     * @param intent
     */
    public void onFragmentIntent(Intent intent) {
        boolean needRefresh = intent.getBooleanExtra(Extras.REFRESH.name(), false);

        Class<? extends Fragment> fragmentType = (Class<? extends Fragment>) intent.getSerializableExtra(Extras.FRAGMENT.name());
        Fragment fragment = instantiate(fragmentType);

        if (fragment == null)
            return;

        int container = intent.getIntExtra(Extras.CONTAINER.name(), 0);
        fragment.setArguments(intent.getExtras()); //copy extras to fragment
        fragment.setTargetFragment(getCurrentFragment(container), 0); //set current fragment as target

        if (fragment instanceof DialogFragment && ((DialogFragment) fragment).getShowsDialog()) {
            DialogFragment dialog = ((DialogFragment) fragment);
            dialog.show(getSupportFragmentManager(), null);
        } else {
            boolean skipStack = intent.getBooleanExtra(Extras.SKIP_STACK.name(), false);
            boolean replace = intent.getBooleanExtra(Extras.REPLACE.name(), false);

            boolean added = FragmentHelper.with(this).fragment(fragment, container, replace).skipStack(skipStack).commit();
            needRefresh = !added;
        }

        if (needRefresh)
            RxBus.getInstance().publish(Event.REFRESH.toString(), fragment.getClass().getName()); //refresh
    }

    private Fragment instantiate(Class<? extends Fragment> cls) {
        if (cls == null)
            return null;

        try {
            return cls.newInstance();
        } catch (Exception ignore) {
        }

        return null;
    }

    private Fragment getCurrentFragment(int container) {
        return getSupportFragmentManager().findFragmentById(container);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(this);
        helper.onDestroy();
        helper = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
    * Subscribe for RxBus REFRESH event.
    * see {@link rana.jatin.core.model.Event#REFRESH}
    */
    private void subscribeRefresh() {
        RxBus.getInstance().subscribe(Event.REFRESH.name(), this, new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                onRefresh(o);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = ContextWrapper.wrap(newBase, new Locale(Locale.ENGLISH.getLanguage()));
        super.attachBaseContext(context);
    }

    /*
    * Invoke abstract method {@link onBackPress(int) onBackPress}
    */
    @Override
    public void onBackPressed() {
        int i = getSupportFragmentManager().getBackStackEntryCount();
        onBackPress(i);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof BaseFragment && fragment.isVisible())
                    ((BaseFragment) fragment).onBackPress(i);
            }
        }
        super.onBackPressed();
    }

    /*
    * return HelperContainer. see {@link rana.jatin.core.etc.HelperContainer}
    */
    public HelperContainer helper() {
        return helper;
    }

    /*
    * invoke method {@link android.support.v4.app.Fragment#onActivityResult(int ,int ,Intent) onActivityResult} of visible fragments.
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null && fragment.isVisible())
                fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    /*
   * invoke method {@link android.support.v4.app.Fragment#onRequestPermissionsResult(int ,String[] ,int[]) onRequestPermissionsResult} of visible fragments.
   */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null && fragment.isVisible())
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public abstract void onBackPress(int fragmentCount);

    public abstract void onRefresh(Object model);

}
