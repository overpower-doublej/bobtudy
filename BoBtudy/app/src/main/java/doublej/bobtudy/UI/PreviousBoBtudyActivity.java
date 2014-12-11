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

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.ListView.IconTextItemBoBroom;
import doublej.bobtudy.ListView.IconTextItemBoBroomMember;
import doublej.bobtudy.ListView.IconTextItemPreviousBoBroom;
import doublej.bobtudy.ListView.IconTextListAdapterPreviousBoBroom;
import doublej.bobtudy.R;
import doublej.bobtudy.UI.CurrentBoBroom;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class PreviousBoBtudyActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    private final String tag = "PreviousBoBtudyActivity";


    static String ID;

    ListView list;
    IconTextListAdapterPreviousBoBroom adapter;

    Button backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.previousbobtudy_main);

        backToMain = (Button) findViewById(R.id.backToMain);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID = bundle.getString("user");

        MyDatabase myDB = new MyDatabase(this);
        SQLiteDatabase db = myDB.getReadableDatabase();


        list = (ListView) findViewById(R.id.previousBoBroomList);
        adapter = new IconTextListAdapterPreviousBoBroom(this);

        String sql = "SELECT * FROM post_user ps, post p WHERE ps.postId = p.id AND ps.postId LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{ID});

        int recordCount = cursor.getCount();
        Log.d(tag, "cursor count : " + recordCount + "\n");

        int titleCol = cursor.getColumnIndex("title");
        int dateCol = cursor.getColumnIndex("date");
        int placeCol = cursor.getColumnIndex("place");


        Resources res = getResources();

        for(int i=0;i<cursor.getCount();i++) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(titleCol);
                String date = cursor.getString(dateCol);
                String place = cursor.getString(placeCol);

                adapter.addItem(new IconTextItemPreviousBoBroom(res.getDrawable(R.drawable.bobroom_image), title, date, place));

            }
        }

        cursor.close();
        myDB.close();


        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub
                IconTextItemPreviousBoBroom curItem = (IconTextItemPreviousBoBroom) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("title", curItem.getData(0));
                bundle.putString("user", ID);

                Intent intent = new Intent(getApplicationContext(), MyBoBRoomActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
