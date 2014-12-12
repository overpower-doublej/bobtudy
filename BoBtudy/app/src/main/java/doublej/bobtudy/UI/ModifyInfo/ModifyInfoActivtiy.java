package doublej.bobtudy.UI.ModifyInfo;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class ModifyInfoActivtiy extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    EditText modifyinfoNickName, modifyinfoPWOriginal, modifyinfoPWNew, modifyinfoPWNew2;
    Button confirmModify, cancelModify;

    static String ID;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.modifyinfo_main);

        confirmModify = (Button) findViewById(R.id.confirmModify);
        cancelModify = (Button) findViewById(R.id.cancelModify);
        modifyinfoNickName = (EditText) findViewById(R.id.modifyinfoNickName);
        modifyinfoPWOriginal = (EditText) findViewById(R.id.modifyinfoPWOriginal);
        modifyinfoPWNew = (EditText) findViewById(R.id.modifyinfoPWNew);
        modifyinfoPWNew2 = (EditText) findViewById(R.id.modifyinfoPWNew2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID = bundle.getString("user");


        confirmModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDatabase myDB = new MyDatabase(ModifyInfoActivtiy.this);
                SQLiteDatabase db = myDB.getWritableDatabase();

                db.execSQL("UPDATE myInfo SET nickName ="+modifyinfoNickName.getText().toString()+
                        ", pwd="+modifyinfoPWNew.getText().toString()+" WHERE id = "+ID+";");
                db.execSQL("UPDATE user SET nickName ="+modifyinfoNickName.getText().toString()+" WHERE id = "+ID+";");

                finish();

            }
        });

        cancelModify.setOnClickListener(new View.OnClickListener() {
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
