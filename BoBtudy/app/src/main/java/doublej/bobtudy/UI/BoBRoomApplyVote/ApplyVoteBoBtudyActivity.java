package doublej.bobtudy.UI.BoBRoomApplyVote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import doublej.bobtudy.R;

/**
 * Created by YeomJi on 14. 12. 12..
 */
public class ApplyVoteBoBtudyActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;


    TextView NewMemberNicName, NewMemberParticipate;
    Button applyAccept, applyReject;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.apply_vote_message);

        applyAccept = (Button) findViewById(R.id.applyAccept);
        applyReject = (Button) findViewById(R.id.applyReject);
        NewMemberNicName = (TextView) findViewById(R.id.NewMemberNicName);
        NewMemberParticipate = (TextView) findViewById(R.id.NewMemberParticipate);

        applyAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        applyReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }
}
