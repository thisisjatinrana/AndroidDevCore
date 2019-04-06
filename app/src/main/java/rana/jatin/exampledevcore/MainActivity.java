package rana.jatin.exampledevcore;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import rana.jatin.core.base.BaseActivity;
import rana.jatin.core.mediaPicker.MediaPicker;
import rana.jatin.core.mediaPicker.MimeType;
import rana.jatin.core.mediaPicker.engine.impl.GlideEngine;
import rana.jatin.core.mediaPicker.internal.entity.CaptureStrategy;
import rana.jatin.core.rxbus.RxBus;
import rana.jatin.core.rxbus.Subscribe;
import rana.jatin.core.rxbus.ThreadMode;
import rana.jatin.core.util.IntentUtil;
import rana.jatin.core.util.MessageUtil;

public class MainActivity extends BaseActivity {

    MessageUtil messageUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageUtil = new MessageUtil(this);
        RxBus.get().send(101, "hi");
        MediaPicker.from(this)
                .choose(MimeType.ofImage())
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .imageEngine(new GlideEngine()).capture(true).captureStrategy(new CaptureStrategy(true, "com.vemeet.provider", "vemeet"))
                .forResult(IntentUtil.PICK_IMAGE_CODE);
    }

    @Subscribe(code = 101, threadMode = ThreadMode.MAIN_THREAD)
    public void show(String message) {
        messageUtil.toast(message);
    }

    @Override
    public void onBackPress(int fragmentCount) {

    }

    @Override
    public void onRefresh() {

    }
}
