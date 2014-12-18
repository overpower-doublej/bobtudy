package doublej.bobtudy.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import doublej.bobtudy.R;

/**
 * Created by YeomJi on 14. 12. 19..
 */
public class BoBRoomApplyReject extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;


    TextView ApplyforBoBtudy;
    Button applyRejectOK;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.apply_reject_message);

        applyRejectOK = (Button) findViewById(R.id.applyRejectOK);
        ApplyforBoBtudy = (TextView) findViewById(R.id.ApplyforBoBtudy);


        applyRejectOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }
}
