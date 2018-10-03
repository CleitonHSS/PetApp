package br.com.ipet.view.listener;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import br.com.ipet.R;

public class IconClickListener implements View.OnClickListener {

    private Context context;
    private View viewSheet;
    private View floatb;
    private View toobar;
    private Interpolator interpolator;
    private int height;
    private boolean backShown = false;
    private Drawable open;
    private Drawable close;

    private final AnimatorSet animatorSet = new AnimatorSet();

    public IconClickListener(Context context, View sheet, View floatb, View toobar, @Nullable Interpolator interpolator,
            @Nullable Drawable openIcon, @Nullable Drawable closeIcon) {
        this.context = context;
        this.viewSheet = sheet;
        this.floatb = floatb;
        this.toobar = toobar;
        this.interpolator = interpolator;
        this.open = openIcon;
        this.close = closeIcon;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
    }

    @Override
    public void onClick(View view) {
        backShown = !backShown;

        // Cancel the existing animations
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();
        updateIcon(view);
        sheetsAnimator(viewSheet);
        sheetsAnimator(floatb);

    }

    private void sheetsAnimator(View view) {
        final int translateY = height - context.getResources().getDimensionPixelSize(R.dimen.product_grid_reveal_height);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", backShown ? translateY : 0);
        animator.setDuration(500);
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        animatorSet.play(animator);
        animator.start();
    }

    private void updateIcon(View view) {
        if (open != null && close != null) {
            if (!(view instanceof ImageView)) {
                throw new IllegalArgumentException("updateIcon() must be called on an ImageView");
            }
            if (backShown) {
                ((ImageView) view).setImageDrawable(close);
                toobar.setPadding(56, 12, 12, 12);
            } else {
                ((ImageView) view).setImageDrawable(open);
                toobar.setPadding(300, 12, 12, 12);

            }
        }
    }
}
