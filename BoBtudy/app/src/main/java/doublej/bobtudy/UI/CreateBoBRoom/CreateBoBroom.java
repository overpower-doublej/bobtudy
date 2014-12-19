package doublej.bobtudy.UI.CreateBoBRoom;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.JsonWriter;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.Date;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;
import doublej.bobtudy.UI.MyBoBRoom.MyBoBRoomActivity;
import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.http.handler.PostHandler;
import doublej.bobtudy.http.post.NewPost;
import doublej.bobtudy.http.post.PostHttp;
import doublej.bobtudy.util.ISODate;


public class CreateBoBroom extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    static String userId, Time, AMPM;
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
        userId = bundle.getString("user");

        MyDatabase myDB = new MyDatabase(this);
        final SQLiteDatabase db = myDB.getWritableDatabase();


        bobroomTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                if (hourOfDay >= 13) {
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
                 * 출석률 값 입력 --> 수정
                 * 지금은 아래 쿼리문의 숫자 바꿔야댐*/

                /*db.execSQL("INSERT INTO post VALUES ( 6, '"+ BoBroomTitle.getText().toString() +"', '"+ Time+"', '"+
                        BoBroomGoto.getText().toString()+"', '"+ BoBroomPlace.getText().toString()+"', '"+
                        BoBroomComment.getText().toString()+"', '"+ userId +"' );");

                db.execSQL("INSERT INTO post_user VALUES ( 6, '"+ userId+"', '"+
                        "출석률 값 입력"+"' );");*/

                String title = BoBroomTitle.getText().toString();
                Date date = new Date();
                date.setHours(bobroomTime.getCurrentHour());
                date.setMinutes(bobroomTime.getCurrentMinute());
                ISODate isoDate = new ISODate(date);
                String menu = BoBroomGoto.getText().toString();
                String place = BoBroomPlace.getText().toString();
                String content = BoBroomComment.getText().toString();
                String bossId = userId;

                NewPost newPost = new NewPost(title, isoDate, menu, place, content, bossId);
                PostHttp.create(newPost, new PostHandler() {
                    @Override
                    public void onResponse(Post post) {
                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(CreateBoBroom.this);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("mypost", post.getId());
                        editor.commit();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("post", post);
                        bundle.putString("title", post.getTitle());
                        Intent intent = new Intent(getApplicationContext(), MyBoBRoomActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.rightin, R.anim.rightout);

                        finish();
                    }
                });
            }
        });

        createBoBroomCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.fade, R.anim.hold);

            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}
