package cuit.log.impl;

import cuit.log.UserLog;
import cuit.model.LogInfo;

import java.util.ArrayList;

/**
 * Created by Esong on 2017/5/26.
 * 保存用户操作的日志
 */
public class UserLogImpl implements UserLog{
    @Override
    public int writeUserLog(String uId, String date, String useType, String useLog) {
        return 0;
    }

    @Override
    public ArrayList<LogInfo> readUserLog(String uId, String date, int length) {
        return null;
    }
}
