package doublej.bobtudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MyBoBRoomActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;

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


    }
}
