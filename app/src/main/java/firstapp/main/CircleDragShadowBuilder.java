package firstapp.main;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.core.graphics.drawable.DrawableCompat;

import java.util.Arrays;

public class CircleDragShadowBuilder extends View.DragShadowBuilder {

    private static Drawable shadow;

    public CircleDragShadowBuilder(View v, Drawable d, int color) {
        super(v);
        shadow = d;
        ((GradientDrawable) d).setColor(color);
        ((GradientDrawable) d).setStroke(2, Circle.getOpposite(color));
    }

    @Override
    public void onProvideShadowMetrics (Point size, Point touch) {
        int width, height;
        width = getView().getWidth();
        height = getView().getHeight();
        shadow.setBounds(0, 0, width, height);
        size.set(width, height);
        touch.set(width / 2, height / 2);
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        shadow.draw(canvas);
    }

}
