package rana.jatin.exampledevcore;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import java.util.List;

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
                .choose(MimeType.ofAll(), false).capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.app.provider", true, true))
                .countable(true).maxSelectable(5)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .imageEngine(new GlideEngine())
                .forResult(IntentUtil.PICK_IMAGE_VIDEO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Uri> mSelected = MediaPicker.obtainResult(data);
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
