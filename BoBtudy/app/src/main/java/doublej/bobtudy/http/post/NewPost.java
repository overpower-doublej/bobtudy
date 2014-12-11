package doublej.bobtudy.http.post;

import doublej.bobtudy.form.post.Post;
import doublej.bobtudy.util.ISODate;

/**
 * Created by Jun on 2014-12-08.
 */
public class NewPost extends Post {
    public NewPost(String title, ISODate date, String menu, String place, String content, String bossId) {
        super(null, title, date, menu, place, content, bossId, null);

        this.title = title;
        this.date = date;
        this.menu = menu;
        this.place = place;
        this.content = content;
        this.bossId = bossId;
    }
}
