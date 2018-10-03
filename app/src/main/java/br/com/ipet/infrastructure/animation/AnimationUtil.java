package br.com.ipet.infrastructure.animation;

import android.view.View;
import android.view.animation.TranslateAnimation;

public class AnimationUtil {

    public static void slideUp(final View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(0, 0, view.getHeight(), 0);
            animate.setDuration(300);
            animate.setFillAfter(true);
            view.startAnimation(animate);
        }
    }

    public static void slideDown(final View view) {
        if (view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
            TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
            animate.setDuration(300);
            animate.setFillAfter(true);
            view.startAnimation(animate);
        }
    }
}
