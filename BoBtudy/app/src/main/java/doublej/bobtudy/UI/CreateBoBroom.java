package doublej.bobtudy.UI;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;


public class CreateBoBroom extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;


    Button createBoBroomOK, createBoBroomCancel;

    EditText BoBroomTitle, BoBroomPlace, BoBroomGoto, BoBroomComment;

    TimePicker bobroomTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_bobroom);

        createBoBroomOK = (Button) findViewById(R.id.createbobroomOK);
        createBoBroomCancel = (Button) findViewById(R.id.createbobroomCancel);

        BoBroomTitle = (EditText) findViewById(R.id.bobroomTitle);
        BoBroomPlace = (EditText) findViewById(R.id.bobroomPlace);
        BoBroomGoto = (EditText) findViewById(R.id.bobroomGoto);
        BoBroomComment = (EditText) findViewById(R.id.bobroomComment);

        bobroomTime = (TimePicker) findViewById(R.id.bobroomTime);

        MyDatabase myDB = new MyDatabase(this);
        final SQLiteDatabase db = myDB.getWritableDatabase();

        createBoBroomOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                db.execSQL("INSERT INTO post VALUES ( null, '"+ BoBroomTitle.getText().toString() +"', '"+ bobroomTime.getText().toString()+"', '"+
                        BoBroomGoto.getText().toString()+"', '"+ BoBroomPlace.getText().toString()+"', '"+
                        BoBroomComment.getText().toString()+"', '"+ editStuId.getText().toString() +"' );");
*/
                Intent intent = new Intent(getBaseContext(),
                        MyBoBRoomActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);

                finish();

            }
        });

        createBoBroomCancel.setOnClickListener(new View.OnClickListener() {
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
