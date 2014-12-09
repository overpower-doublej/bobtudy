package doublej.bobtudy.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.http.NewPost;
import doublej.bobtudy.http.PostHttp;
import doublej.bobtudy.util.ISODate;
import doublej.bobtudy.Control.BackPressCloseHandler;
import doublej.bobtudy.R;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class LoginActivity extends Activity {

    public static final int REQUEST_CODE_ANOTHER = 1001;
    private static final String tag = "LoginActivity";

    private BackPressCloseHandler backPressCloseHandler;


    EditText loginID, loginPW;
    Button enterLogin, createID;

    private void test() {
        NewPost newPost = new NewPost("sdfsad", new ISODate(new Date()), "sadf", "asdf", "asdf", "asdf");
        PostHttp.create(newPost, new PostHttp.PostHandler() {
            @Override
            public void onSuccess(Post post) {
                Log.i(tag, post.getId());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        test();

        backPressCloseHandler = new BackPressCloseHandler(this);


        loginID = (EditText) findViewById(R.id.loginID);
        loginPW = (EditText) findViewById(R.id.loginPW);
        enterLogin = (Button) findViewById(R.id.enterLogin);
        createID = (Button) findViewById(R.id.createID);

        enterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),
                        CurrentBoBroom.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);
                overridePendingTransition(R.anim.leftin, R.anim.leftout);

            }
        });

        createID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),
                        JoinActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);

            }
        });
    }

    @Override
    public void onBackPressed() {

        backPressCloseHandler.onBackPressed();
    }
}
