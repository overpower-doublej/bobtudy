package doublej.bobtudy.UI.Chatting;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import doublej.bobtudy.Control.MyDatabase;
import doublej.bobtudy.R;
import doublej.bobtudy.form.post.Chat;
import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.http.handler.ChatListHandler;
import doublej.bobtudy.http.handler.PostHandler;
import doublej.bobtudy.http.handler.ResponseHandler;
import doublej.bobtudy.http.post.PostChatHttp;
import doublej.bobtudy.http.post.PostIdHttp;

/**
 * Created by YeomJi on 2014. 12. 5..
 */
public class ChattingActivity extends Activity {

    private final String tag = "ChattingActivity";

    static String Nick, post_id, ID;

    TextView ChattingContent;
    EditText ChattingInputContent;
    Button ChattingInputOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chatting_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int nid = bundle.getInt("nid");
        if (nid != 0) {
            // notification 매니저 생성
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // 등록된 notification 을 제거 한다.
            nm.cancel(nid);
        }

        ChattingContent = (TextView) findViewById(R.id.ChattingContent);
        ChattingInputContent = (EditText) findViewById(R.id.ChattingInputContent);
        ChattingInputOK = (Button) findViewById(R.id.ChattingInputOK);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ChattingActivity.this);
        SharedPreferences.Editor editor = pref.edit();
        final String id = pref.getString("id", "");
        final String postId = pref.getString("mypost", "");

        final ArrayList<Chat> chatList = new ArrayList<Chat>();

        PostIdHttp.getPost(postId, new PostHandler() {
            @Override
            public void onResponse(Post post) {
                PostChatHttp.listChatAfter(post.getId(), post.getPostedDate(), new ChatListHandler() {
                    @Override
                    public void onResponse(ArrayList<Chat> chats) {
                        chatList.addAll(chats);
                        Iterator<Chat> it = chats.iterator();
                        while (it.hasNext()) {
                            Chat chat = it.next();
                            println(chat.toString());
                        }
                    }
                });
            }
        });

        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        Nick = bundle.getString("nickName");
        ID = bundle.getString("user");
//        post_id = bundle.getString("post_id");

        MyDatabase myDB = new MyDatabase(this);
        final SQLiteDatabase db = myDB.getWritableDatabase();


        String sql = "SELECT * FROM post_chat pc, user u WHERE u.id = pc.userId AND postId LIKE ?";
        Cursor cursor = db.rawQuery(sql, new String[]{post_id});

        int recordCount = cursor.getCount();
        Log.d(tag, "cursor count : " + recordCount + "\n");

        int nickNameCol = cursor.getColumnIndex("nickName");
        int dateCol = cursor.getColumnIndex("date");
        int msgCol = cursor.getColumnIndex("msg");

        Resources res = getResources();

        for (int i = 0; i < cursor.getCount(); i++) {
            while (cursor.moveToNext()) {
                String nickName = cursor.getString(nickNameCol);
                String date = cursor.getString(dateCol);
                String msg = cursor.getString(msgCol);

                ChattingContent.append(nickName + " (" + date + ") : " + msg + "\r\n");

            }
        }

        cursor.close();*/


        ChattingInputOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long time = System.currentTimeMillis();
                SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
                String str = dayTime.format(new Date(time));

                /* postId에 값 입력 *//*
                db.execSQL("INSERT INTO post_chat VALUES ( '" + post_id + "','" + ID + "', '" + str + "', '" +
                        ChattingInputContent.getText().toString() + "' );");*/

//                ChattingContent.append(Nick + " (" + str + ") : " + ChattingInputContent.getText().toString() + "\r\n");
                PostChatHttp.sendChat(postId, id, ChattingInputContent.getText().toString(), new ResponseHandler() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        PostChatHttp.listChatAfter(postId, chatList.get(chatList.size() - 1).getDate(), new ChatListHandler() {
                            @Override
                            public void onResponse(ArrayList<Chat> chats) {
                                chatList.addAll(chats);
                                Iterator<Chat> it = chats.iterator();
                                while (it.hasNext()) {
                                    Chat chat = it.next();
                                    println(chat.toString());
                                }
                            }
                        });
                    }
                });

                ChattingInputContent.setText("");
            }
        });

    }

    private void println(String msg) {
        ChattingContent.append("\n" + msg);
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.leftin, R.anim.leftout);
    }
}
