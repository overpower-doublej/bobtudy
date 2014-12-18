package doublej.bobtudy.UI.MyBoBRoom;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;
import doublej.bobtudy.UI.BoBtudyParticipationCheck.BoBtudyParticipationActivity;
import doublej.bobtudy.UI.Chatting.ChattingActivity;
import doublej.bobtudy.UI.CurrentBoBRoom.CurrentBoBroom;
import doublej.bobtudy.UI.VoteSuggestion.VoteSuggestionActivity;

public class MyBoBRoomActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    private final String tag = "MyBoBRoomActivity";


    static String ID, post_id, title;


    ListView list;
    IconTextListAdapterBoBroomMember adapter;

    TextView mybobroomTitle, mybobroomTime, mybobroomPlace, mybobroomGoto, mybobroomComment;
    Button getOutBoBtudy, chattingRoom, voteSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_bobroom);

        mybobroomTitle = (TextView) findViewById(R.id.mybobroomTitle);
        mybobroomTime = (TextView) findViewById(R.id.mybobroomTime);
        mybobroomPlace = (TextView) findViewById(R.id.mybobroomPlace);
        mybobroomGoto = (TextView) findViewById(R.id.mybobroomGoto);
        mybobroomComment = (TextView) findViewById(R.id.mybobroomComment);

        getOutBoBtudy = (Button) findViewById(R.id.getOutBoBtudy);
        chattingRoom = (Button) findViewById(R.id.chattingRoom);
        voteSuggestion = (Button) findViewById(R.id.voteSuggestion);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        title = bundle.getString("title");
        ID = bundle.getString("user");

        MyDatabase myDB = new MyDatabase(this);
        SQLiteDatabase db = myDB.getReadableDatabase();

        list = (ListView) findViewById(R.id.bobroomMemberList);
        adapter = new IconTextListAdapterBoBroomMember(this);

        String sql = "SELECT * FROM post WHERE title LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{title});

        int idCol = cursor.getColumnIndex("id");
        int titleCol = cursor.getColumnIndex("title");
        int dateCol = cursor.getColumnIndex("date");
        int menuCol = cursor.getColumnIndex("menu");
        int placeCol = cursor.getColumnIndex("place");
        int contentCol = cursor.getColumnIndex("content");
        int bossCol = cursor.getColumnIndex("boss");

        while (cursor.moveToNext()) {

            String title = cursor.getString(titleCol);
            String date = cursor.getString(dateCol);
            String menu = cursor.getString(menuCol);
            String place = cursor.getString(placeCol);
            String content = cursor.getString(contentCol);

            mybobroomTitle.setText(title);
            mybobroomTime.setText(date);
            mybobroomPlace.setText(menu);
            mybobroomGoto.setText(place);
            mybobroomComment.setText(content);
        }
        cursor.moveToFirst();

        post_id = cursor.getString(idCol);
        String boss = cursor.getString(bossCol);

        cursor.close();


        sql = "SELECT * FROM post_user ps, user u  WHERE ps.userId = u.id and ps.postId LIKE ?";
        cursor = db.rawQuery(sql, new String[]{post_id});

        int recordCount = cursor.getCount();
        Log.d(tag, "cursor count : " + recordCount + "\n");

        int nickNameCol = cursor.getColumnIndex("nickName");

        Resources res = getResources();

        for(int i=0;i<cursor.getCount();i++) {
            while (cursor.moveToNext()) {
                String nickName = cursor.getString(nickNameCol);

                /* 출석률 값 입력 --> 수정*/
                if(ID.toString().equals(boss)) {
                    adapter.addItem(new IconTextItemBoBroomMember(res.getDrawable(R.drawable.member),nickName +" (방장)", "출석률 값 입력"));
                } else {
                    adapter.addItem(new IconTextItemBoBroomMember(res.getDrawable(R.drawable.member),nickName, "출석률 값 입력"));
                }


            }
        }

        cursor.close();
        myDB.close();

        list.setAdapter(adapter);


        getOutBoBtudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabase myDB = new MyDatabase(MyBoBRoomActivity.this);
                SQLiteDatabase db = myDB.getReadableDatabase();

                db.execSQL("DELETE FROM post_user WHERE postId = "+post_id+" and userId = '"+ ID +"';");

                myDB.close();

                finish();
                overridePendingTransition(R.anim.leftin, R.anim.leftout);

            }
        });

        chattingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("user", ID);
                Intent intent = new Intent(getApplicationContext(), ChattingActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.rightout);

            }
        });

        voteSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),
                        VoteSuggestionActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);
                overridePendingTransition(R.anim.rightin, R.anim.rightout);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 0, "출석체크");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                //처리할 이벤트
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("post_id", post_id);

                Intent intent = new Intent(getApplicationContext(), BoBtudyParticipationActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin, R.anim.rightout);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.leftin, R.anim.leftout);
    }
}
