package doublej.bobtudy.UI;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import doublej.bobtudy.form.post.Access;
import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.form.user.User;
import doublej.bobtudy.http.handler.AccessHandler;
import doublej.bobtudy.http.handler.PostHandler;
import doublej.bobtudy.http.handler.PostListHandler;
import doublej.bobtudy.http.handler.ResponseHandler;
import doublej.bobtudy.http.post.NewPost;
import doublej.bobtudy.http.post.PostHttp;
import doublej.bobtudy.http.post.PostIdHttp;
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
        PostHttp.create(newPost, new PostHandler() {
            @Override
            public void onResponse(Post post) {
                Log.i(tag, "New Post ID: " + post.getId());
            }
        });

        PostHttp.list(new PostListHandler() {
            @Override
            public void onResponse(ArrayList<Post> posts) {
                Log.i(tag, "Post List");
                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.i(tag, post.getId() + ": " + post.getTitle() + " - " + post.getDate().toString() + " - " + post.getPostedDate().toString());
                }
            }
        });

        PostIdHttp.getPost("548537f493e2661812a0ff07", new PostHandler() {
            @Override
            public void onResponse(Post post) {
                Log.i(tag, "Post title: " + post.getTitle());
                Log.i(tag, "Post accesses");

                PostIdHttp.listAccess(post, new PostHandler() {
                    @Override
                    public void onResponse(Post post) {
                        Iterator<String> it = post.getAccesses().keySet().iterator();
                        while (it.hasNext()) {
                            String id = it.next();
                            Log.i(tag, post.getAccesses().get(id).toString());
                        }
                    }
                });

                PostIdHttp.createAccess(post, "userNEW", new ResponseHandler() {
                    @Override
                    public void onResponse(JSONObject res) {
                        Log.i(tag, res.toString());
                    }
                });

                PostIdHttp.getAccess("548537f493e2661812a0ff07", "54896efd0707d4125df4ffe8", new AccessHandler() {
                    @Override
                    public void onResponse(Access access) {
                        Log.i(tag, "get access: " + access.toString());
                    }
                });

                PostIdHttp.voteAccess("548537f493e2661812a0ff07", "54896efd0707d4125df4ffe8", "user3", true, new ResponseHandler() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(tag, "vote access: " + response.toString());
                    }
                });
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

        MyDatabase myDB = new MyDatabase(this);
        final SQLiteDatabase db = myDB.getReadableDatabase();

        enterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchId = loginID.getText().toString();

                Cursor cursor = db.rawQuery("SELECT * FROM myInfo WHERE id LIKE ?", new String[]{searchId});

                int loginPWCol = cursor.getColumnIndex("pwd");
                //int loginPWCol = cursor.getColumnIndex("nickName");
                //bundle을 닉네임으로 하도록 바꿔야됨


                if (cursor.getCount() != 0) {

                    while (cursor.moveToNext()) {

                        String PW = cursor.getString(loginPWCol);

                        if (loginPW.getText().toString().equals(PW)) {

                            cursor.close();

                            Bundle bundle = new Bundle();
                            //bundle.putString("user", ID);

                            Intent intent = new Intent(getApplicationContext(), CurrentBoBroom.class);
                            intent.putExtras(bundle);

                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "PW가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "ID가 일치하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }



                /*int logbinPWCol = cursorID.getColumnIndex("pwd");


                Intent intent = new Intent(getBaseContext(),
                        CurrentBoBroom.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);
                overridePendingTransition(R.anim.leftin, R.anim.leftout);*/

            }
        });

        createID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        JoinActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER);
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
