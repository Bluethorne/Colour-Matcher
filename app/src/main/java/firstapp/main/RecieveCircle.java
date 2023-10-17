package firstapp.main;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class RecieveCircle extends Circle {

    public RecieveCircle(Context context) {
        super(context);
    }

    public RecieveCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecieveCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecieveCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void init(Context context, AttributeSet attrs, int sa, int sr) {
        super.init(context, attrs, sa, sr);
        this.setOnDragListener((v,e)-> {
            if(e.getAction() == DragEvent.ACTION_DROP) {
                if(!(this.getParent() instanceof LinearLayout)) {
                    return MainActivity.animate(v, e, e.getX() + getRelativeLeft(this), e.getY() + getRelativeTop(this));
                }
                LinearLayout parent = (LinearLayout) this.getParent();
                if(!parent.getResources().getResourceName(parent.getId()).equals("firstapp.main:id/guess" + (MainActivity.game.getCurrentGuess()+1))) {
                    return MainActivity.animate(v, e, e.getX() + getRelativeLeft(this), e.getY() + getRelativeTop(this));
                }
                ClipData.Item item = e.getClipData().getItemAt(0);
                int c = Integer.valueOf(item.getText().toString());
                ((Circle) v).setCircleColor(c);
                String id = this.getResources().getResourceName(this.getId());
                int cNum = Integer.valueOf(id.substring(22, 23));
                MainActivity.game.getCurrentFour().set(cNum, c);
            }
            return true;
        });
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!(this.getParent() instanceof LinearLayout)) {
            return false;
        }
        LinearLayout parent = (LinearLayout) this.getParent();
        if(!parent.getResources().getResourceName(parent.getId()).equals("firstapp.main:id/guess" + (MainActivity.game.getCurrentGuess()+1))) {
            return false;
        }
        int c = this.getCircleColor();
        if(c == Four.NULL_COL) {
            return false;
        }
        ClipData.Item item = new ClipData.Item(String.valueOf(c));
        ClipData dragData = new ClipData("color", new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN }, item);
        View.DragShadowBuilder shadow = new CircleDragShadowBuilder(this, this.getContext().getDrawable(R.drawable.circle), c);
        this.startDragAndDrop(dragData, shadow, true, DRAG_FLAG_OPAQUE);

        this.setCircleColor(Four.NULL_COL);
        String id = this.getResources().getResourceName(this.getId());
        int cNum = Integer.valueOf(id.substring(22, 23));
        MainActivity.game.getCurrentFour().set(cNum, null);

        return super.onTouchEvent(event);
    }

    private int getRelativeLeft(View myView) {
        if (myView.getParent() == myView.getRootView()) {
            return myView.getLeft();
        } else {
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
        }
    }

    private int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView()) {
            return myView.getTop();
        } else {
            return myView.getTop() + getRelativeTop((View) myView.getParent());
        }
    }

}
