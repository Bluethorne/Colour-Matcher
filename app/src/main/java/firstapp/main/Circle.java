package firstapp.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class Circle extends View {

    protected int circleColor = getColorInt(R.color.white);

    public Circle(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public Circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init(Context context, AttributeSet attrs, int sa, int sr) {
        int color = circleColor;
        if(attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Circle, sa, sr);
            try {
                color = a.getColor(R.styleable.Circle_circleColor, color);
            } finally {
                a.recycle();
            }
        }
        setCircleColor(color);
    }

    public void setCircleColor(int color) {
        circleColor = color;
        GradientDrawable d = (GradientDrawable) getContext().getDrawable(R.drawable.circle);
        d.setColor(color);
        d.setStroke(2, getOpposite(color));
        this.setBackground(d);
    }

    public static int getOpposite(int color) {
        String hex = String.format("%08X", color);
        int r = Integer.valueOf(hex.substring(2, 4), 16);
        int g = Integer.valueOf(hex.substring(4, 6), 16);
        int b = Integer.valueOf(hex.substring(6, 8), 16);
        String ophex = "FF" + String.format("%02X", 255 - r) + String.format("%02X", 255 - g) + String.format("%02X", 255 - b);
        return (int) Long.parseLong(ophex, 16);
    }

    public int getCircleColor() {
        return circleColor;
    }

    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        int l = Math.min(w, h);
        if(h <= 0 || w <= 0) {
            return;
        }
        setMeasuredDimension(l, l);
    }

    private static int getColorInt(int id) {
        return ContextCompat.getColor(MainActivity.m.getBaseContext(), id);
    }
}
