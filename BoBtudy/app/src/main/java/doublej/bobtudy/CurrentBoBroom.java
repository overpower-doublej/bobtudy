package doublej.bobtudy;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * Created by YeomJi on 2014. 12. 1..
 */
public class CurrentBoBroom extends Activity implements OnClickListener{

    public static final int REQUEST_CODE_ANOTHER = 1001;

    SimpleSideDrawer mNav;

    Button BoBtudyMenu;
    ImageView CreateRoomImage;

    TextView currentBoBtudy, BoBtudyHistory, myInfo, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.current_bobroom);


        currentBoBtudy = (TextView) findViewById(R.id.currentBoBtudy);
        BoBtudyHistory = (TextView) findViewById(R.id.BoBtudyHistory);
        myInfo = (TextView) findViewById(R.id.myInfo);
        logout = (TextView) findViewById(R.id.logout);


        BoBtudyMenu = (Button) findViewById(R.id.BoBtudyMenu);
        CreateRoomImage = (ImageView) findViewById(R.id.CreateRoomImage);

        currentBoBtudy.setOnClickListener(this);
        BoBtudyHistory.setOnClickListener(this);
        myInfo.setOnClickListener(this);
        logout.setOnClickListener(this);

        mNav = new SimpleSideDrawer(this);
        mNav.setLeftBehindContentView(R.layout.bobtudy_menu);


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

                //mNav.toggleRightDrawer();
                mNav.toggleLeftDrawer();


            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == currentBoBtudy) {


        } else if (v == BoBtudyHistory) {


        } else if (v == myInfo) {
            Intent intent = new Intent(getBaseContext(),
                    MyInfoActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ANOTHER);

        } else if (v == logout) {
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
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
