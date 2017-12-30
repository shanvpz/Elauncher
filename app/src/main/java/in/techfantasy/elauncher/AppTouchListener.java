package in.techfantasy.elauncher;

import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by campusiq on 29/12/17.
 */

public class AppTouchListener implements View.OnTouchListener {
    //int iconSize;
    int leftMargins;
    int topMargins;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(v.getWidth(),v.getHeight());

                leftMargins = (int) (event.getRawX()-v.getWidth()/2);
                topMargins= (int) (event.getRawY()-v.getHeight()/2);

                if(leftMargins+v.getWidth()>v.getRootView().getWidth())
                    leftMargins=v.getRootView().getWidth()-v.getWidth();


                if(leftMargins<0)
                    leftMargins=0;

                if(topMargins+v.getHeight()>((View)v.getParent()).getHeight())
                    topMargins=((View)v.getParent()).getHeight()-v.getHeight();

                if(topMargins<0)
                    topMargins=0;


                lp.leftMargin= leftMargins;
                lp.topMargin= topMargins;
                v.setLayoutParams(lp);
                break;
            case MotionEvent.ACTION_UP:
                v.setOnTouchListener(null);
                break;
        }
        return true;
    }

//    public AppTouchListener(int iconSize) {
//        this.iconSize = iconSize;
//    }
}
