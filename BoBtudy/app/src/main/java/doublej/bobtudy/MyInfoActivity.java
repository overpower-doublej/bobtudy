package doublej.bobtudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class MyInfoActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;


    Button myinfoModify, myinfoConfirm;
    TextView myNickName, MyParticipate, myinfoID, myinfoName, myinfoDept, myinfoStuID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_main);

        myinfoModify = (Button) findViewById(R.id.myinfoModify);
        myinfoConfirm = (Button) findViewById(R.id.myinfoConfirm);
        myNickName = (TextView) findViewById(R.id.myNickName);
        MyParticipate = (TextView) findViewById(R.id.MyParticipate);
        myinfoID = (TextView) findViewById(R.id.myinfoID);
        myinfoName = (TextView) findViewById(R.id.myinfoName);
        myinfoDept = (TextView) findViewById(R.id.myinfoDept);
        myinfoStuID = (TextView) findViewById(R.id.myinfoStuID);


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
}
