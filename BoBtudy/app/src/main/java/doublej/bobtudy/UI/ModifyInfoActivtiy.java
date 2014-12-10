package doublej.bobtudy.UI;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class ModifyInfoActivtiy extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    EditText modifyinfoNickName, modifyinfoPWOriginal, modifyinfoPWNew, modifyinfoPWNew2;
    Button confirmModify, cancelModify;

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

        MyDatabase myDB = new MyDatabase(this);
        SQLiteDatabase db = myDB.getWritableDatabase();


        confirmModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* 쿼리
        db.execSQL("UPDATE INTO myInfo SET nickName = '"+ modifyinfoNickName.getText().toString()
                +"', pwd = '"+ modifyinfoPWNew.getText().toString()+"' WHERE id = '"+ +"' );");
*/

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
