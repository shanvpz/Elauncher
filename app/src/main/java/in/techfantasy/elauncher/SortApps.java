package in.techfantasy.elauncher;

/**
 * Created by campusiq on 29/12/17.
 */

public class SortApps {
    public void exchange_sort(Item[] items){
        int i,j;
        Item temp;
        for(i=0;i<items.length;i++)
        {
            for(j=i+1;j<items.length;j++)
            {
                if(items[i].label.compareToIgnoreCase(items[j].label)>0)
                {
                    temp=items[i];
                    items[i]=items[j];
                    items[j]=temp;
                }
            }

        }
    }
}
