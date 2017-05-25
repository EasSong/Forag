package cuit.log;

import cuit.model.LogInfo;

import java.util.ArrayList;

/**
 * Created by Esong on 2017/5/26.
 * 日志操作接口
 */
public interface UserLog {
    int writeUserLog(String uId, String date, String useType, String useLog);
    ArrayList<LogInfo> readUserLog(String uId, String date, int length);
}
