package doublej.bobtudy;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * Created by YeomJi on 2014. 12. 1..
 */
public class CurrentBoBroom extends Activity implements View.OnClickListener {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    ListView list;
    IconTextListAdapterBoBroom adapter;

    SimpleSideDrawer mNav;

    Button BoBtudyMenu;
    ImageView CreateRoomImage;

    TextView BoBroomListAlign1, BoBroomListAlign2;
    TextView currentBoBtudy, BoBtudyHistory, myInfo, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.current_bobroom);


        BoBtudyMenu = (Button) findViewById(R.id.BoBtudyMenu);
        CreateRoomImage = (ImageView) findViewById(R.id.CreateRoomImage);

        BoBroomListAlign1 = (TextView) findViewById(R.id.BoBroomListAlign1);
        BoBroomListAlign2 = (TextView) findViewById(R.id.BoBroomListAlign2);

        list = (ListView) findViewById(R.id.bobroomList);

        adapter = new IconTextListAdapterBoBroom(this);

        Resources res = getResources();
        adapter.addItem(new IconTextItemBoBroom(res.getDrawable(R.drawable.bobroom_image),"밥먹자 애드라", "오후 1시 돈부리집입니다."));
        adapter.addItem(new IconTextItemBoBroom(res.getDrawable(R.drawable.bobroom_image),"난 아싸가 아니야", "정문 칼리앤메리 12시"));

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

                Intent intent = new Intent(getBaseContext(),
                        CreateBoBroom.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);

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


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.currentBoBtudy) {
            Intent intent = new Intent(getBaseContext(),
                    MyBoBRoomActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ANOTHER);

        } else if (v.getId() == R.id.BoBtudyHistory) {

            Intent intent = new Intent(getBaseContext(),
                    PreviousBoBtudyActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ANOTHER);


        } else if (v.getId() == R.id.myInfo) {
            Intent intent = new Intent(getBaseContext(),
                    MyInfoActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ANOTHER);

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
