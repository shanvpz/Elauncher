package in.techfantasy.elauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.List;

public class MainActivity extends Activity {
    Item[] items;
    PackageManager pm;
    GridView gv;
    DrawerAdapter Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm=getPackageManager();
        gv=findViewById(R.id.content);
        setItems();
        gv.setAdapter(new DrawerAdapter(this,items));
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
    }


}
