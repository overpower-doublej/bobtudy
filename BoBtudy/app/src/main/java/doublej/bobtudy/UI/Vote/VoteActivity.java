package doublej.bobtudy.UI.Vote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import doublej.bobtudy.R;

/**
 * Created by YeomJi on 14. 12. 12..
 */
public class VoteActivity  extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;


    TextView voteSuggestionTitleConfirm, voteSuggestionContentConfirm;
    Button voteResponseYes, voteResponseNo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vote_message);

        voteResponseYes = (Button) findViewById(R.id.voteResponseYes);
        voteResponseNo = (Button) findViewById(R.id.voteResponseNo);
        voteSuggestionTitleConfirm = (TextView) findViewById(R.id.voteSuggestionTitleConfirm);
        voteSuggestionContentConfirm = (TextView) findViewById(R.id.voteSuggestionContentConfirm);

        voteResponseYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.leftin, R.anim.leftout);

            }
        });

        voteResponseNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.leftin, R.anim.leftout);

            }
        });

    }
}