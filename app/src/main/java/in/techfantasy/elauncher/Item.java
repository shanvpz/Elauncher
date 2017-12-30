package in.techfantasy.elauncher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    public void addToHome(Context ctx,RelativeLayout homeViewForAdapter){
        MainActivity.appLaunchable=false;
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin= (int) homeViewForAdapter.getX();
        lp.topMargin= (int) homeViewForAdapter.getY();
        LayoutInflater li=(LayoutInflater)ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll= (LinearLayout) li.inflate(R.layout.draweritem,null);

        if(icon==null){
            icon=new BitmapDrawable(ctx.getResources(),getCachedIcon());
        }


        ((ImageView)ll.findViewById(R.id.icon_image)).setImageDrawable(icon);
        ((TextView)ll.findViewById(R.id.icon_text)).setText(label);


        ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setOnTouchListener(new AppTouchListener());
                return false;
            }
        });


        ll.setTag(this);
        ll.setOnClickListener(new AppClickListner(ctx));
        homeViewForAdapter.addView(ll,0,lp);
    }
}
