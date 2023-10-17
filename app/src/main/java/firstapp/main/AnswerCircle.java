package firstapp.main;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class AnswerCircle extends Circle {

    public AnswerCircle(Context context) {
        super(context);
    }

    public AnswerCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnswerCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AnswerCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setColor(int c) {
        this.setCircleColor(c);
    }

    @Override
    public void setCircleColor(int color) {
        if(color != Four.NULL_COL) {
            super.setCircleColor(color);
            return;
        }
        this.circleColor = color;
        GradientDrawable d = (GradientDrawable) getContext().getDrawable(R.drawable.circle);
        d.setColor(color);
        d.setStroke(2, color);
        this.setBackground(d);
    }

}
