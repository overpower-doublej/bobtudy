package doublej.bobtudy.UI.BoBRoomApply;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import doublej.bobtudy.R;

/**
 * Created by YeomJi on 14. 12. 12..
 */
public class ApplyBoBtudyActivitiy extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    static String ID;


    TextView ApplyforBoBtudy;
    Button applyYes, applyCancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.apply_message);

        applyYes = (Button) findViewById(R.id.applyYes);
        applyCancel = (Button) findViewById(R.id.applyCancel);
        ApplyforBoBtudy = (TextView) findViewById(R.id.ApplyforBoBtudy);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String bobRoomTitle = bundle.getString("title");
        ID = bundle.getString("user");

        ApplyforBoBtudy.setText(bobRoomTitle);

        /* 현재 밥룸 멤버들에게 알림이 감 */
        applyYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        applyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }
}
