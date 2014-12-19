package doublej.bobtudy.UI.Login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.UI.CurrentBoBRoom.CurrentBoBroom;
import doublej.bobtudy.UI.Join.JoinActivity;
import doublej.bobtudy.form.post.Access;
import doublej.bobtudy.form.post.Chat;
import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.form.user.User;
import doublej.bobtudy.gcm.Gcm;
import doublej.bobtudy.http.handler.AccessHandler;
import doublej.bobtudy.http.handler.BoolResultHandler;
import doublej.bobtudy.http.handler.ChatListHandler;
import doublej.bobtudy.http.handler.PostHandler;
import doublej.bobtudy.http.handler.PostListHandler;
import doublej.bobtudy.http.handler.ResponseHandler;
import doublej.bobtudy.http.handler.UserHandler;
import doublej.bobtudy.http.post.NewPost;
import doublej.bobtudy.http.post.PostChatHttp;
import doublej.bobtudy.http.post.PostHttp;
import doublej.bobtudy.http.post.PostIdHttp;
import doublej.bobtudy.http.user.NewUser;
import doublej.bobtudy.http.user.UserHttp;
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
    Button btnLogin, btnJoin;


    // Save login data
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

        backPressCloseHandler = new BackPressCloseHandler(this);

        loginID = (EditText) findViewById(R.id.loginID);
        loginPW = (EditText) findViewById(R.id.loginPW);
        btnLogin = (Button) findViewById(R.id.enterLogin);
        btnJoin = (Button) findViewById(R.id.createID);

        String id = pref.getString("id", "");
        String pwd = pref.getString("pwd", "");

        if (!id.isEmpty() && !pwd.isEmpty()) {
            login(id, pwd);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userId = loginID.getText().toString();
                String pwd = loginPW.getText().toString();

                 /* 서버 */
                login(userId, pwd);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        JoinActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);
                overridePendingTransition(R.anim.rightin, R.anim.rightout);

            }
        });
    }

    private void login(final String id, final String pwd) {
        UserHttp.login(id, pwd, new BoolResultHandler() {
            @Override
            public void onResponse(Boolean result) {
                if (result) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("id", id);
                    editor.putString("pwd", pwd);
                    editor.commit();

                    Bundle bundle = new Bundle();
                    bundle.putString("user", id);

                    Intent intent = new Intent(getApplicationContext(), CurrentBoBroom.class);
                    intent.putExtras(bundle);

                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "PW가 일치하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }

                btnLogin.setEnabled(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.d(tag, "RequestCode: ", Integer.toString(requestCode));
    }

    @Override
    public void onBackPressed() {

        backPressCloseHandler.onBackPressed();
    }
}