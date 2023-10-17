package firstapp.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class HoverButton extends AppCompatButton {

    private int originalColor;

    public HoverButton(@NonNull Context context) {
        super(context);
    }

    public HoverButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HoverButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getAction() == MotionEvent.ACTION_DOWN) {
            originalColor = ((ColorDrawable) this.getBackground()).getColor();
            final int mult = 60;
            Integer[] rgb = getRGB(originalColor);
            int total = rgb[0] + rgb[1] + rgb[2];
            int rdiff = (int) (rgb[0]*mult/total);
            int gdiff = (int) (rgb[1]*mult/total);
            int bdiff = (int) (rgb[2]*mult/total);
            int newR;
            int newG;
            int newB;
            if(total > 382.5) {
                newR = rgb[0] - rdiff;
                newG = rgb[1] - gdiff;
                newB = rgb[2] - bdiff;
            } else {
                int ex = ((int)255*mult/total);
                newR = rgb[0] + (ex - rdiff)*total/(765-total);
                newG = rgb[1] + (ex - gdiff)*total/(765-total);
                newB = rgb[2] + (ex - bdiff)*total/(765-total);
            }
            int newCol = Color.rgb(newR, newG, newB);
            this.setBackgroundColor(newCol);
        } else if(e.getAction() == MotionEvent.ACTION_UP) {
            this.setBackgroundColor(originalColor);
        }
        return super.onTouchEvent(e);
    }

    private static Integer[] getRGB(int col) {
        Integer[] l = {Color.red(col), Color.green(col), Color.blue(col)};
        return l;
    }

}
