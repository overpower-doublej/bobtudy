package doublej.bobtudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class VoteSuggestionActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;


    EditText voteSuggestionTitle, voteSuggestionContent;
    Button voteSuggestionOK, voteSuggestionCancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vote_suggestion);

        voteSuggestionOK = (Button) findViewById(R.id.voteSuggestionOK);
        voteSuggestionCancel = (Button) findViewById(R.id.voteSuggestionCancel);
        voteSuggestionTitle = (EditText) findViewById(R.id.voteSuggestionTitle);
        voteSuggestionContent = (EditText) findViewById(R.id.voteSuggestionContent);

        voteSuggestionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        voteSuggestionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }
}
