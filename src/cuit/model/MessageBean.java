package cuit.model;

import java.sql.Date;

/**
 * Created by Esong on 2017/4/28.
 */
public class MessageBean {
    private int mId;
    private String mSource;
    private String mTitle;
    private String mIntro;
    private String mPic;
    private String mTags;
    private String mAuthor;
    private String mContent;
    private Date mPublishTime;
    private Date mCollectTime;
    private int mLikeCount;
    private int mDislikeCount;
    private int mCollectCount;
    private int mTransmitCount;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmSource() {
        return mSource;
    }

    public void setmSource(String mSource) {
        this.mSource = mSource;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmIntro() {
        return mIntro;
    }

    public void setmIntro(String mIntro) {
        this.mIntro = mIntro;
    }

    public String getmPic() {
        return mPic;
    }

    public void setmPic(String mPic) {
        this.mPic = mPic;
    }

    public String getmTags() {
        return mTags;
    }

    public void setmTags(String mTags) {
        this.mTags = mTags;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public Date getmPublishTime() {
        return mPublishTime;
    }

    public void setmPublishTime(Date mPublishTime) {
        this.mPublishTime = mPublishTime;
    }

    public Date getmCollectTime() {
        return mCollectTime;
    }

    public void setmCollectTime(Date mCollectTime) {
        this.mCollectTime = mCollectTime;
    }

    public int getmLikeCount() {
        return mLikeCount;
    }

    public void setmLikeCount(int mLikeCount) {
        this.mLikeCount = mLikeCount;
    }

    public int getmDislikeCount() {
        return mDislikeCount;
    }

    public void setmDislikeCount(int mDislikeCount) {
        this.mDislikeCount = mDislikeCount;
    }

    public int getmCollectCount() {
        return mCollectCount;
    }

    public void setmCollectCount(int mCollectCount) {
        this.mCollectCount = mCollectCount;
    }

    public int getmTransmitCount() {
        return mTransmitCount;
    }

    public void setmTransmitCount(int mTransmitCount) {
        this.mTransmitCount = mTransmitCount;
    }
}
