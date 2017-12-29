package in.techfantasy.elauncher;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;

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
        return false;
    }
}
