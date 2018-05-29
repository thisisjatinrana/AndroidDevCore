package rana.jatin.core.widget.circularReveal.util;

import android.animation.Animator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import rana.jatin.core.widget.circularReveal.animation.ViewAnimationUtils;

public class CircularRevealUtil {
    private static final CircularRevealUtil ourInstance = new CircularRevealUtil();

    private CircularRevealUtil() {
    }

    public static CircularRevealUtil getInstance() {
        return ourInstance;
    }

    public void startAnimation(View view, int duration, Interpolator interpolator, int x, int y) {
        if (view == null)
            return;
        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        if (interpolator == null)
            interpolator = new AccelerateDecelerateInterpolator();
        Animator animator = ViewAnimationUtils.createCircularReveal(view, x, y, 0, finalRadius);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.start();
    }
}
