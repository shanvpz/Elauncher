package in.techfantasy.elauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
        ViewHolder vh;
        LayoutInflater li=(LayoutInflater)ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            convertView=li.inflate(R.layout.draweritem,null);
            vh=new ViewHolder();
            vh.tv=convertView.findViewById(R.id.icon_text);
            vh.iv=convertView.findViewById(R.id.icon_image);
            convertView.setTag(vh);
        }
        else
            vh= (ViewHolder) convertView.getTag();
            vh.iv.setImageDrawable(items[position].icon);
            vh.tv.setText(items[position].label);

        return convertView;
    }
    static class ViewHolder{
        TextView tv;
        ImageView iv;
    }
}
