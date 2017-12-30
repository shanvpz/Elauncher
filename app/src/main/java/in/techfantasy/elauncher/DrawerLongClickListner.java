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
    Item[] itemsForListener;

    public DrawerLongClickListner(Context ctx, SlidingDrawer drawer, RelativeLayout homeview,Item[] items) {
        this.ctx = ctx;
        this.drawer = drawer;
        this.homeview = homeview;
        this.itemsForListener=items;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
        MainActivity.appLaunchable=false;
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(view.getWidth(),view.getHeight());
        lp.leftMargin= (int) view.getX();
        lp.topMargin= (int) view.getY();
        LayoutInflater li=(LayoutInflater)ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
         LinearLayout ll= (LinearLayout) li.inflate(R.layout.draweritem,null);
        ((ImageView)ll.findViewById(R.id.icon_image)).setImageDrawable(((ImageView)view.findViewById(R.id.icon_image)).getDrawable());
        ((TextView)ll.findViewById(R.id.icon_text)).setText(((TextView)view.findViewById(R.id.icon_text)).getText());


        ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setOnTouchListener(new AppTouchListener());
                return false;
            }
        });



         ll.setOnClickListener(new AppClickListner(ctx,itemsForListener));
         String[] data = new String[2];
        data[0]=itemsForListener[i].packageName;
        data[1]=itemsForListener[i].name;
        ll.setTag(data);

        homeview.addView(ll,lp);
        drawer.animateClose();
        drawer.bringToFront();
        return false;
    }
}
