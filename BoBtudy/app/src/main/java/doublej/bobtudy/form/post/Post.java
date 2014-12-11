package doublej.bobtudy.form.post;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import doublej.bobtudy.form.user.User;
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
    private HashSet<Access> accesses;
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
        this.accesses = new HashSet<Access>();

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
        this.accesses = new HashSet<Access>();

        postMap.put(id, this);
    }

    public void addUser(User user) {
        this.users.add(user);
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

    public static Post getPost(String postId) {
        return (Post) postMap.get(postId);
    }

    public static Post parseJsonObject(JSONObject jsonObject) {
        Post post = null;
        try {
            String id = jsonObject.getString("_id");
            String title = jsonObject.getString("title");
            String dateString = jsonObject.getString("date");
            ISODate date = ISODate.getInstanceByIsoString(dateString);
            String menu = jsonObject.getString("menu");
            String place = jsonObject.getString("place");
            String content = jsonObject.getString("content");
            String bossId = jsonObject.getString("boss");
            String postedDateString = jsonObject.getString("postedDate");
            ISODate postedDate = ISODate.getInstanceByIsoString(postedDateString);

            post = new Post(id, title, date, menu, place, content, bossId, postedDate);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return post;
    }
}
