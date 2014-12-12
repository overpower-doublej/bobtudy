package doublej.bobtudy.UI.MyInfo;

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
import android.widget.Toast;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;
import doublej.bobtudy.UI.ModifyInfo.ModifyInfoActivtiy;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class MyInfoActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    private final String tag = "MyInfoActivity";
    static String ID;


    Button myinfoModify, myinfoConfirm;
    TextView myNickName, MyParticipate, myinfoID, myinfoName, myinfoDept, myinfoStuID;

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID = bundle.getString("user");

        MyDatabase myDB = new MyDatabase(this);
        SQLiteDatabase db = myDB.getReadableDatabase();


        String sql = "SELECT * FROM myInfo";

        Cursor cursor = db.rawQuery(sql, null);

        int recordCount = cursor.getCount();
        Log.d(tag, "cursor count : " + recordCount + "\n");


        // getPost column index
        int nickNameCol = cursor.getColumnIndex("nickName");
        int meetCol = cursor.getColumnIndex("meet");
        int totalMeetCol = cursor.getColumnIndex("totalMeet");
        int idCol = cursor.getColumnIndex("id");
        int nameCol = cursor.getColumnIndex("name");
        int deptCol = cursor.getColumnIndex("dept");
        int stuIdCol = cursor.getColumnIndex("stuId");


            while (cursor.moveToNext()) {
                String nickName = cursor.getString(nickNameCol);
                int meet = cursor.getInt(meetCol);
                int totalMeet = cursor.getInt(totalMeetCol);
                String id = cursor.getString(idCol);
                String name = cursor.getString(nameCol);
                String dept = cursor.getString(deptCol);
                String stuId = cursor.getString(stuIdCol);

                String participate;
                if(totalMeet == 0) {
                    participate = "없음";
                } else {
                    participate = (meet / totalMeet)*100 + "%";
                }

                myNickName.setText(nickName);
                MyParticipate.setText(participate);
                myinfoID.setText(id);
                myinfoName.setText(name);
                myinfoDept.setText(dept);
                myinfoStuID.setText(stuId);

            }


        cursor.close();

        myinfoModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("user", ID);
                Intent intent = new Intent(getApplicationContext(), ModifyInfoActivtiy.class);
                intent.putExtras(bundle);
                startActivity(intent);

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
