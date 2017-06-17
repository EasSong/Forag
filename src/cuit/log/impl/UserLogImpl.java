package cuit.log.impl;

import cuit.dao.UserDao;
import cuit.log.UserLog;
import cuit.model.LogInfo;
import cuit.model.MessageBean;
import cuit.model.UserBean;
import cuit.service.MessageService;
import cuit.service.UserService;
import cuit.util.AppUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Esong on 2017/5/26.
 * 保存用户操作的日志
 */
public class UserLogImpl implements UserLog {
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
        if (!LogRandomFileUtil.createLogFile(logFileName)) {
            System.out.println("The file is exist!");
        }
        //日志信息
        logInfo.setDate(timeStr);
        int codeState = LogRandomFileUtil.writeUserLog(logFileName, logInfo.toString());

        return codeState;
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

    public JSONArray readUserLogForTimeLine(String uId, int offsetLength, int maxLength) {
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "date");
        jsonObject.put("date", dateFormat.format(nowDate));
        logJsonArr.add(jsonObject);
        for (int i = listLog.size() - 1; i >= 0; i--) {
            if (offset++ < offsetLength) {
                continue;
            }
            if (count++ > maxLength) {
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
            if (!logDate.equals(dateFormat.format(nowDate))) {
                try {
                    logTime = daysBetween(dateFormat.parse(logDate), nowDate) + "days ago";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("type", "date");
                jsonObject1.put("date", logDate);
                logJsonArr.add(jsonObject1);
            } else {
                logTime = timeFormat.format(date);
            }
            if (logItems[1].equals("browse") || logItems[1].equals("comment") || logItems[1].equals("like")) {
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
                System.err.println("日志类型出错: " + logItems[1]);
            }
        }

        return logJsonArr;
    }

    //计算两个日期相差多少天
    private String daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return String.valueOf(between_days);
    }
}
