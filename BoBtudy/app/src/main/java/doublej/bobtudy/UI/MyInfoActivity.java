package doublej.bobtudy.UI;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class MyInfoActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    private final String tag = "MyInfoActivity";


    Button myinfoModify, myinfoConfirm;
    TextView myNickName, MyParticipate, myinfoID, myinfoName, myinfoDept, myinfoStuID, myinfoComment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.myinfo_main);

        myinfoModify = (Button) findViewById(R.id.myinfoModify);
        myinfoConfirm = (Button) findViewById(R.id.myinfoConfirm);
        myNickName = (TextView) findViewById(R.id.myNickName);
        MyParticipate = (TextView) findViewById(R.id.MyParticipate);
        myinfoID = (TextView) findViewById(R.id.myinfoID);
        myinfoName = (TextView) findViewById(R.id.myinfoName);
        myinfoDept = (TextView) findViewById(R.id.myinfoDept);
        myinfoStuID = (TextView) findViewById(R.id.myinfoStuID);
        myinfoComment = (TextView) findViewById(R.id.myinfoComment);

        MyDatabase myDB = new MyDatabase(this);
        final SQLiteDatabase db = myDB.getReadableDatabase();


        String sql = "SELECT * FROM myInfo";

        Cursor cursor = db.rawQuery(sql, null);

        int recordCount = cursor.getCount();
        Log.d(tag, "cursor count : " + recordCount + "\n");


        // get column index
        int nickNameCol = cursor.getColumnIndex("nickName");
        int meetCol = cursor.getColumnIndex("meet");
        int totalMeetCol = cursor.getColumnIndex("totalMeet");
        int idCol = cursor.getColumnIndex("id");
        int nameCol = cursor.getColumnIndex("name");
        int deptCol = cursor.getColumnIndex("dept");
        int stuIdCol = cursor.getColumnIndex("stuId");
        int infoCol = cursor.getColumnIndex("info");


        while (cursor.moveToNext()) {

            String nickName = cursor.getString(nickNameCol);
            int meet = cursor.getInt(meetCol);
            int totalMeet = cursor.getInt(totalMeetCol);
            String id = cursor.getString(idCol);
            String name = cursor.getString(nameCol);
            String dept = cursor.getString(deptCol);
            String stuId = cursor.getString(stuIdCol);
            String info = cursor.getString(infoCol);

            String participate = (meet / totalMeet) + "%";

            myNickName.setText(nickName);
            MyParticipate.setText(participate);
            myinfoID.setText(id);
            myinfoName.setText(name);
            myinfoDept.setText(dept);
            myinfoStuID.setText(stuId);
            myinfoComment.setText(info);

        }


        cursor.close();

        myinfoModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),
                        ModifyInfoActivtiy.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);
                finish();

            }
        });

        myinfoConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
