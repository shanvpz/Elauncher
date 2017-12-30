package in.techfantasy.elauncher;

import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.Serializable;

import static in.techfantasy.elauncher.MainActivity.*;

/**
 * Created by campusiq on 29/12/17.
 */

public class Item implements Serializable{
    transient Drawable icon;
    String name;
    String label;
    String packageName;
    boolean visible;
    int x,y;
    String itemlocation;
    public void cacheIcon(){
        if(itemlocation==null)
          new File(MainActivity.ac.getApplicationInfo().dataDir+"/cachedApps/").mkdirs();

    }
    public void deleteIcon(){

    }
}
