package in.techfantasy.elauncher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    String iconlocation;
    boolean landscape;
    public void cacheIcon(){
        if(iconlocation==null)
          new File(MainActivity.activity.getApplicationInfo().dataDir+"/cachedApps/").mkdirs();
        if(icon!=null){
            iconlocation=MainActivity.activity.getApplicationInfo().dataDir+"/cachedApps/"+packageName+name;
            FileOutputStream fos=null;
            try{
                fos = new FileOutputStream(iconlocation);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            if (fos!=null){
                Tools.drawableToBitmap(icon).compress(Bitmap.CompressFormat.PNG,100,fos);
                try{
                    fos.flush();
                    fos.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }else{
                iconlocation=null;
            }
        }

    }

    public Bitmap getCachedIcon(){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inDither=true;

        if(iconlocation!=null){
            File cachedIcon=new File(iconlocation);
            if(cachedIcon.exists()){
                return BitmapFactory.decodeFile(cachedIcon.getAbsolutePath(),options);
            }
        }
        return null;
    }
    public void deleteIcon(){
        if(iconlocation!=null)
             new File(iconlocation).delete();
    }
}
