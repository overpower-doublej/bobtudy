package doublej.bobtudy.http.handler;

import java.util.ArrayList;

import doublej.bobtudy.form.post.Chat;

/**
 * Created by Jun on 2014-12-12.
 */
public interface ChatListHandler {
    void onResponse(ArrayList<Chat> chats);
}
