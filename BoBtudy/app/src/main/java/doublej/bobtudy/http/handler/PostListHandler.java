package doublej.bobtudy.http.handler;

import java.util.ArrayList;

import doublej.bobtudy.form.post.Post;

/**
 * Created by Jun on 2014-12-11.
 */
public interface PostListHandler {
    void onResponse(ArrayList<Post> posts);
}
