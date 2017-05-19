package cuit.model;

import java.sql.Timestamp;

/**
 * Created by Esong on 2017/4/28.
 */
public class MessageBean {
    private int mId;
    private int uId;
    private Timestamp mTime;
    private String mSource;
    private String mTags;
    private String mStore_uri;
    private String mTitle;
    private String mIntro;
    private String mContext;
    private String mPic;//这里的图片应该是二进制数据。这里暂时先用字符串读取
    private int mLike_Count;
    private int mCollect_Count;
    private int mTransmit_Count;
    private int mHavePic;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public Timestamp getmTime() {
        return mTime;
    }

    public void setmTime(Timestamp mTime) {
        this.mTime = mTime;
    }

    public String getmSource() {
        return mSource;
    }

    public void setmSource(String mSource) {
        this.mSource = mSource;
    }

    public void setmTags(String mTags) {
        this.mTags = mTags;
    }

    public String getmTags() {
        return mTags;
    }

    public String getmStore_uri() {
        return mStore_uri;
    }

    public void setmStore_uri(String mStore_uri) {
        this.mStore_uri = mStore_uri;
    }

    public String getmIntro() {
        return mIntro;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmIntro(String mIntro) {
        this.mIntro = mIntro;
    }

    public String getmContext() {
        return mContext;
    }

    public void setmContext(String mContext) {
        this.mContext = mContext;
    }

    public String getmPic() {
        return mPic;
    }

    public void setmPic(String mPic) {
        this.mPic = mPic;
    }

    public int getmLike_Count() {
        return mLike_Count;
    }

    public void setmLike_Count(int mLike_Count) {
        this.mLike_Count = mLike_Count;
    }

    public int getmCollect_Count() {
        return mCollect_Count;
    }

    public void setmCollect_Count(int mCollect_Count) {
        this.mCollect_Count = mCollect_Count;
    }

    public int getmTransmit_Count() {
        return mTransmit_Count;
    }

    public void setmTransmit_Count(int mTransmit_Count) {
        this.mTransmit_Count = mTransmit_Count;
    }

    public int getmHavePic() {
        return mHavePic;
    }

    public void setmHavePic(int mHavePic) {
        this.mHavePic = mHavePic;
    }
}
