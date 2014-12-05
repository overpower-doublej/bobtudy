package doublej.bobtudy;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class IconTextListAdapterPreviousBoBroom  extends BaseAdapter {

    private Context mContext;

    private List<IconTextItemBoBroom> mItems = new ArrayList<IconTextItemBoBroom>();

    public IconTextListAdapterPreviousBoBroom(Context context) {
        mContext = context;
    }

    public void addItem(IconTextItemBoBroom it) {
        mItems.add(it);
    }

    public void setListItems(List<IconTextItemBoBroom> lit) {
        mItems = lit;
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public boolean isSelectable(int position) {
        try {
            return mItems.get(position).isSelectable();
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        IconTextViewBoBroom itemView;
        if (convertView == null) {
            itemView = new IconTextViewBoBroom(mContext, mItems.get(position));
        } else {
            itemView = (IconTextViewBoBroom) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));
            itemView.setText(1, mItems.get(position).getData(1));
            itemView.setText(2, mItems.get(position).getData(2));
        }

        return itemView;
    }
}

