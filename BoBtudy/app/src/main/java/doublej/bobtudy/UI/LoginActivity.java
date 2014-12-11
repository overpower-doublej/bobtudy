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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.form.post.Post;
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
        PostHttp.create(newPost, new PostHttp.PostHandler() {
            @Override
            public void onSuccess(Post post) {
                Log.i(tag, "New Post ID: " + post.getId());
            }
        });

        PostHttp.list(new PostHttp.PostListHandler() {
            @Override
            public void onSuccess(ArrayList<Post> posts) {
                Log.i(tag, "Post List");
                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.i(tag, post.getId() + ": " + post.getTitle() + " - " + post.getDate().toString() + " - " + post.getPostedDate().toString());
                }
            }
        });

        PostIdHttp.getPost("548537f493e2661812a0ff07", new PostIdHttp.PostHandler() {
            @Override
            public void onSuccess(Post post) {
                Log.i(tag, "Post title: " + post.getTitle());
                Log.i(tag, "Post accesses");

                PostIdHttp.listAccess(post, new PostIdHttp.PostHandler() {
                    @Override
                    public void onSuccess(Post post) {
                        Iterator<String> it = post.getAccesses().keySet().iterator();
                        while (it.hasNext()) {
                            String id = it.next();
                            Log.i(tag, post.getAccesses().get(id).toString());
                        }
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

                int loginIDCol = cursor.getColumnIndex("id");
                int loginPWCol = cursor.getColumnIndex("pwd");

                if (cursor.getCount() != 0) {

                    while (cursor.moveToNext()) {

                        String PW = cursor.getString(loginPWCol);

                        if (loginPW.getText().toString().equals(PW)) {

                            cursor.close();

                            Intent intent = new Intent(getBaseContext(),
                                    CurrentBoBroom.class);
                            startActivityForResult(intent, REQUEST_CODE_ANOTHER);
                            overridePendingTransition(R.anim.leftin, R.anim.leftout);
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
