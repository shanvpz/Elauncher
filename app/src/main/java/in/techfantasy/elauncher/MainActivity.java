package in.techfantasy.elauncher;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    Item[] items;
    PackageManager pm;
    GridView gv;
    SlidingDrawer slidingdrawer;
    RelativeLayout homeview;
    AppWidgetManager mAppWidgetManager;
    LauncherAppWidgetHost mAppWidgetHost;
    int REQUEST_CREATE_APPWIDGET=900;
    int REQUEST_CREATE_SHORTCUT=700;
    int numWidgets;


    static boolean appLaunchable=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppWidgetManager = AppWidgetManager.getInstance(this);
        mAppWidgetHost = new LauncherAppWidgetHost(this, R.id.APPWIDGET_HOST_ID);
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

        homeview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder b=new AlertDialog.Builder(MainActivity.this);
                String[] items ={getResources().getString(R.string.widget),getResources().getString(R.string.shortcut)};
                b.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                selectWidget();
                                break;

                            case 1:
                                selectShortcut();
                                break;
                        }

                    }
                });
                AlertDialog d=b.create();
                d.show();

                return false;
            }
        });


        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(new ItemReceiver(),filter);
    }
    void selectWidget() {
        int appWidgetId = this.mAppWidgetHost.allocateAppWidgetId();
        Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        addEmptyData(pickIntent);
        startActivityForResult(pickIntent, R.id.REQUEST_PICK_APPWIDGET);
    }
    void addEmptyData(Intent pickIntent) {
        ArrayList customInfo = new ArrayList();
        pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo);
        ArrayList customExtras = new ArrayList();
        pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS, customExtras);
    };

    void selectShortcut(){
        Intent intent=new Intent(Intent.ACTION_PICK_ACTIVITY);
        intent.putExtra(Intent.EXTRA_INTENT,new Intent(Intent.ACTION_CREATE_SHORTCUT));
        startActivityForResult(intent,R.id.REQUEST_PICK_SHORTCUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK ) {
            if (requestCode == R.id.REQUEST_PICK_APPWIDGET) {
                configureWidget(data);
            }
            else if (requestCode == REQUEST_CREATE_APPWIDGET) {
                createWidget(data);
            }
            else if(requestCode == R.id.REQUEST_PICK_SHORTCUT){
                configureShortcut(data);
            }
            else if(requestCode == REQUEST_CREATE_SHORTCUT){
                createShortcut(data);
            }
        }
        else if (resultCode == RESULT_CANCELED && data != null) {
            int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            if (appWidgetId != -1) {
                mAppWidgetHost.deleteAppWidgetId(appWidgetId);
            }
        }
    }
    public void createShortcut(Intent intent){
        Intent.ShortcutIconResource iconResource = intent.getParcelableExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE);
        Bitmap icon                              = intent.getParcelableExtra(Intent.EXTRA_SHORTCUT_ICON);
        String shortcutLabel                     = intent.getStringExtra(Intent.EXTRA_SHORTCUT_NAME);
        Intent shortIntent                       = intent.getParcelableExtra(Intent.EXTRA_SHORTCUT_INTENT);

        if (icon==null){
            if (iconResource!=null){
                Resources resources =null;
                try {
                    resources = pm.getResourcesForApplication(iconResource.packageName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (resources != null) {
                    int id = resources.getIdentifier(iconResource.resourceName, null, null);
                    if(resources.getDrawable(id) instanceof StateListDrawable) {
                        Drawable d = ((StateListDrawable)resources.getDrawable(id)).getCurrent();
                        icon = ((BitmapDrawable)d).getBitmap();
                    }else
                        icon = ((BitmapDrawable)resources.getDrawable(id)).getBitmap();
                }
            }
        }


        if (shortcutLabel!=null && shortIntent!=null && icon!=null){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 100;
            lp.topMargin = (int) 100;

            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout ll = (LinearLayout) li.inflate(R.layout.draweritem, null);

            ((ImageView)ll.findViewById(R.id.icon_image)).setImageBitmap(icon);
            ((TextView)ll.findViewById(R.id.icon_text)).setText(shortcutLabel);

            ll.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    v.setOnTouchListener(new AppTouchListener());
                    return false;
                }
            });

            ll.setTag(shortIntent);
            homeview.addView(ll, lp);
        }

    }
    void configureShortcut(Intent data){
        startActivityForResult(data,REQUEST_CREATE_SHORTCUT);
    }
    private void configureWidget(Intent data) {
        Bundle extras = data.getExtras();
        int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
        if (appWidgetInfo.configure != null) {
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
            intent.setComponent(appWidgetInfo.configure);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivityForResult(intent, REQUEST_CREATE_APPWIDGET);
        } else {
            createWidget(data);
        }
    }
    public void createWidget(Intent data) {
        Bundle extras = data.getExtras();
        int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
        AppWidgetHostView hostView = mAppWidgetHost.createView(this, appWidgetId, appWidgetInfo);
        hostView.setAppWidget(appWidgetId, appWidgetInfo);
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(homeview.getWidth()/3,homeview.getHeight()/3);
        lp.leftMargin=numWidgets*(homeview.getWidth()/3);
        hostView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.RED);
            }
        });
        homeview.addView(hostView,lp);
        slidingdrawer.bringToFront();
        numWidgets++;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAppWidgetHost.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAppWidgetHost.stopListening();
    }
    public void setItems(){
        final Intent mainIntent=new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> itemList=pm.queryIntentActivities(mainIntent,0);
        items=new Item[itemList.size()];
        for(int i=0;i<itemList.size();i++){
            items[i]=new Item();
            items[i].icon=itemList.get(i).loadIcon(pm);
            items[i].packageName=itemList.get(i).activityInfo.packageName;
            items[i].name=itemList.get(i).activityInfo.name;
            items[i].label=itemList.get(i).loadLabel(pm).toString();
        }
        new SortApps().exchange_sort(items);
        gv.setAdapter(new DrawerAdapter(this,items));
        gv.setOnItemClickListener(new DrawerClickListner(MainActivity.this,items,pm));
        gv.setOnItemLongClickListener(new DrawerLongClickListner(MainActivity.this,slidingdrawer,homeview,items));

    }


}
