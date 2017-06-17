package cuit.model;

/**
 * Created by Esong on 2017/5/26.
 */
public class LogInfo {
    private String date;
    private String logType;
    private String logContext;
    private String objName;
    private String mId;
    private String mTags;

    public LogInfo(String date, String logType, String logContext,String mId,String mTags,String objName){
        this.date = date;
        this.logType = logType;
        this.logContext = logContext;
        this.mId = mId;
        this.mTags = mTags;
        this.objName = objName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogContext() {
        return logContext;
    }

    public void setLogContext(String logContext) {
        this.logContext = logContext;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTags() {
        return mTags;
    }

    public void setmTags(String mTags) {
        this.mTags = mTags;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    @Override
    public String toString() {
        return date+"-1b1-"+logType+"-1b1-"+logContext+"-1b1-"+objName+"-1b1-"+mId+"-1b1-"+mTags+"\r\n";
    }
}
