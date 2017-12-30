package in.techfantasy.elauncher;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by campusiq on 29/12/17.
 */

public class AppClickListner implements View.OnClickListener {
    Context ctx;
    //Item[] packs;

    public AppClickListner(Context ctx) {
        this.ctx = ctx;
        //this.packs = packs;
    }

    @Override
    public void onClick(View view) {
        Item data;
        data= (Item) view.getTag();
        Intent launch = new Intent(Intent.ACTION_MAIN);
        launch.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn=new ComponentName(data.packageName,data.name);
        launch.setComponent(cn);
        ctx.startActivity(launch);
    }
}
