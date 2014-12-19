package doublej.bobtudy.UI.BoBRoomApplyVote;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import doublej.bobtudy.Control.BackPressCloseHandler;
import doublej.bobtudy.R;
import doublej.bobtudy.http.handler.ResponseHandler;
import doublej.bobtudy.http.post.PostIdHttp;

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

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ApplyVoteBoBtudyActivity.this);

        final String id = pref.getString("id", "");
        String pwd = pref.getString("pwd", "");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int nid = bundle.getInt("nid");
        final String userId = bundle.getString("userId");
        final String accessId = bundle.getString("accessId");
        final String postId = bundle.getString("postId");

        // notification 매니저 생성
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 등록된 notification 을 제거 한다.
        nm.cancel(nid);

        applyAccept = (Button) findViewById(R.id.applyAccept);
        applyReject = (Button) findViewById(R.id.applyReject);
        NewMemberNicName = (TextView) findViewById(R.id.NewMemberNicName);
        NewMemberParticipate = (TextView) findViewById(R.id.NewMemberParticipate);

        NewMemberNicName.setText(userId);

        applyAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostIdHttp.voteAccess(postId, accessId, id, true, new ResponseHandler() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        finish();
                    }
                });
            }
        });

        applyReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostIdHttp.voteAccess(postId, accessId, userId, false, new ResponseHandler() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        finish();
                    }
                });
            }
        });

    }
}
