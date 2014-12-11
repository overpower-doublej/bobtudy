package doublej.bobtudy.http.handler;

import doublej.bobtudy.form.user.User;

/**
 * Created by Jun on 2014-12-11.
 */
public interface UserHandler {
    void onResponse(User user);
}