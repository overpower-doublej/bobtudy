package doublej.bobtudy;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class PreviousBoBtudyActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

    ListView list;
    IconTextListAdapterPreviousBoBroom adapter;

    Button backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.previousbobtudy_main);

        backToMain = (Button) findViewById(R.id.backToMain);

        list = (ListView) findViewById(R.id.previousBoBroomList);

        adapter = new IconTextListAdapterPreviousBoBroom(this);

        Resources res = getResources();
        adapter.addItem(new IconTextItemPreviousBoBroom(res.getDrawable(R.drawable.bobroom_image),"밥먹자 애드라", "오후 1시 돈부리집입니다."));
        adapter.addItem(new IconTextItemPreviousBoBroom(res.getDrawable(R.drawable.bobroom_image),"난 아싸가 아니야", "정문 칼리앤메리 12시"));

        list.setAdapter(adapter);


        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),
                        MyBoBRoomActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);

            }
        });

    }
}
