package doublej.bobtudy.UI.BoBtudyParticipationCheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
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

/**
 * Created by YeomJi on 14. 12. 12..
 */
public class BoBtudyParticipationActivity extends Activity {

    ListView list;
    IconTextListAdapterParticipation adapter;

    static String title, post_id;

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
        post_id = bundle.getString("post_id");


        MyDatabase myDB = new MyDatabase(this);
        SQLiteDatabase db = myDB.getReadableDatabase();

        list = (ListView) findViewById(R.id.bobroomParticipationList);
        adapter = new IconTextListAdapterParticipation(this);

        String sql = "SELECT * FROM post_user ps, user u  WHERE ps.userId = u.id and ps.postId LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{post_id});

        int recordCount = cursor.getCount();
        Log.d(tag, "cursor count : " + recordCount + "\n");

        int nickNameCol = cursor.getColumnIndex("nickName");


        Resources res = getResources();

        for (int i = 0; i < cursor.getCount(); i++) {
            while (cursor.moveToNext()) {
                String nickName = cursor.getString(nickNameCol);

                adapter.addItem(new IconTextItemParticipation(res.getDrawable(R.drawable.member), nickName));

            }
        }

        cursor.close();
        myDB.close();

        list.setAdapter(adapter);


        BoBtudyDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.rightin, R.anim.rightout);

            }
        });

        BackToTheMyBoBRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
                overridePendingTransition(R.anim.leftin, R.anim.leftout);

            }

        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub
                IconTextItemParticipation curItem = (IconTextItemParticipation) adapter.getItem(position);

                Bundle bundle = new Bundle();
                bundle.putString("title", curItem.getData(0));
                bundle.putString("post_id", post_id);


                AlertDialog.Builder alert = new AlertDialog.Builder(
                        BoBtudyParticipationActivity.this);

                alert.setTitle("출석 체크");
                alert.setMessage("출석여부를 체크해 주세요.");


                alert.setPositiveButton("참석",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {


                            }
                        });

                alert.setNegativeButton("결석",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                            }
                        });

                alert.show();

            }
        });

    }


}
