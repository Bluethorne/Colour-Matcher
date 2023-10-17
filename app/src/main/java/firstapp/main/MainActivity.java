package firstapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static Game game;
    public static MainActivity m;

    private PopupWindow pw = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        MainActivity.m = this;
    }

    public void start(View v) {
        closePopUp(v);
        game = new Game();
        setContentView(R.layout.activity_main);
        findViewById(R.id.root).setOnDragListener((r,e)-> {
            if(e.getAction() != DragEvent.ACTION_DROP) {
                return true;
            }
            return animate(r, e, e.getX(), e.getY());
        });
    }

    public void end() {
        game = null;
        setContentView(R.layout.start);
    }

    public void submit(View v) {
        game.guess();
    }

    public void closePopUp(View v) {
        if(pw != null) {
            pw.dismiss();
            pw = null;
        }
    }

    public void openPopUp(boolean won, Four ans) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.result, null);

        CharSequence text = (CharSequence) (won ? "You Won" : "You Lost");
        ((TextView) popupView.findViewById(R.id.textView)).setText(text);

        LinearLayout ll = (LinearLayout) popupView.findViewById(R.id.ans);
        ((Circle) ll.findViewById(R.id.c1)).setCircleColor(ans.getColor(1));
        ((Circle) ll.findViewById(R.id.c2)).setCircleColor(ans.getColor(2));
        ((Circle) ll.findViewById(R.id.c3)).setCircleColor(ans.getColor(3));
        ((Circle) ll.findViewById(R.id.c4)).setCircleColor(ans.getColor(4));

        int width = 1000;
        int height = 500;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);

        popupWindow.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 0, 0);

        pw = popupWindow;
    }

    public static boolean animate(View v, DragEvent e, float x, float y) {
        if((boolean) e.getLocalState() == true) {

            ClipData.Item item = e.getClipData().getItemAt(0);
            int c = Integer.valueOf(item.getText().toString());
            int pos = Four.getPosOfColor(c);
            int id = MainActivity.m.getResources().getIdentifier("view" + pos, "id", "firstapp.main");
            View guess = MainActivity.m.findViewById(id);

            ConstraintLayout layout = (ConstraintLayout) m.findViewById(R.id.root);
            Circle view = new Circle(m.getBaseContext());
            view.setCircleColor(c);
            view.setId(View.generateViewId());
            layout.addView(view,0);
            view.bringToFront();
            view.setLayoutParams(new ViewGroup.LayoutParams(guess.getMeasuredWidth(), guess.getMeasuredWidth()));
            view.getBackground().setAlpha(128);

            Animation animation = new TranslateAnimation(x - guess.getMeasuredWidth(), guess.getX(), y - guess.getMeasuredHeight(), guess.getY());
            animation.setDuration(200);
            view.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    layout.removeView(view);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            return true;
        }
        return false;
    }

}