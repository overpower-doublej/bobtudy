package doublej.bobtudy.http.user;

import doublej.bobtudy.form.user.User;
import doublej.bobtudy.http.handler.ResponseHandler;
import doublej.bobtudy.http.handler.UserHandler;

/**
 * Created by Jun on 2014-12-08.
 */
public class NewUser extends User {
    private String pwd;
    private String dept;
    private String stuId;
    private String info;
    private String regId;

    public NewUser(String id, String pwd, String name, String dept, String stuId, String info, String regId) {
        super(id, name);

        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.dept = dept;
        this.stuId = stuId;
        this.info = info;
        this.regId = regId;
    }

    public void join(ResponseHandler handler) {
        UserHttp.join(this, handler);
    }

    public String getId() {
        return this.id;
    }

    public String getPwd() {
        return this.pwd;
    }

    public String getName() {
        return this.name;
    }

    public String getDept() {
        return this.dept;
    }

    public String getStuId() {
        return this.stuId;
    }

    public String getInfo() {
        return this.info;
    }

    public String getRegId() {
        return this.regId;
    }
}
