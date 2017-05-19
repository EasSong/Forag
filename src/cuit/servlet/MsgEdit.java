package cuit.servlet;

import cuit.service.MessageService;
import cuit.util.AppUtil;
import cuit.util.ConstantDeclare;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
