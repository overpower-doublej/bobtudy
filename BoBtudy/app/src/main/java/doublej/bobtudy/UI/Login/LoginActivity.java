package doublej.bobtudy.UI.Login;

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

        final String userId = "newUser1234567";
        UserHttp.checkIdExists(userId, new BoolResultHandler() {
            @Override
            public void onResponse(Boolean result) {
                if (result) {
                    UserHttp.getUser(userId, new UserHandler() {
                        @Override
                        public void onResponse(User user) {
                            Log.i(tag, userId + " exists");
                            Log.i(tag, user.toString());
                        }
                    });
                } else {
                    UserHttp.join(new NewUser(userId, "newuserpwd", "이이름", "학과과", "202022020", "123", "asdfadf"), new ResponseHandler() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.i(tag, "User created: " + userId + " / response: " + jsonObject.toString());

                            UserHttp.login(userId, "newuserpwd", new BoolResultHandler() {
                                @Override
                                public void onResponse(Boolean result) {
                                    Log.i(tag, userId + " login result: " + Boolean.toString(result));
                                }
                            });
                        }
                    });
                }
            }
        });

        User.findUser("user1", new UserHandler() {
            @Override
            public void onResponse(final User user1) {
                Log.i(tag, "find user1 #1");
                User.findUser("user1", new UserHandler() {
                    @Override
                    public void onResponse(User user2) {
                        Log.i(tag, "find user1 #2");
                        Log.i(tag, "#1 == #2 ?: " + Boolean.toString(user1 == user2));
                    }
                });
            }
        });

        Gcm gcm = new Gcm(this);
        gcm.getRegId(new Gcm.RegIdHandler() {
            @Override
            public void onResponse(String regId) {
                Log.i(tag, "REGISTRATION_ID: " + regId);
                NewUser newUser = new NewUser("newnewnew", "newnewnew", "newName", "과과", "123123", "information", regId);
                newUser.join(new ResponseHandler() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i(tag, "User created with registration id: " + jsonObject.toString());
                    }
                });
            }
        });

        PostChatHttp.listChatAfter("548aa6cea3c1132141d15842", ISODate.getInstanceByIsoString("2014-12-12T08:26:54.607Z"), new ChatListHandler() {
            @Override
            public void onResponse(ArrayList<Chat> chats) {
                for (int i = 0; i < chats.size(); i++) {
                    Chat chat = chats.get(i);
                    Log.i(tag, chat.toString());
                }
            }
        });

        PostChatHttp.sendChat("548aa6cea3c1132141d15842", "user3", "유저3의 메세지", new ResponseHandler() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                PostChatHttp.listChatAfter("548aa6cea3c1132141d15842", ISODate.getInstanceByIsoString("2014-12-12T08:26:54.607Z"), new ChatListHandler() {
                    @Override
                    public void onResponse(ArrayList<Chat> chats) {
                        for (int i = 0; i < chats.size(); i++) {
                            Chat chat = chats.get(i);
                            Log.i(tag, chat.toString());
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
        final SQLiteDatabase db = myDB.getWritableDatabase();

        enterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterLogin.setEnabled(false);

                final String userId = loginID.getText().toString();
                String pwd = loginPW.getText().toString();


                UserHttp.login(userId, pwd, new BoolResultHandler() {
                    @Override
                    public void onResponse(Boolean result) {
                        if (result) {
                            Bundle bundle = new Bundle();
                            bundle.putString("user", userId);

                            Intent intent = new Intent(getApplicationContext(), CurrentBoBroom.class);
                            intent.putExtras(bundle);

                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "PW가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        enterLogin.setEnabled(true);
                    }
                });


                /* 로그인 내장디비*/
                /*String searchId = loginID.getText().toString();

                Cursor cursor = db.rawQuery("SELECT * FROM myInfo WHERE id LIKE ?", new String[]{searchId});

                int loginPWCol = cursor.getColumnIndex("pwd");

                if (cursor.getCount() != 0) {

                    while (cursor.moveToNext()) {

                        String PW = cursor.getString(loginPWCol);

                        if (loginPW.getText().toString().equals(PW)) {

                            cursor.close();

                            Bundle bundle = new Bundle();
                            bundle.putString("user", searchId);

                            Intent intent = new Intent(getApplicationContext(), CurrentBoBroom.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.leftin, R.anim.leftout);

                        } else {
                            Toast.makeText(LoginActivity.this, "PW가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "ID가 일치하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }*/



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
                overridePendingTransition(R.anim.rightin, R.anim.rightout);

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
