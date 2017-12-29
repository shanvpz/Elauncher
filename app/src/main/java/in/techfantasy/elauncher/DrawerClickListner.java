package in.techfantasy.elauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by campusiq on 29/12/17.
 */

public class DrawerClickListner implements AdapterView.OnItemClickListener {
Context ctx;
Item[] packs;
PackageManager pm;

    public DrawerClickListner(Context ctx, Item[] packs, PackageManager pm) {
        this.ctx = ctx;
        this.packs = packs;
        this.pm = pm;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(MainActivity.appLaunchable) {
            Intent launch = new Intent(Intent.ACTION_MAIN);
            launch.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn=new ComponentName(packs[i].packageName,packs[i].name);
            launch.setComponent(cn);
            ctx.startActivity(launch);
        }
    }
}
