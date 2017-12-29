package in.techfantasy.elauncher;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by campusiq on 29/12/17.
 */

class DrawerAdapter extends BaseAdapter{
    Context ctx;
    Item items[];

    public DrawerAdapter(Context ctx, Item[] items) {
        this.ctx = ctx;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView=new ImageView(ctx);
        imageView.setImageDrawable(items[position].icon);
        imageView.setLayoutParams(new GridView.LayoutParams(65,65));
        imageView.setPadding(3,3,3,3);
        return imageView;
    }
}
