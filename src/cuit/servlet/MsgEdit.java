package cuit.servlet;

import cuit.log.UserLog;
import cuit.log.impl.UserLogImpl;
import cuit.model.LogInfo;
import cuit.service.MessageService;
import cuit.util.AppUtil;
import cuit.util.ConstantDeclare;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by Esong on 2017/5/4.
 */
public class MsgEdit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String type = request.getParameter("type");
        String mId = request.getParameter("mId");
        String mTags = request.getParameter("mTags");
        String uId = request.getParameter("uId");
        String mSource = request.getParameter("mSource");
        int stateCode = ConstantDeclare.ERROR_UPDATE_LIKE;
        MessageService messageService = AppUtil.getMessageService();
        if (type.equals("like")){
            stateCode = messageService.updateLikeCountById(Integer.parseInt(mId));
            sendCallbackInfo(uId, response, mId, mTags, stateCode, "like",mSource);
        }else if(type.equals("dislike")) {
            stateCode = messageService.updateDisLikeCountById(Integer.parseInt(mId));
            sendCallbackInfo(uId, response, mId, mTags, stateCode, "dislike",mSource);
        }else if(type.equals("share")){
            stateCode = messageService.updateTransmitCountById(Integer.parseInt(mId));
            sendCallbackInfo(uId, response, mId, mTags, stateCode, "transmit",mSource);
        }else if(type.equals("collect")){
            stateCode = messageService.updateCollectCountById(Integer.parseInt(mId));
            sendCallbackInfo(uId, response, mId, mTags, stateCode, "collect",mSource);
        }
    }

    private void sendCallbackInfo(String uId, HttpServletResponse response, String mId, String mTags, int stateCode, String type,String mSource) throws IOException {
        if (stateCode == ConstantDeclare.ERROR_UPDATE_LIKE){
            response.getWriter().print(type+",failed");
        }else{
            response.getWriter().print(type+",success");
            recordUserLog(uId, mId, mTags,type,mSource);
        }
    }

    private void recordUserLog(String uId,String mId, String mTags,String type,String mSource) {
        UserLog userLog = new UserLogImpl();
        if (Integer.parseInt(uId) > -1){
            userLog.writeUserLog(uId,new LogInfo("null",type,"like msg",mId,mTags,"null",mSource));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
