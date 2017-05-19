package cuit.model;

import java.sql.Timestamp;

/**
 * Created by Esong on 2017/4/28.
 */
public class CommentBean {
    private int cId;
    private int uId;
    private int mId;
    private int cParent_Id;
    private Timestamp cTime;
    private String cCommentText;
    private int cRoot_Id;

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getcParent_Id() {
        return cParent_Id;
    }

    public void setcParent_Id(int cParent_Id) {
        this.cParent_Id = cParent_Id;
    }

    public Timestamp getcTime() {
        return cTime;
    }

    public void setcTime(Timestamp cTime) {
        this.cTime = cTime;
    }

    public String getcCommentText() {
        return cCommentText;
    }

    public void setcCommentText(String cCommentText) {
        this.cCommentText = cCommentText;
    }

    public int getcRoot_Id() {
        return cRoot_Id;
    }

    public void setcRoot_Id(int cRoot_Id) {
        this.cRoot_Id = cRoot_Id;
    }
}
