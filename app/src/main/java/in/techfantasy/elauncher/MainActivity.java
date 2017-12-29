package in.techfantasy.elauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;

import java.util.List;

public class MainActivity extends Activity {
    Item[] items;
    PackageManager pm;
    GridView gv;
    SlidingDrawer slidingdrawer;
    RelativeLayout homeview;


    static boolean appLaunchable=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm=getPackageManager();
        gv=findViewById(R.id.content);
        slidingdrawer =findViewById(R.id.sliding);
        homeview=findViewById(R.id.home_view_layout);
        setItems();
        slidingdrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                appLaunchable=true;
            }
        });

        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(new ItemReceiver(),filter);
    }
    public void setItems(){
        final Intent mainIntent=new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> itemList=pm.queryIntentActivities(mainIntent,0);
        items=new Item[itemList.size()];
        for(int i=0;i<itemList.size();i++){
            items[i]=new Item();
            items[i].icon=itemList.get(i).loadIcon(pm);
            items[i].name=itemList.get(i).activityInfo.packageName;
            items[i].label=itemList.get(i).loadLabel(pm).toString();
        }
        new SortApps().exchange_sort(items);
        gv.setAdapter(new DrawerAdapter(this,items));
        gv.setOnItemClickListener(new DrawerClickListner(MainActivity.this,items,pm));
        gv.setOnItemLongClickListener(new DrawerLongClickListner(MainActivity.this,slidingdrawer,homeview));

    }


}
