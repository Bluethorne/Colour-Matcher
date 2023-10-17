package firstapp.main;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

public class InfiniteCircle extends Circle {

    public InfiniteCircle(Context context) {
        super(context);
    }

    public InfiniteCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InfiniteCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InfiniteCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int c = this.getCircleColor();
        ClipData.Item item = new ClipData.Item(String.valueOf(c));
        ClipData dragData = new ClipData("color", new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN }, item);
        View.DragShadowBuilder shadow = new CircleDragShadowBuilder(this, this.getContext().getDrawable(R.drawable.circle), c);
        this.startDragAndDrop(dragData, shadow, false, DRAG_FLAG_OPAQUE);
        return super.onTouchEvent(event);
    }

}
