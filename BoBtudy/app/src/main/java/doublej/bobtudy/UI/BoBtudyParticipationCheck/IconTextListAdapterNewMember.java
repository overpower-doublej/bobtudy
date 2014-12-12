package doublej.bobtudy.UI.BoBtudyParticipationCheck;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YeomJi on 14. 12. 12..
 */
public class IconTextListAdapterNewMember extends BaseAdapter {

    private Context mContext;

    private List<IconTextItemNewMember> mItems = new ArrayList<IconTextItemNewMember>();

    public IconTextListAdapterNewMember(Context context) {
        mContext = context;
    }

    public void addItem(IconTextItemNewMember it) {
        mItems.add(it);
    }

    public void setListItems(List<IconTextItemNewMember> lit) {
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
        IconTextViewNewMember itemView;
        if (convertView == null) {
            itemView = new IconTextViewNewMember(mContext, mItems.get(position));
        } else {
            itemView = (IconTextViewNewMember) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));

        }

        return itemView;
    }
}
