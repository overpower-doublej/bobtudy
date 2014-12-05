package doublej.bobtudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class PreviousBoBtudyActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;


    Button backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.previousbobtudy_main);

        backToMain = (Button) findViewById(R.id.backToMain);


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
