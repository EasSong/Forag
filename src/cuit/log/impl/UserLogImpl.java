package cuit.log.impl;

import com.sun.org.apache.xerces.internal.xs.StringList;
import cuit.dao.UserDao;
import cuit.log.UserLog;
import cuit.model.LogInfo;
import cuit.model.MessageBean;
import cuit.model.UserBean;
import cuit.service.MessageService;
import cuit.service.UserService;
import cuit.util.AppUtil;
import cuit.util.ConstantDeclare;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Esong on 2017/5/26.
 * 保存用户操作的日志
 */
public class UserLogImpl implements UserLog {
    private final String[] logTypeArr = {"browse","comment","like","dislike","collect","transmit"};
    @Override
    public int writeUserLog(String uId, LogInfo logInfo) {
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = timeFormat.format(date);

        UserService userService = AppUtil.getUserService();
        UserBean userBean = new UserBean();
        try {
            userBean = userService.getUserDetail(Integer.parseInt(uId));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            userService = null;
        }
        //日志文件路径
        String userEmail = userBean.getUtMail();
        String logFileName = userEmail.split("@")[0] + "-behavior.log";
        if (LogRandomFileUtil.createLogFile(logFileName)) {
            System.out.println("info: create file is success, file name: "+logFileName);
        }
        logInfo.setDate(timeStr);
        int codeState = ConstantDeclare.ERROR_WRITE_LOG;
        try {
            if (checkUserLog(readUserLog(uId),logInfo,20,1)) {
                //日志信息
                codeState = LogRandomFileUtil.writeUserLog(logFileName, logInfo.toString());
            }else{
                System.out.println("error: Write in logfile error, log information:"+logInfo.toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return codeState;
    }
    /*
    检查日志是不是重复产生
    参数：   oldLogList      老日志列表
            logInfo         新日志
            compareLength   比较长度
            delayDays       延时时间,以天为单位
    返回值： true   没有重复，能够写入日志文件
            false  有重复，不能写入日志文件
     */
    public boolean checkUserLog(List<String> oldLogList, LogInfo logInfo, int compareLength, int delayDays) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = oldLogList.size()-1; i > 0; i--){
            if (oldLogList.size() - i > compareLength){
                break;
            }
            String[] items = oldLogList.get(i).split("-1b1-");
            if (daysBetween(dateFormat.parse(items[0]),dateFormat.parse(dateFormat.format(new Date(System.currentTimeMillis())))) > delayDays){
                break;
            }
            if (items[1].equals(logInfo.getLogType()) && items[4].equals(logInfo.getmId())){
                if (!items[1].equals("comment")) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public List<String> readUserLog(String uId) {
        UserService userService = AppUtil.getUserService();
        UserBean userBean = new UserBean();
        try {
            userBean = userService.getUserDetail(Integer.parseInt(uId));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            userService = null;
        }
        //日志文件路径
        String userEmail = userBean.getUtMail();
        String logFileName = userEmail.split("@")[0] + "-behavior.log";
        //读取日志信息
        //List<String> listLogStr = LogRandomFileUtil.readUserLog(logFileName);

        return LogRandomFileUtil.readUserLog(logFileName);
    }

    //记录上次读取的日志的时间
    private static String oldDate = "";
    public JSONArray readUserLogForTimeLine(String uId, int offsetLength, int maxLength) {
        ArrayList<String> listType = new ArrayList<>(Arrays.asList(logTypeArr));
        List<String> listLog = readUserLog(uId);
        listLog.remove(0);//移除第一条标题内容
        JSONArray logJsonArr = new JSONArray();//封装日志信息
        int count = 0;//获取日志数量计数
        int offset = 0;//偏移量计数
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date nowDate = new Date(System.currentTimeMillis());
        MessageService messageService = AppUtil.getMessageService();
        try {//比较当前日期和最近一条日志的时间
            if (offsetLength == 0 && listLog.size() > 1 && dateFormat.format(dateTimeFormat.parse(listLog.get(listLog.size() - 1).split("-1b1-")[0])).equals(dateFormat.format(nowDate))) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "date");
                jsonObject.put("date", dateFormat.format(nowDate));
                logJsonArr.add(jsonObject);
                oldDate = dateFormat.format(nowDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = listLog.size() - 1; i >= 0; i--) {
            if (offset++ < offsetLength) {
                continue;
            }
            if (count++ >= maxLength) {
                break;
            }
            String[] logItems = listLog.get(i).split("-1b1-");
            String logDate = null;
            Date date = new Date();
            try {
                date = dateTimeFormat.parse(logItems[0]);
                logDate = dateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String logTime = "";
            if (logDate!=null && !logDate.equals(oldDate)) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("type", "date");
                jsonObject1.put("date", logDate);
                logJsonArr.add(jsonObject1);
                oldDate = logDate;
            }
            if (logDate!=null && !logDate.equals(dateFormat.format(nowDate))){
                try {
                    logTime = String.valueOf(daysBetween(dateFormat.parse(logDate), nowDate)) + " days ago";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                logTime = timeFormat.format(date);
            }
            if (listType.contains(logItems[1])){
                //System.out.println("Info: Read user log is success,type is "+logItems[1]);
                JSONObject logItem = new JSONObject();
                logItem.put("type", logItems[1]);
                logItem.put("time", logTime);
                logItem.put("context", logItems[2]);
                logItem.put("objName", logItems[3]);
                logItem.put("mId",logItems[4]);
                MessageBean messageBean = messageService.selectById(Integer.parseInt(logItems[4]));
                logItem.put("title", messageBean.getmTitle());
                logJsonArr.add(logItem);
            } else {
                System.err.println("Error: Log type error: " + logItems[1]);
            }
        }

        return logJsonArr;
    }

    //计算两个日期相差多少天
    private long daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return between_days;
    }

    public JSONObject readUserLogForSocket(String uId, int offsetLength, int maxLogLength, int historyLength){
        List<String> listLog = readUserLog(uId);
        JSONObject logJson = new JSONObject();//log info
        JSONArray logArr = new JSONArray();//log
        JSONArray hisArr = new JSONArray();//history mid
        int offsetCount = 0;
        int logCount = 0;
        int historyCount = 0;

        for (int i = listLog.size() - 1; i >= 1; i--){
            if (offsetCount++ < offsetLength){
                continue;
            }
            if (historyCount++ > historyLength){
                break;
            }
            String[] logItems = listLog.get(i).split("-1b1-");
            if (logItems[1].equals("recommend")){
                logCount = maxLogLength+1;
            }else {
                hisArr.add(logItems[4]);
            }
            if (logCount++ <= maxLogLength) {
                JSONObject logInfo = new JSONObject();
                logInfo.put("time", logItems[0]);
                logInfo.put("logType", logItems[1]);
                logInfo.put("msgId", logItems[4]);
                logInfo.put("msgTags", logItems[5]);
                logInfo.put("msgSource",logItems[6]);
                logArr.add(logInfo);
            }
        }
        logJson.put("data",logArr);
        logJson.put("history",hisArr);
        writeUserLog(uId,new LogInfo("","recommend","null","null","{}","null","null"));

        return logJson;
    }
}
