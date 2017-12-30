package in.techfantasy.elauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by campusiq on 30/12/17.
 */

public class ShortcutClickListener implements View.OnClickListener {
    Context ctx;
    Item[] packs;

    public ShortcutClickListener(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {
        Intent data;
        data= (Intent) view.getTag();
        //Intent launch = new Intent(Intent.ACTION_MAIN);
        ctx.startActivity(data);
    }
}
