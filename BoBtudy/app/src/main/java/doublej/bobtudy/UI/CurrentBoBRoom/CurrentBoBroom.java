package doublej.bobtudy.UI.CurrentBoBRoom;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;
import doublej.bobtudy.Control.SimpleSideDrawer;
import doublej.bobtudy.UI.CreateBoBRoom.CreateBoBroom;
import doublej.bobtudy.UI.MyBoBRoom.MyBoBRoomActivity;
import doublej.bobtudy.UI.MyInfo.MyInfoActivity;
import doublej.bobtudy.UI.PreviousBoBRoom.PreviousBoBtudyActivity;

/**
 * Created by YeomJi on 2014. 12. 1..
 */
public class CurrentBoBroom extends Activity implements View.OnClickListener {

    public static final int REQUEST_CODE_ANOTHER = 1001;
    private final String tag = "CurrentBoBroom";


    ListView list;
    IconTextListAdapterBoBroom adapter;

    static String ID;

    SimpleSideDrawer mNav;

    Button BoBtudyMenu;
    ImageView CreateRoomImage;

    TextView BoBroomListAlign1, BoBroomListAlign2;
    TextView currentBoBtudy, BoBtudyHistory, myInfo, logout;
    TextView CurrentBoBRoomName, CurrentBoBRoomPlace, CurrentBoBRoomTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.current_bobroom);


        BoBtudyMenu = (Button) findViewById(R.id.BoBtudyMenu);
        CreateRoomImage = (ImageView) findViewById(R.id.CreateRoomImage);

        CurrentBoBRoomName = (TextView) findViewById(R.id.dataItem01BoBroom);
        CurrentBoBRoomPlace = (TextView) findViewById(R.id.dataItem02BoBroom);
        CurrentBoBRoomTime = (TextView) findViewById(R.id.dataItem03BoBroom);

        BoBroomListAlign1 = (TextView) findViewById(R.id.BoBroomListAlign1);
        BoBroomListAlign2 = (TextView) findViewById(R.id.BoBroomListAlign2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID = bundle.getString("user");


        MyDatabase myDB = new MyDatabase(this);
        SQLiteDatabase db = myDB.getReadableDatabase();

        list = (ListView) findViewById(R.id.bobroomList);
        adapter = new IconTextListAdapterBoBroom(this);

        String sql = "SELECT title, date, place  FROM post";
        Cursor cursor = db.rawQuery(sql, null);

        int recordCount = cursor.getCount();
        Log.d(tag, "cursor count : " + recordCount + "\n");

        int titleCol = cursor.getColumnIndex("title");
        int dateCol = cursor.getColumnIndex("date");
        int placeCol = cursor.getColumnIndex("place");

        Resources res = getResources();

        for (int i = 0; i < cursor.getCount(); i++) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(titleCol);
                String date = cursor.getString(dateCol);
                String place = cursor.getString(placeCol);

                adapter.addItem(new IconTextItemBoBroom(res.getDrawable(R.drawable.bobroom_image), title, date, place));

            }
        }

        cursor.close();

        list.setAdapter(adapter);


        mNav = new SimpleSideDrawer(this);
        mNav.setRightBehindContentView(R.layout.bobtudy_menu);


        currentBoBtudy = (TextView) findViewById(R.id.currentBoBtudy);
        BoBtudyHistory = (TextView) findViewById(R.id.BoBtudyHistory);
        myInfo = (TextView) findViewById(R.id.myInfo);
        logout = (TextView) findViewById(R.id.logout);

        currentBoBtudy.setOnClickListener(this);
        BoBtudyHistory.setOnClickListener(this);
        myInfo.setOnClickListener(this);
        logout.setOnClickListener(this);


        CreateRoomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("user", ID);
                Intent intent = new Intent(getApplicationContext(), CreateBoBroom.class);
                intent.putExtras(bundle);

                startActivity(intent);


            }
        });

        BoBtudyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNav.toggleRightDrawer();


            }
        });

        BoBroomListAlign1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        BoBroomListAlign2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub
                IconTextItemBoBroom curItem = (IconTextItemBoBroom) adapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putString("title", curItem.getData(0));
                bundle.putString("user", ID);

                Toast.makeText(CurrentBoBroom.this, ID,
                        Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getApplicationContext(), MyBoBRoomActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);


            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.currentBoBtudy) {

            Bundle bundle = new Bundle();
            bundle.putString("user", ID);
            Intent intent = new Intent(getApplicationContext(), MyBoBRoomActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);


        } else if (v.getId() == R.id.BoBtudyHistory) {

            Bundle bundle = new Bundle();
            bundle.putString("user", ID);
            Intent intent = new Intent(getApplicationContext(), PreviousBoBtudyActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (v.getId() == R.id.myInfo) {

            Bundle bundle = new Bundle();
            bundle.putString("user", ID);
            Intent intent = new Intent(getApplicationContext(), MyInfoActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (v.getId() == R.id.logout) {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if (!mNav.isClosed()) mNav.closeLeftSide();
        else finish();

    }
}
