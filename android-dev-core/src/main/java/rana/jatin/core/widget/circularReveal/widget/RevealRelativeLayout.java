package rana.jatin.core.widget.circularReveal.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import rana.jatin.core.widget.circularReveal.animation.RevealViewGroup;
import rana.jatin.core.widget.circularReveal.animation.ViewRevealManager;

public class RevealRelativeLayout extends RelativeLayout implements RevealViewGroup {
  private ViewRevealManager manager;

  public RevealRelativeLayout(Context context) {
    this(context, null);
  }

  public RevealRelativeLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RevealRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    manager = new ViewRevealManager();
  }

  @Override protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
    try {
      canvas.save();
      return manager.transform(canvas, child)
          & super.drawChild(canvas, child, drawingTime);
    } finally {
      canvas.restore();
    }
  }

  public void setViewRevealManager(ViewRevealManager manager) {
    if (manager == null) {
      throw new NullPointerException("ViewRevealManager is null");
    }

    this.manager = manager;
  }

  @Override public ViewRevealManager getViewRevealManager() {
    return manager;
  }
}