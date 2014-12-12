package doublej.bobtudy.UI.CreateBoBRoom;

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
import doublej.bobtudy.UI.MyBoBRoom.MyBoBRoomActivity;


public class CreateBoBroom extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    static String ID, Time, AMPM;
    static int hour, min;

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID = bundle.getString("user");

        MyDatabase myDB = new MyDatabase(this);
        final SQLiteDatabase db = myDB.getWritableDatabase();


        bobroomTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                if(hourOfDay >= 13) {
                    hour = hourOfDay - 12;
                    AMPM = "PM ";
                } else {
                    hour = hourOfDay;
                    AMPM = "AM ";

                }
                min = minute;
                Time = AMPM + Integer.toString(hour) + ":" + Integer.toString(min);

            }
        });

        createBoBroomOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /* 임시 쿼리
                 * post.id = null --> 수정
                 * 출석률 값 입력 --> 수정*/

                db.execSQL("INSERT INTO post VALUES ( 3, '"+ BoBroomTitle.getText().toString() +"', '"+ Time+"', '"+
                        BoBroomGoto.getText().toString()+"', '"+ BoBroomPlace.getText().toString()+"', '"+
                        BoBroomComment.getText().toString()+"', '"+ ID +"' );");

                db.execSQL("INSERT INTO post_user VALUES ( '"+ BoBroomTitle.getText().toString() +"', '"+ ID+"', '"+
                        "출석률 값 입력"+"' );");


                Bundle bundle = new Bundle();
                bundle.putString("title", BoBroomTitle.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MyBoBRoomActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

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
