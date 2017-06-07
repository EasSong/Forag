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
        MessageService messageService = AppUtil.getMessageService();
        if (type.equals("like")){
            String mId = request.getParameter("mId");
            int stateCode = messageService.updateLikeCountById(Integer.parseInt(mId));
            if (stateCode == ConstantDeclare.ERROR_UPDATE_LIKE){
                response.getWriter().print("errorUpdateLikeCount");
            }else{
                response.getWriter().print("successUpdateLikeCount");
                UserLog userLog = new UserLogImpl();
                String uId = request.getParameter("uId");
                if (Integer.parseInt(uId) > -1){
                    Date date = new Date(System.currentTimeMillis());
                    userLog.writeUserLog(uId,new LogInfo(date.toString(),uId+" like msg "+mId ,uId+" like "+mId));
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
