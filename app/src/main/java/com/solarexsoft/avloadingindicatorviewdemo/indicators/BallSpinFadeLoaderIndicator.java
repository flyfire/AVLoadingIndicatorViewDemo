package com.solarexsoft.avloadingindicatorviewdemo.indicators;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.solarexsoft.avloadingindicatorviewdemo.Indicator;

import java.util.ArrayList;

/**
 * Created by Jack on 2015/10/20.
 */
public class BallSpinFadeLoaderIndicator extends Indicator {

    public static final float SCALE = 1.0f;

    public static final int ALPHA = 255;

    float[] scaleFloats = new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE};

    int[] alphas = new int[]{ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA};


    @Override
    public void draw(Canvas canvas, Paint paint) {
        float radius = getWidth() / 10;
        for (int i = 0; i < 8; i++) {
            canvas.save();
            PointF point = circleAt(getWidth(), getHeight(), getWidth() / 2 - radius, i * (Math.PI / 4));
            canvas.translate(point.x, point.y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            paint.setAlpha(alphas[i]);
            canvas.drawCircle(0, 0, radius, paint);
            canvas.restore();
        }
    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        int[] delays = {0, 120, 240, 360, 480, 600, 720, 780, 840};
        for (int i = 0; i < 8; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.4f, 1);
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            addUpdateListener(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });

            ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 77, 255);
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay(delays[i]);
            addUpdateListener(alphaAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alphas[index] = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }

    /**
     * ???O????????????(a,b),?????????R,???A??????X??????????????.
     * ??????A????????????(a+R*cos??,b+R*sin??)
     *
     * @param width
     * @param height
     * @param radius
     * @param angle
     * @return
     */
    PointF circleAt(int width, int height, float radius, double angle) {
        float x = (float) (width / 2 + radius * (Math.cos(angle)));
        float y = (float) (height / 2 + radius * (Math.sin(angle)));
        return new PointF(x, y);
    }


}
