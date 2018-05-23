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

    public static final  int MATCH_PARENT = 2;
    public static final  int WRAP_CONTENT = 1;
    private static final String TAG_LOADING = "ProgressView.TAG_LOADING";
    private static final String TAG_EMPTY = "ProgressView.TAG_EMPTY";
    private static final String TAG_ERROR = "ProgressView.TAG_ERROR";
    final String CONTENT = "type_content";
    final String LOADING = "type_loading";
    final String EMPTY = "type_empty";
    final String ERROR = "type_error";
    LayoutInflater inflater;
    View view;
    List<View> contentViews = new ArrayList<>();
    View loadingView;
    View emptyView;
    View errorView;
    private String TAG = FrameViewLayout.class.getName();
    private String state = CONTENT;
    private LayoutParams layoutParams;
    private TextView tvProgressTitle;
    private TextView tvProgressDesc;
    public static long DELAY = 0;

    public FrameViewLayout(Context context) {
        super(context);
    }

    public FrameViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FrameViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
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
            setLoadingView(progressViewLayout, margin,ta.getInt(R.styleable.FrameViewLayout_progressViewTheme,MATCH_PARENT),ta.getInt(R.styleable.FrameViewLayout_progressViewGravity,Gravity.NO_GRAVITY));
            setEmptyView(emptyViewLayout, margin,ta.getInt(R.styleable.FrameViewLayout_emptyViewTheme,MATCH_PARENT),ta.getInt(R.styleable.FrameViewLayout_emptyViewGravity,Gravity.NO_GRAVITY));
            setErrorView(errorViewLayout, margin,ta.getInt(R.styleable.FrameViewLayout_errorViewTheme,MATCH_PARENT),ta.getInt(R.styleable.FrameViewLayout_errorViewGravity,Gravity.NO_GRAVITY));
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
        if (child.getTag() == null || (!child.getTag().equals(TAG_LOADING)
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
        }, DELAY);
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
        }, DELAY);
    }

    /**
     * Hide content and show the progress bar
     *
     * @param skipIds Ids of views to not hide
     */
    public void showLoading(List<Integer> skipIds) {
        switchState(LOADING, skipIds);
    }

    /**
     * Hide content and show the progress bar
     */
    public void showLoading() {
        switchState(LOADING, Collections.<Integer>emptyList());
    }

    /**
     * Hide content and show the progress bar with progress text
     */
    public void showLoading(String progress, String des) {
        switchState(LOADING, progress, des, Collections.<Integer>emptyList());
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
        }, DELAY);
    }

    public void showEmpty() {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchState(EMPTY, Collections.<Integer>emptyList());
            }
        }, DELAY);
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
        }, DELAY);
    }


    public void showError() {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchState(ERROR, Collections.<Integer>emptyList());
            }
        }, DELAY);
    }

    public String getState() {
        return state;
    }

    public boolean isContent() {
        return state.equals(CONTENT);
    }

    public boolean isLoading() {
        return state.equals(LOADING);
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
                hideLoadingView();
                hideEmptyView();
                hideErrorView();

                // setContentVisibility(true, skipIds);
                break;
            case LOADING:
                hideEmptyView();
                hideErrorView();

                showLoadingView();
                // setContentVisibility(false, skipIds);
                break;
            case EMPTY:
                hideLoadingView();
                hideErrorView();

                showEmptyView();
                //   setContentVisibility(false, skipIds);
                break;
            case ERROR:
                hideLoadingView();
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
                hideLoadingView();
                hideEmptyView();
                hideErrorView();

                // setContentVisibility(true, skipIds);
                break;
            case LOADING:
                hideEmptyView();
                hideErrorView();

                showLoadingView(messageTitle, messageDesc);
                // setContentVisibility(false, skipIds);
                break;
            case EMPTY:
                hideLoadingView();
                hideErrorView();

                showEmptyView();
                //   setContentVisibility(false, skipIds);
                break;
            case ERROR:
                hideLoadingView();
                hideEmptyView();

                showErrorView();
                // setContentVisibility(false, skipIds);
                break;
        }
    }

    private void showLoadingView() {
        if (loadingView != null) {
            loadingView.setVisibility(VISIBLE);
            loadingView.bringToFront();
            loadingView.invalidate();
        }
    }

    private void showLoadingView(String progress, String des) {
        if (loadingView != null) {
            if (tvProgressTitle != null)
                tvProgressTitle.setText(progress);

            if (tvProgressDesc != null)
                tvProgressDesc.setText(des);

            loadingView.setVisibility(VISIBLE);
            loadingView.bringToFront();
            loadingView.invalidate();
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

    public void loadingClickListener(OnClickListener onClickListener) {
        if (loadingView != null) {
            loadingView.setOnClickListener(onClickListener);
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

    private void hideLoadingView() {
        if (loadingView != null) {
            loadingView.setVisibility(GONE);
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

    public View getLoadingView() {
        return loadingView;
    }

    public void setLoadingView(int id,int[] margin,int theme,int gravity) {
        try {
            this.loadingView = inflater.inflate(id, null);
            this.loadingView.setTag(TAG_LOADING);
            this.loadingView.setClickable(true);
            this.loadingView.setVisibility(GONE);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            tvProgressTitle = loadingView.findViewById(R.id.progress_title);
            tvProgressDesc = loadingView.findViewById(R.id.progress_desc);

            addView(this.loadingView, layoutParams);
            setLoadingViewTheme(theme,gravity);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public FrameViewLayout setLoadingViewTheme(int theme, int gravity) {
        if (theme==MATCH_PARENT) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = gravity;
            loadingView.setLayoutParams(layoutParams);
            return this;
        } else {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = gravity;
            loadingView.setLayoutParams(layoutParams);
            return this;
        }
    }

    public FrameViewLayout setErrorViewTheme(int theme, int gravity) {
        if (theme == MATCH_PARENT) {
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

    public FrameViewLayout setEmptyViewTheme(int theme, int gravity) {
        if (theme == MATCH_PARENT) {
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

    public void setEmptyView(int id,int[] margin,int theme,int gravity) {
        try {
            this.emptyView = inflater.inflate(id, null);
            this.emptyView.setTag(TAG_EMPTY);
            this.emptyView.setClickable(true);
            this.emptyView.setVisibility(GONE);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            addView(this.emptyView, layoutParams);
            setEmptyViewTheme(theme, gravity);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

    }

    public View getErrorView() {
        return errorView;
    }

    public void setErrorView(int id,int[] margin,int theme,int gravity) {
        try {
            this.errorView = inflater.inflate(id, null);
            this.errorView.setTag(TAG_ERROR);
            this.errorView.setClickable(true);
            this.errorView.setVisibility(GONE);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            addView(this.errorView, layoutParams);
            setErrorViewTheme(theme, gravity);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }
}