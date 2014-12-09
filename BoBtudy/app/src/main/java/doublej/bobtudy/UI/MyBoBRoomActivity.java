package doublej.bobtudy.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import doublej.bobtudy.ListView.IconTextItemBoBroomMember;
import doublej.bobtudy.ListView.IconTextListAdapterBoBroomMember;
import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;

public class MyBoBRoomActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    private final String tag = "MyInfoActivity";

    MyDatabase myDB = new MyDatabase(this);
    final SQLiteDatabase db = myDB.getReadableDatabase();

    ListView list;
    IconTextListAdapterBoBroomMember adapter;

    TextView mybobroomTime, mybobroomPlace, mybobroomGoto, mybobroomComment;
    Button getOutBoBtudy, chattingRoom, voteSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_bobroom);

        mybobroomTime = (TextView) findViewById(R.id.mybobroomTime);
        mybobroomPlace = (TextView) findViewById(R.id.mybobroomPlace);
        mybobroomGoto = (TextView) findViewById(R.id.mybobroomGoto);
        mybobroomComment = (TextView) findViewById(R.id.mybobroomComment);

        getOutBoBtudy = (Button) findViewById(R.id.getOutBoBtudy);
        chattingRoom = (Button) findViewById(R.id.chattingRoom);
        voteSuggestion = (Button) findViewById(R.id.voteSuggestion);

        list = (ListView) findViewById(R.id.bobroomMemberList);

        adapter = new IconTextListAdapterBoBroomMember(this);

        Resources res = getResources();
        adapter.addItem(new IconTextItemBoBroomMember(res.getDrawable(R.drawable.member),"김태준", "아싸킹"));
        adapter.addItem(new IconTextItemBoBroomMember(res.getDrawable(R.drawable.member),"염은지", "염징ㅎㅎㅎㅎㅎ"));

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


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub
                IconTextItemBoBroomMember curItem = (IconTextItemBoBroomMember) adapter.getItem(position);
                String[] curData = curItem.getData();

            }
        });


    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
