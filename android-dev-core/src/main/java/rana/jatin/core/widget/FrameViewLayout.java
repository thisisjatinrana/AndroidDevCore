package rana.jatin.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rana.jatin.core.R;
import rana.jatin.core.widget.circularReveal.widget.RevealFrameLayout;

public class FrameViewLayout extends RevealFrameLayout {

    public final int MATCH_PARENT = 2;
    public final int WRAP_CONTENT = 1;
    private final String TAG_PROGRESS = "ProgressView.TAG_PROGRESS";
    private final String TAG_EMPTY = "ProgressView.TAG_EMPTY";
    private final String TAG_ERROR = "ProgressView.TAG_ERROR";
    private final String CONTENT = "type_content";
    private final String PROGRESS = "type_progress";
    private final String EMPTY = "type_empty";
    private final String ERROR = "type_error";
    private LayoutInflater inflater;
    private View view;
    private List<View> contentViews = new ArrayList<>();
    private View progressView;
    private View emptyView;
    private View errorView;
    private String TAG = FrameViewLayout.class.getName();
    private String state = CONTENT;
    private LayoutParams layoutParams;
    private TextView tvProgressTitle;
    private TextView tvProgressDesc;
    private long delay = 0;

    public FrameViewLayout(Context context) {
        super(context);
    }

