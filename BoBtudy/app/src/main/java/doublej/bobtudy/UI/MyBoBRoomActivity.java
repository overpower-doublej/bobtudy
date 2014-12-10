package doublej.bobtudy.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import doublej.bobtudy.ListView.IconTextItemBoBroom;
import doublej.bobtudy.ListView.IconTextItemBoBroomMember;
import doublej.bobtudy.ListView.IconTextListAdapterBoBroomMember;
import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;

public class MyBoBRoomActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    private final String tag = "MyBoBRoomActivity";

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
        String bobRoomTitle = bundle.getString("title");

        MyDatabase myDB = new MyDatabase(this);
        SQLiteDatabase db = myDB.getReadableDatabase();

        list = (ListView) findViewById(R.id.bobroomMemberList);
        adapter = new IconTextListAdapterBoBroomMember(this);

        /* 수정 필요 */
        String sql = "SELECT * content FROM post WHERE title LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{bobRoomTitle});

        int idCol = cursor.getColumnIndex("id");
        int titleCol = cursor.getColumnIndex("title");
        int dateCol = cursor.getColumnIndex("date");
        int menuCol = cursor.getColumnIndex("menu");
        int placeCol = cursor.getColumnIndex("place");
        int contentCol = cursor.getColumnIndex("content");
        int bossCol = cursor.getColumnIndex("boss");

        cursor.moveToNext();
        String id = cursor.getString(idCol);
        String boss = cursor.getString(bossCol);
        cursor.moveToFirst();

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
        cursor.close();


        sql = "SELECT * content FROM post_user WHERE postId LIKE ?";
        cursor = db.rawQuery(sql, new String[]{id});

        int userIdCol = cursor.getColumnIndex("userId");
        int meetCol = cursor.getColumnIndex("meet");


        Resources res = getResources();

        for(int i=0;i<cursor.getCount();i++) {
            while (cursor.moveToNext()) {
                String userId = cursor.getString(userIdCol);
                String meet = cursor.getString(meetCol);

                adapter.addItem(new IconTextItemBoBroomMember(res.getDrawable(R.drawable.member),userId, meet));

            }
        }

        cursor.close();

        list.setAdapter(adapter);

        list.setAdapter(adapter);

        getOutBoBtudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        chattingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),
                        ChattingActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);

            }
        });

        voteSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),
                        VoteSuggestionActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);

            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
