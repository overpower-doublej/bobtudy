package doublej.bobtudy.UI.BoBtudyParticipationCheck;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import doublej.bobtudy.R;

/**
 * Created by YeomJi on 14. 12. 12..
 */
public class IconTextViewNewMember extends LinearLayout {

    /**
     * Icon
     */
    private ImageView mIcon;

    /**
     * TextView 01
     */
    private TextView mText;

    /**
     * TextView 02
     */
    private CheckBox mCheck;

    /**
     * TextView 03
     */

    public IconTextViewNewMember(Context context, IconTextItemNewMember aItem) {
        super(context);

        // Layout Inflation
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.participation_check_listitem, this, true);

        // Set Icon
        mIcon = (ImageView) findViewById(R.id.iconItemNewMember);
        mIcon.setImageDrawable(aItem.getIcon());

        // Set Text
        mText = (TextView) findViewById(R.id.dataItem01NewMember);
        mText.setText(aItem.getData(0));

        // Set Check
        mCheck = (CheckBox) findViewById(R.id.participationCheck);


    }

    /**
     * set Text
     *
     * @param index
     * @param data
     */
    public void setText(int index, String data) {
        if (index == 0) {
            mText.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * set Icon
     *
     * @param icon
     */
    public void setIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
    }
}
