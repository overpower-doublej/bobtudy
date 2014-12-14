package doublej.bobtudy.UI.Chatting;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class ChattingActivity extends Activity {

    static String ID;

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID = bundle.getString("user");

        MyDatabase myDB = new MyDatabase(this);
        final SQLiteDatabase db = myDB.getWritableDatabase();

        ChattingInputOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long time = System.currentTimeMillis();
                SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
                String str = dayTime.format(new Date(time));

                /* postId에 값 입력 */
                db.execSQL("INSERT INTO post_chat VALUES ( 'postId','" + ID + "', '" + str + "', '" +
                        ChattingInputContent.getText().toString() + "' );");
            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
