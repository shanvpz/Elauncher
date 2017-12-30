package in.techfantasy.elauncher;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import java.util.ArrayList;

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

//         String[] data = new String[2];
//        data[0]=itemsForListener[i].packageName;
//        data[1]=itemsForListener[i].name;


        AppSerializableData objectData=SerializationTools.loadSerializableData();
        if(objectData==null)
            objectData=new AppSerializableData();

        if(objectData.apps==null)
            objectData.apps=new ArrayList<Item>();

        Item itemToAdd=itemsForListener[i];
        itemToAdd.x= (int) view.getX();
        itemToAdd.y= (int) view.getY();
        if(MainActivity.activity.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            itemToAdd.landscape = true;
        }
        else{
            itemToAdd.landscape = false;
        }
        //ll.setTag(itemToAdd);
        objectData.apps.add(itemToAdd);
        SerializationTools.serializeData(objectData);

        itemToAdd.addToHome(ctx,homeview);
        drawer.animateClose();
        drawer.bringToFront();
        return false;
    }
}
