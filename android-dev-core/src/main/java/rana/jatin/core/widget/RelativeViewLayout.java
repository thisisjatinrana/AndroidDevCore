package rana.jatin.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rana.jatin.core.R;
import rana.jatin.core.widget.circularReveal.widget.RevealRelativeLayout;

public class RelativeViewLayout extends RevealRelativeLayout {

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
    private List<View> contentViews = new ArrayList<>();
    private View progressView;
    private View emptyView;
    private View errorView;
    private TextView tvProgressTitle;
    private TextView tvProgressDesc;
    private String TAG = RelativeViewLayout.class.getName();
    private String state = CONTENT;
    private RelativeLayout.LayoutParams layoutParams;
    private int delay = 0;

    public RelativeViewLayout(Context context) {
        super(context);
    }

    public RelativeViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        showContent();
    }

    public RelativeViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        showContent();
    }

    private void init(Context context, AttributeSet attrs) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RelativeViewLayout);
        int emptyViewLayout, progressViewLayout, errorViewLayout;
        try {
            emptyViewLayout = ta.getResourceId(R.styleable.RelativeViewLayout_emptyView, 0);
            progressViewLayout = ta.getResourceId(R.styleable.RelativeViewLayout_progressView, 0);
            errorViewLayout = ta.getResourceId(R.styleable.RelativeViewLayout_errorView, 0);
            int marginTop = ta.getDimensionPixelSize(R.styleable.RelativeViewLayout_marginTop, 0);
            int marginBottom = ta.getDimensionPixelSize(R.styleable.RelativeViewLayout_marginBottom, 0);
            int marginLeft = ta.getDimensionPixelSize(R.styleable.RelativeViewLayout_marginLeft, 0);
            int marginRight = ta.getDimensionPixelSize(R.styleable.RelativeViewLayout_marginRight, 0);
            int[] margin = {marginLeft, marginTop, marginRight, marginBottom};
            setProgressView(progressViewLayout, margin, ta.getInt(R.styleable.RelativeViewLayout_progressViewSize, MATCH_PARENT), ta.getInt(R.styleable.RelativeViewLayout_progressViewAlign, Gravity.NO_GRAVITY));
            setEmptyView(emptyViewLayout, margin, ta.getInt(R.styleable.RelativeViewLayout_emptyViewSize, MATCH_PARENT), ta.getInt(R.styleable.RelativeViewLayout_emptyViewAlign, Gravity.NO_GRAVITY));
            setErrorView(errorViewLayout, margin, ta.getInt(R.styleable.RelativeViewLayout_errorViewSize, MATCH_PARENT), ta.getInt(R.styleable.RelativeViewLayout_errorViewAlign, Gravity.NO_GRAVITY));
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

    public void progressViewClickListener(View.OnClickListener onClickListener) {
        if (progressView != null) {
            progressView.setOnClickListener(onClickListener);
        }
    }

    public void emptyViewClickListener(View.OnClickListener onClickListener) {
        if (emptyView != null) {
            emptyView.setOnClickListener(onClickListener);
        }

    }

    public void errorViewClickListener(View.OnClickListener onClickListener) {
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

            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            if (margin != null)
                layoutParams.setMargins(margin[0], margin[1], margin[2], margin[3]);

            tvProgressTitle = progressView.findViewById(R.id.progress_title);
            tvProgressDesc = progressView.findViewById(R.id.progress_desc);

            addView(this.progressView, layoutParams);
            setProgressViewSize(size, gravity);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }


    public RelativeViewLayout setProgressViewSize(int size, int gravity) {
        try {
            if (size == MATCH_PARENT) {
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(gravity);
                this.progressView.setLayoutParams(layoutParams);
            } else {
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(gravity);
                this.progressView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return this;
    }

    public RelativeViewLayout setErrorViewSize(int size, int gravity) {
        try {
            if (size == MATCH_PARENT) {
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(gravity);
                this.errorView.setLayoutParams(layoutParams);
            } else {
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(gravity);
                this.errorView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return this;
    }

    public RelativeViewLayout setEmptyViewSize(int size, int gravity) {
        try {
            if (size == MATCH_PARENT) {
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(gravity);
                this.emptyView.setLayoutParams(layoutParams);
            } else {
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(gravity);
                this.emptyView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return this;
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

            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            if (margin != null)
                layoutParams.setMargins(margin[0], margin[1], margin[2], margin[3]);

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

            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            if (margin != null)
                layoutParams.setMargins(margin[0], margin[1], margin[2], margin[3]);
            addView(this.errorView, layoutParams);
            setErrorViewSize(size, gravity);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}