package in.techfantasy.elauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

/**
 * Created by campusiq on 29/12/17.
 */

public class DrawerLongClickListner implements AdapterView.OnItemLongClickListener{
    Context ctx;
    SlidingDrawer drawer;
    RelativeLayout homeview;

    public DrawerLongClickListner(Context ctx, SlidingDrawer drawer, RelativeLayout homeview) {
        this.ctx = ctx;
        this.drawer = drawer;
        this.homeview = homeview;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        MainActivity.appLaunchable=false;
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(view.getWidth(),view.getHeight());
        lp.leftMargin= (int) view.getX();
        lp.topMargin= (int) view.getY();
        LayoutInflater li=(LayoutInflater)ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll= (LinearLayout) li.inflate(R.layout.draweritem,null);
        ((ImageView)ll.findViewById(R.id.icon_image)).setImageDrawable(((ImageView)view.findViewById(R.id.icon_image)).getDrawable());
        ((TextView)ll.findViewById(R.id.icon_text)).setText(((TextView)view.findViewById(R.id.icon_text)).getText());


        homeview.addView(ll,lp);
        drawer.animateClose();
        drawer.bringToFront();
        return false;
    }
}
