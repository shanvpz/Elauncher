package in.techfantasy.elauncher;

import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by campusiq on 29/12/17.
 */

public class AppTouchListener implements View.OnTouchListener {
    int iconSize;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(iconSize,iconSize);
                lp.leftMargin= (int) (event.getRawX()-iconSize/2);
                lp.topMargin= (int) (event.getRawY()-iconSize/2);
                v.setLayoutParams(lp);
        }
        return false;
    }

    public AppTouchListener(int iconSize) {
        this.iconSize = iconSize;
    }
}
