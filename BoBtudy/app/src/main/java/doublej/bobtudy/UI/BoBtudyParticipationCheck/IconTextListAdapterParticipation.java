package doublej.bobtudy.UI.BoBtudyParticipationCheck;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YeomJi on 14. 12. 18..
 */
public class IconTextListAdapterParticipation  extends BaseAdapter {

    private Context mContext;

    private List<IconTextItemParticipation> mItems = new ArrayList<IconTextItemParticipation>();

    public IconTextListAdapterParticipation(Context context) {
        mContext = context;
    }

    public void addItem(IconTextItemParticipation it) {
        mItems.add(it);
    }

    public void setListItems(List<IconTextItemParticipation> lit) {
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
        IconTextViewParticipation itemView;
        if (convertView == null) {
            itemView = new IconTextViewParticipation(mContext, mItems.get(position));
        } else {
            itemView = (IconTextViewParticipation) convertView;

            itemView.setIcon(mItems.get(position).getIcon());
            itemView.setText(0, mItems.get(position).getData(0));
        }

        return itemView;
    }

}
