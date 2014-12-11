package doublej.bobtudy.form.post;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import doublej.bobtudy.form.user.User;
import doublej.bobtudy.http.post.PostIdHttp;
import doublej.bobtudy.util.ISODate;

public class Post {
    private String id;
    protected String title;
    protected ISODate date;
    protected String menu;
    protected String place;
    protected String content;
    protected String bossId;
    private ISODate postedDate;

    private HashSet<User> users;
    private HashMap<String, Access> accesses;
    //private ArrayList<Chat> chats;

    private static HashMap postMap = new HashMap();

    public Post(String id, String title, ISODate date, String menu, String bossId, ISODate postedDate) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.menu = menu;
        this.bossId = bossId;
        this.postedDate = postedDate;

        this.users = new HashSet<User>();
        this.accesses = new HashMap<String, Access>();

        postMap.put(id, this);
    }

    public Post(String id, String title, ISODate date, String menu, String place, String content, String bossId, ISODate postedDate) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.menu = menu;
        this.place = place;
        this.content = content;
        this.bossId = bossId;
        this.postedDate = postedDate;

        this.users = new HashSet<User>();
        this.accesses = new HashMap<String, Access>();

        postMap.put(id, this);
    }

    public void refresh(final ResponseHandler handler) {
        PostIdHttp.getPost(this.id, new PostIdHttp.PostHandler() {
            @Override
            public void onSuccess(Post post) {
                handler.onResponse();
            }
        });
    }

    public void listAccess(final ResponseHandler handler) {
        PostIdHttp.listAccess(this, new PostIdHttp.PostHandler() {
            @Override
            public void onSuccess(Post post) {
                handler.onResponse();
            }
        });
    }

    public void createAccess(String userId, final ResponseHandler handler) {
        PostIdHttp.createAccess(this, userId, new PostIdHttp.PlainHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                handler.onResponse();
            }
        });
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addAccess(Access access) {
        this.accesses.put(access.getId(), access);
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public ISODate getDate() {
        return this.date;
    }

    public ISODate getPostedDate() {
        return this.postedDate;
    }

    public String getMenu() {
        return this.menu;
    }

    public String getPlace() {
        return this.place;
    }

    public String getContent() {
        return this.content;
    }

    public String getBossId() {
        return this.bossId;
    }

    public HashMap<String, Access> getAccesses() {
        return this.accesses;
    }


    public static Post getPost(String postId) {
        return (Post) postMap.get(postId);
    }

    /**
     * If key of jsonObject is empty, that property of key is empty string
     *
     * @param jsonObject
     * @return
     */
    public static Post parseJsonObject(JSONObject jsonObject) {
        String id = jsonObject.optString("_id");
        String title = jsonObject.optString("title");
        String menu = jsonObject.optString("menu");
        String place = jsonObject.optString("place");
        String content = jsonObject.optString("content");
        String bossId = jsonObject.optString("boss");

        String dateString = jsonObject.optString("date");
        ISODate date = ISODate.getInstanceByIsoString(dateString);

        String postedDateString = jsonObject.optString("postedDate");
        ISODate postedDate = ISODate.getInstanceByIsoString(postedDateString);

        Post post = new Post(id, title, date, menu, place, content, bossId, postedDate);

        return post;
    }

    public interface ResponseHandler {
        void onResponse();
    }
}
