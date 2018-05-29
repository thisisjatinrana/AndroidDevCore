package rana.jatin.exampledevcore;

import android.os.Bundle;

import rana.jatin.core.base.BaseActivity;
import rana.jatin.core.rxbus.RxBus;
import rana.jatin.core.rxbus.Subscribe;
import rana.jatin.core.rxbus.ThreadMode;
import rana.jatin.core.util.ViewUtil;

public class MainActivity extends BaseActivity {

    ViewUtil viewUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewUtil = new ViewUtil(this);
        RxBus.get().send(101, "hi");
    }

    @Subscribe(code = 101, threadMode = ThreadMode.MAIN_THREAD)
    public void show(String message) {
        viewUtil.toast(message);
    }

    @Override
    public void onBackPress(int fragmentCount) {

    }

    @Override
    public void onRefresh() {

    }
}
