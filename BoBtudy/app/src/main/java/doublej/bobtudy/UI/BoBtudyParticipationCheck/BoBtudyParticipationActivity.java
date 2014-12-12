package doublej.bobtudy.UI.BoBtudyParticipationCheck;

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
import android.widget.EditText;
import android.widget.ListView;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;
import doublej.bobtudy.UI.CurrentBoBRoom.IconTextItemBoBroom;
import doublej.bobtudy.UI.CurrentBoBRoom.IconTextListAdapterBoBroom;
import doublej.bobtudy.UI.MyBoBRoom.MyBoBRoomActivity;

/**
 * Created by YeomJi on 14. 12. 12..
 */
public class BoBtudyParticipationActivity extends Activity {

    ListView list;
    IconTextListAdapterNewMember adapter;
    static String title;

    public static final int REQUEST_CODE_ANOTHER = 1001;

    private final String tag = "BoBtudyParticipationActivity";



    Button BoBtudyDone, BackToTheMyBoBRoom;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.participation_check);

        BoBtudyDone = (Button) findViewById(R.id.BoBtudyDone);
        BackToTheMyBoBRoom = (Button) findViewById(R.id.BackToTheMyBoBRoom);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        title = bundle.getString("title");

        MyDatabase myDB = new MyDatabase(this);
        SQLiteDatabase db = myDB.getReadableDatabase();


        list = (ListView) findViewById(R.id.bobroomList);
        adapter = new IconTextListAdapterNewMember(this);


        String sql = "SELECT nickName  FROM post_user ps, user u WHERE ps.userId = u.id and ps.postId =?";
        Cursor cursor = db.rawQuery(sql, new String[]{title});

        int recordCount = cursor.getCount();
        Log.d(tag, "cursor count : " + recordCount + "\n");

        int nickNameCol = cursor.getColumnIndex("nickName");


        Resources res = getResources();

        for (int i = 0; i < cursor.getCount(); i++) {
            while (cursor.moveToNext()) {
                String nickName = cursor.getString(nickNameCol);

                adapter.addItem(new IconTextItemNewMember(res.getDrawable(R.drawable.member), nickName, false));

            }
        }

        cursor.close();

        list.setAdapter(adapter);



        BoBtudyDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        BackToTheMyBoBRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }

        });list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub



            }
        });



    }
}
