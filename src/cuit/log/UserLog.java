package cuit.log;

import cuit.model.LogInfo;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Esong on 2017/5/26.
 * 日志操作接口
 */
public interface UserLog {
    int writeUserLog(String uId, LogInfo logInfo);
    JSONObject readUserLog(String uId, String date, int maxLength);
}
