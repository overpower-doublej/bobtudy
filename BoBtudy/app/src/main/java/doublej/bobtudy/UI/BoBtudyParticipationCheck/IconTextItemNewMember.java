package doublej.bobtudy.UI.BoBtudyParticipationCheck;

import android.graphics.drawable.Drawable;

/**
 * Created by YeomJi on 14. 12. 12..
 */
public class IconTextItemNewMember {

    /**
     * Icon
     */
    private Drawable mIcon;

    /**
     * Data array
     */
    private String[] mData;

    /**
     * IsChecked
     */
    private boolean mCheck;


    /**
     * True if this item is selectable
     */
    private boolean mSelectable = true;

    /**
     * Initialize with icon and data array
     *
     * @param icon
     * @param obj
     */
    public IconTextItemNewMember(Drawable icon, String[] obj) {
        mIcon = icon;
        mData = obj;
    }

    /**
     * Initialize with icon and strings
     *
     * @param icon
     * @param obj
     * @param check
     */
    public IconTextItemNewMember(Drawable icon, String obj, boolean check) {
        mIcon = icon;

        mData = new String[3];
        mData[0] = obj;
        mCheck = check;
    }

    /**
     * True if this item is selectable
     */
    public boolean isSelectable() {
        return mSelectable;
    }

    /**
     * Set selectable flag
     */
    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    /**
     * Get data array
     *
     * @return
     */
    public String[] getData() {
        return mData;
    }

    /**
     * Get data
     */
    public String getData(int index) {
        if (mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];
    }

    /**
     * Set data array
     *
     * @param obj
     */
    public void setData(String[] obj) {
        mData = obj;
    }

    /**
     * Set check
     *
     * @param check
     */
    public void setCheck(boolean check) {
        mCheck = check;
    }

    /**
     * Set icon
     *
     * @param icon
     */
    public void setIcon(Drawable icon) {
        mIcon = icon;
    }

    /**
     * Get icon
     *
     * @return
     */
    public Drawable getIcon() {
        return mIcon;
    }

    public boolean getCheck() {
        return mCheck;
    }

    /**
     * Compare with the input object
     *
     * @param other
     * @return
     */
    public int compareTo(IconTextItemNewMember other) {
        if (mData != null) {
            String[] otherData = other.getData();
            if (mData.length == otherData.length) {
                for (int i = 0; i < mData.length; i++) {
                    if (!mData[i].equals(otherData[i])) {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        } else {
            throw new IllegalArgumentException();
        }

        return 0;
    }
}