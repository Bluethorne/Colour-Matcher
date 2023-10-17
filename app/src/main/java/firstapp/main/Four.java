package firstapp.main;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class Four {

    public static final Integer[] ALL_COLORS = {getColorInt(R.color.Red), getColorInt(R.color.Orange), getColorInt(R.color.Yellow), getColorInt(R.color.Green), getColorInt(R.color.Blue), getColorInt(R.color.Purple), getColorInt(R.color.Pink), getColorInt(R.color.Brown), getColorInt(R.color.white), getColorInt(R.color.black)};
    private final Integer[] colors = {null, null, null, null};

    public static final int NULL_COL = getColorInt(R.color.Dark_Blue);

    public Four() {}

    public Integer getColor(int num) {
        if(num <= 0 || num > 4) {
            return null;
        }
        return colors[num-1];
    }

    public void set(int num, @Nullable Integer color) {
        if(num <= 0 || num > 4) {
            return;
        }
        colors[num-1] = color;
    }

    public boolean isEqual(Four compare) {
        for(int i = 1; i <= 4; i++) {
            if(!getColor(i).equals(compare.getColor(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isFull() {
        return !((colors[0] == null) || (colors[1] == null) || (colors[2] == null) || (colors[3] == null));
    }

    public Integer[] getCopy() {
        return colors.clone();
    }

    public static Four getRandom() {
        Four four = new Four();
        int l = ALL_COLORS.length;
        for(int i = 0; i < 4; i++) {
            int rand = (int) (Math.random() * (l-1));
            int color = ALL_COLORS[rand];
            four.set(i+1, color);
        }
        return four;
    }

    private static int getColorInt(int id) {
        return ContextCompat.getColor(MainActivity.m.getBaseContext(), id);
    }

    public static Integer getPosOfColor(int col) {
        for(int i = 0; i < ALL_COLORS.length; i++) {
            if(ALL_COLORS[i].equals(col)) {
                return i;
            }
        }
        return null;
    }

}