    public FrameViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        showContent();
    }

    public FrameViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        showContent();
    }

    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FrameViewLayout);
        int emptyViewLayout, progressViewLayout, errorViewLayout;
        try {
            emptyViewLayout = ta.getResourceId(R.styleable.FrameViewLayout_emptyView, 0);
            progressViewLayout = ta.getResourceId(R.styleable.FrameViewLayout_progressView, 0);
            errorViewLayout = ta.getResourceId(R.styleable.FrameViewLayout_errorView, 0);
            int marginTop = ta.getDimensionPixelSize(R.styleable.FrameViewLayout_marginTop, 0);
            int marginBottom = ta.getDimensionPixelSize(R.styleable.FrameViewLayout_marginBottom, 0);
            int marginLeft = ta.getDimensionPixelSize(R.styleable.FrameViewLayout_marginLeft, 0);
            int marginRight = ta.getDimensionPixelSize(R.styleable.FrameViewLayout_marginRight, 0);
            int[] margin = {marginLeft, marginTop, marginRight, marginBottom};
            setProgressView(progressViewLayout, margin, ta.getInt(R.styleable.FrameViewLayout_progressViewSize, MATCH_PARENT), ta.getInt(R.styleable.FrameViewLayout_progressViewGravity, Gravity.NO_GRAVITY));
            setEmptyView(emptyViewLayout, margin, ta.getInt(R.styleable.FrameViewLayout_emptyViewSize, MATCH_PARENT), ta.getInt(R.styleable.FrameViewLayout_emptyViewGravity, Gravity.NO_GRAVITY));
            setErrorView(errorViewLayout, margin, ta.getInt(R.styleable.FrameViewLayout_errorViewSize, MATCH_PARENT), ta.getInt(R.styleable.FrameViewLayout_errorViewGravity, Gravity.NO_GRAVITY));
            showContent();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }

    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null || (!child.getTag().equals(TAG_PROGRESS)
                && !child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR))) {
            contentViews.add(child);
        }
    }

    /**
     * Hide all other states and show content
     */
    public void showContent() {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchState(CONTENT, Collections.<Integer>emptyList());
            }
        }, delay);
    }

    /**
     * Hide all other states and show content
     *
     * @param skipIds Ids of views not to show
     */
    public void showContent(final List<Integer> skipIds) {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchState(CONTENT, skipIds);
            }
        }, delay);
    }

    /**
     * Hide content and show the progress bar
     *
     * @param skipIds Ids of views to not hide
     */
    public void showProgress(List<Integer> skipIds) {
        switchState(PROGRESS, skipIds);
    }

    /**
     * Hide content and show the progress bar
     */
    public void showProgress() {
        switchState(PROGRESS, Collections.<Integer>emptyList());
    }

    /**
     * Hide content and show the progress bar with progress text
     */
    public void showProgress(String progress, String des) {
        switchState(PROGRESS, progress, des, Collections.<Integer>emptyList());
    }

    /**
     * Show empty view when there are not data to show
     *
     * @param skipIds Ids of views to not hide
     */
    public void showEmpty(final List<Integer> skipIds) {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchState(EMPTY, skipIds);
            }
        }, delay);
    }

    public void showEmpty() {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchState(EMPTY, Collections.<Integer>emptyList());
            }
        }, delay);
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param skipIds Ids of views to not hide
     */
    public void showError(final List<Integer> skipIds) {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchState(ERROR, skipIds);
            }
        }, delay);
    }


    public void showError() {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchState(ERROR, Collections.<Integer>emptyList());
            }
        }, delay);
    }

    public String getState() {
        return state;
    }

    public boolean isContent() {
        return state.equals(CONTENT);
    }

    public boolean isProgress() {
        return state.equals(PROGRESS);
    }

    public boolean isEmpty() {
        return state.equals(EMPTY);
    }

    public boolean isError() {
        return state.equals(ERROR);
    }

    private void switchState(String state, List<Integer> skipIds) {
        this.state = state;

        switch (state) {
            case CONTENT:
                //Hide all state views to display content
                hideProgressView();
                hideEmptyView();
                hideErrorView();

                // setContentVisibility(true, skipIds);
                break;
            case PROGRESS:
                hideEmptyView();
                hideErrorView();

                showProgressView();
                // setContentVisibility(false, skipIds);
                break;
            case EMPTY:
                hideProgressView();
                hideErrorView();

                showEmptyView();
                //   setContentVisibility(false, skipIds);
                break;
            case ERROR:
                hideProgressView();
                hideEmptyView();

                showErrorView();
                // setContentVisibility(false, skipIds);
                break;
        }
    }


    private void switchState(String state, String messageTitle, String messageDesc, List<Integer> skipIds) {
        this.state = state;

        switch (state) {
            case CONTENT:
                //Hide all state views to display content
                hideProgressView();
                hideEmptyView();
                hideErrorView();

                // setContentVisibility(true, skipIds);
                break;
            case PROGRESS:
                hideEmptyView();
                hideErrorView();

                showProgressView(messageTitle, messageDesc);
                // setContentVisibility(false, skipIds);
                break;
            case EMPTY:
                hideProgressView();
                hideErrorView();

                showEmptyView();
                //   setContentVisibility(false, skipIds);
                break;
            case ERROR:
                hideProgressView();
                hideEmptyView();

                showErrorView();
                // setContentVisibility(false, skipIds);
                break;
        }
    }

    private void showProgressView() {
        if (progressView != null) {
            progressView.setVisibility(VISIBLE);
            progressView.bringToFront();
            progressView.invalidate();
        }
    }

    private void showProgressView(String progress, String des) {
        if (progressView != null) {
            if (tvProgressTitle != null)
                tvProgressTitle.setText(progress);

            if (tvProgressDesc != null)
                tvProgressDesc.setText(des);

            progressView.setVisibility(VISIBLE);
            progressView.bringToFront();
            progressView.invalidate();
        }
    }

    private void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(VISIBLE);
            emptyView.bringToFront();
            emptyView.invalidate();
        }
    }

    private void showErrorView() {
        if (errorView != null) {
            errorView.setVisibility(VISIBLE);
            errorView.bringToFront();
            errorView.invalidate();
        }
    }

    public void progressViewClickListener(OnClickListener onClickListener) {
        if (progressView != null) {
            progressView.setOnClickListener(onClickListener);
        }
    }

    public void emptyViewClickListener(OnClickListener onClickListener) {
        if (emptyView != null) {
            emptyView.setOnClickListener(onClickListener);
        }

    }

    public void errorViewClickListener(OnClickListener onClickListener) {
        if (errorView != null) {
            errorView.setOnClickListener(onClickListener);
        }
    }

    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void hideProgressView() {
        if (progressView != null) {
            progressView.setVisibility(GONE);
        }
    }

    private void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (errorView != null) {
            errorView.setVisibility(GONE);
        }
    }

    public View getProgressView() {
        return progressView;
    }

    public void setProgressView(int id, int[] margin, int size, int gravity) {
        try {
            this.progressView = inflater.inflate(id, null);
            this.progressView.setTag(TAG_PROGRESS);
            this.progressView.setClickable(true);
            this.progressView.setVisibility(GONE);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            tvProgressTitle = progressView.findViewById(R.id.progress_title);
            tvProgressDesc = progressView.findViewById(R.id.progress_desc);

            addView(this.progressView, layoutParams);
            setProgressViewSize(size, gravity);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public FrameViewLayout setProgressViewSize(int size, int gravity) {
        if (size == MATCH_PARENT) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = gravity;
            progressView.setLayoutParams(layoutParams);
            return this;
        } else {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = gravity;
            progressView.setLayoutParams(layoutParams);
            return this;
        }
    }

    public FrameViewLayout setErrorViewSize(int size, int gravity) {
        if (size == MATCH_PARENT) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = gravity;
            errorView.setLayoutParams(layoutParams);
            return this;
        } else {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = gravity;
            errorView.setLayoutParams(layoutParams);
            return this;
        }
    }

    public FrameViewLayout setEmptyViewSize(int size, int gravity) {
        if (size == MATCH_PARENT) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = gravity;
            emptyView.setLayoutParams(layoutParams);
            return this;
        } else {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = gravity;
            emptyView.setLayoutParams(layoutParams);
            return this;
        }
    }


    public View getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(int id, int[] margin, int size, int gravity) {
        try {
            this.emptyView = inflater.inflate(id, null);
            this.emptyView.setTag(TAG_EMPTY);
            this.emptyView.setClickable(true);
            this.emptyView.setVisibility(GONE);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            addView(this.emptyView, layoutParams);
            setEmptyViewSize(size, gravity);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

    }

    public View getErrorView() {
        return errorView;
    }

    public void setErrorView(int id, int[] margin, int size, int gravity) {
        try {
            this.errorView = inflater.inflate(id, null);
            this.errorView.setTag(TAG_ERROR);
            this.errorView.setClickable(true);
            this.errorView.setVisibility(GONE);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            addView(this.errorView, layoutParams);
            setErrorViewSize(size, gravity);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}