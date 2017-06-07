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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Esong on 2017/5/26.
 * 保存用户操作的日志
 */
public class UserLogImpl implements UserLog{
    @Override
    public int writeUserLog(String uId, LogInfo logInfo) {
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = timeFormat.format(date);

        UserService userService = AppUtil.getUserService();
        UserBean userBean = new UserBean();
        try{
            userBean = userService.getUserDetail(Integer.parseInt(uId));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }finally {
            userService = null;
        }
        //日志文件路径
        String userEmail = userBean.getUtMail();
        String logFileName = userEmail.split("@")[0]+"-behavior.log";
        if (!LogRandomFileUtil.createLogFile(logFileName)){
            System.out.println("The file is exist!");
        }
        //日志信息
        String logType = logInfo.getLogType();
        String logContent = logInfo.getLogContent();
        int codeState = LogRandomFileUtil.writeUserLog(logFileName,timeStr + "," +logType + ","+logContent + "\r\n");

        return codeState;
    }

    @Override
    public JSONObject readUserLog(String uId, String date, int maxLength) {
        UserService userService = AppUtil.getUserService();
        UserBean userBean = new UserBean();
        try{
            userBean = userService.getUserDetail(Integer.parseInt(uId));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }  finally {
            userService = null;
        }
        //日志文件路径
        String userName = userBean.getUtName();
        String logFileName = date+"/"+userName+"-behavior.log";
        //读取日志信息
        ArrayList<String> listLogStr = LogRandomFileUtil.readUserLog(logFileName);
        JSONObject jsonLogInfor = new JSONObject();
        //分类封装日志
        JSONArray jsonCommentLog = new JSONArray();
        JSONArray jsonBrowenLog = new JSONArray();
        JSONArray jsonLikeLog = new JSONArray();
        for (String logStr:listLogStr){
            String[] arrLog = logStr.split(",");
            String[] logType = arrLog[1].split(" ");
            if (logType[1].equals("like")){
                if (jsonLikeLog.size() > maxLength){
                    continue;
                }
                JSONObject jsonObjLike = new JSONObject();
                jsonObjLike.put("time",arrLog[0]);
                String targetId = logType[logType.length-1];
                MessageService messageService = AppUtil.getMessageService();
                MessageBean messageBean = messageService.selectById(Integer.parseInt(targetId));
                jsonObjLike.put("msg",messageBean.getmIntro());
                jsonLikeLog.add(jsonObjLike);
            }else if (logType[1].equals("comment")){
                if (jsonCommentLog.size() > maxLength){
                    continue;
                }
                JSONObject jsonObjComment = new JSONObject();
                jsonObjComment.put("time",arrLog[0]);
                jsonObjComment.put("type","Comment "+arrLog[2]);
                jsonObjComment.put("context",arrLog[2]);
                String targetId = logType[logType.length-1];
                MessageService messageService = AppUtil.getMessageService();
                MessageBean messageBean = messageService.selectById(Integer.parseInt(targetId));
                jsonObjComment.put("msg",messageBean.getmIntro());
                jsonLikeLog.add(jsonObjComment);
            }else if (logType[1].equals("browse")){
                if (jsonBrowenLog.size() > maxLength){
                    continue;
                }
                JSONObject jsonObjBrowse = new JSONObject();
                jsonObjBrowse.put("time",arrLog[0]);
                MessageService messageService = AppUtil.getMessageService();
                String targetId = logType[logType.length-1];
                MessageBean messageBean = messageService.selectById(Integer.parseInt(targetId));
                jsonObjBrowse.put("msg",messageBean.getmIntro());
                jsonBrowenLog.add(jsonObjBrowse);
            }else{
                System.out.println("错误的日志类型。");
            }
        }
        jsonLogInfor.put("likeLog",jsonLikeLog);
        jsonLogInfor.put("commentLog",jsonCommentLog);
        jsonLogInfor.put("browseLog",jsonBrowenLog);

        return jsonLogInfor;
    }
}
