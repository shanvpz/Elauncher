package in.techfantasy.elauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by campusiq on 29/12/17.
 */

public class ItemReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new MainActivity().setItems();
    }
}
