package doublej.bobtudy.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import doublej.bobtudy.R;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class ChattingActivity extends Activity {

    TextView ChattingContent;
    EditText ChattingInputContent;
    Button ChattingInputOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chatting_main);

        ChattingContent = (TextView) findViewById(R.id.ChattingContent);
        ChattingInputContent = (EditText) findViewById(R.id.ChattingInputContent);
        ChattingInputOK = (Button) findViewById(R.id.ChattingInputOK);

        ChattingInputOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
