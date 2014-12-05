package doublej.bobtudy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import doublej.bobtudy.R;


public class CreateBoBroom extends Activity {

    Button createBoBroomOK, createBoBroomCancel;

    EditText BoBroomTitle, BoBroomPlace, BoBroomGoto, BoBroomComment;

    TimePicker bobroomTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_bobroom);

        createBoBroomOK = (Button) findViewById(R.id.createbobroomOK);
        createBoBroomCancel = (Button) findViewById(R.id.createbobroomCancel);

        BoBroomTitle = (EditText) findViewById(R.id.bobroomTitle);
        BoBroomPlace = (EditText) findViewById(R.id.bobroomPlace);
        BoBroomGoto = (EditText) findViewById(R.id.bobroomGoto);
        BoBroomComment = (EditText) findViewById(R.id.bobroomComment);

        bobroomTime = (TimePicker) findViewById(R.id.bobroomTime);

        createBoBroomOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        createBoBroomCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
