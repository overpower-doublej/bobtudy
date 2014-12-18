package doublej.bobtudy.UI.Chatting;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class ChattingActivity extends Activity {

    private final String tag = "ChattingActivity";

    static String Nick, post_id, ID;

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
        Nick = bundle.getString("nickName");
        ID = bundle.getString("user");
        post_id = bundle.getString("post_id");

        MyDatabase myDB = new MyDatabase(this);
        final SQLiteDatabase db = myDB.getWritableDatabase();


        String sql = "SELECT * FROM post_chat pc, user u WHERE u.id = pc.userId AND postId LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{post_id});

        int recordCount = cursor.getCount();
        Log.d(tag, "cursor count : " + recordCount + "\n");

        int nickNameCol = cursor.getColumnIndex("nickName");
        int dateCol = cursor.getColumnIndex("date");
        int msgCol = cursor.getColumnIndex("msg");

        Resources res = getResources();

        for (int i = 0; i < cursor.getCount(); i++) {
            while (cursor.moveToNext()) {
                String nickName = cursor.getString(nickNameCol);
                String date = cursor.getString(dateCol);
                String msg = cursor.getString(msgCol);

                ChattingContent.append(nickName+" ("+date+") : " +msg+"\r\n");

            }
        }

        cursor.close();




        ChattingInputOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long time = System.currentTimeMillis();
                SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
                String str = dayTime.format(new Date(time));

                /* postId에 값 입력 */
                db.execSQL("INSERT INTO post_chat VALUES ( '"+post_id+"','" + ID + "', '" + str + "', '" +
                        ChattingInputContent.getText().toString() + "' );");

                ChattingContent.append(Nick+" ("+str+") : " +ChattingInputContent.getText().toString()+"\r\n");
                ChattingInputContent.setText("");
            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.leftin, R.anim.leftout);
    }
}
