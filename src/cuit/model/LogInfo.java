package cuit.model;

/**
 * Created by Esong on 2017/5/26.
 */
public class LogInfo {
    private String date;
    private String logType;
    private String logContent;

    public LogInfo(String date, String logType, String logContent){
        this.date = date;
        this.logType = logType;
        this.logContent = logContent;
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

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }


}
