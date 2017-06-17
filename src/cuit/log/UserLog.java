package cuit.log;

import cuit.model.LogInfo;
import java.util.List;

/**
 * Created by Esong on 2017/5/26.
 * 日志操作接口
 */
public interface UserLog {
    int writeUserLog(String uId, LogInfo logInfo);
    List<String> readUserLog(String uId);
}
