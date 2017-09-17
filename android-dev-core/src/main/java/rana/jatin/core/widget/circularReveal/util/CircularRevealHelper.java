package rana.jatin.core.widget.circularReveal.util;

import android.animation.Animator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import rana.jatin.core.widget.circularReveal.animation.ViewAnimationUtils;


/**
 * Created by jatin on 8/1/2017.
 */

public class CircularRevealHelper {
    private static final CircularRevealHelper ourInstance = new CircularRevealHelper();

    public static CircularRevealHelper getInstance() {
        return ourInstance;
    }


    private int duration;
    private Interpolator interpolator;
    private View view;
    private int x,y;

    private CircularRevealHelper() {
    }

    public Animator start() {

        if(view==null)
            return null;
        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        if(interpolator==null)
            interpolator=new AccelerateDecelerateInterpolator();
        Animator animator = ViewAnimationUtils.createCircularReveal(view, x, y, 0, finalRadius);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.start();
        return animator;
    }

    public CircularRevealHelper createAnim(View view, int duration, Interpolator interpolator, int x, int y)
    {
        this.view=view;
        this.duration=duration;
        this.interpolator=interpolator;
        this.x=x;
        this.y=y;
        return this;
    }

    public CircularRevealHelper setDuration(int duration){
        this.duration=duration;
        return this;

    }

    public CircularRevealHelper setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;

    }

    public CircularRevealHelper setView(View v) {
        this.view = v;
        return this;

    }

    public CircularRevealHelper setX(int x) {
        this.x = x;
        return this;
    }

    public CircularRevealHelper setY(int y) {
        this.y = y;
        return this;
    }

    public CircularRevealHelper setXY(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

}
