package cuit.servlet;

import cuit.model.UserBean;
import cuit.util.AppUtil;
import cuit.util.MySocket;
import cuit.util.MyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Esong on 2017/6/19.
 */
public class LoadMoreMsg extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String tagName = request.getParameter("tagName");
        String offset = request.getParameter("offset");
        String len = request.getParameter("len");
        String uId = request.getParameter("uId");
        JSONArray msgArr = new JSONArray();
        if (tagName.equals("»»√≈")){
            msgArr = MySocket.getHotMsgIntro(uId,Integer.parseInt(len),Integer.parseInt(offset));
        }else if(tagName.equals("Õ∆ºˆ")) {
            if (!uId.equals("-1")) {
                JSONArray userHistory = (JSONArray)request.getSession().getAttribute("userHistory");
                JSONObject jsonObject = MySocket.getUserInterestMsg((UserBean)request.getSession().getAttribute("userShowInfor"),15,Integer.parseInt(offset),userHistory);
                msgArr = jsonObject.getJSONArray("msg");
                if (userHistory == null){
                    userHistory = jsonObject.getJSONArray("id");
                    //userHistory.addAll();
                }else {
                    userHistory.addAll(jsonObject.getJSONArray("id"));
                }
                System.out.println("Info: User history"+userHistory.toString());
                request.getSession().setAttribute("userHistory",userHistory);
            }
        }else{
            msgArr = MySocket.getMsgIntroByTag(tagName,uId,Integer.parseInt(len),Integer.parseInt(offset));
        }
        JSONArray jsonArray = MyUtil.getMsgJsonArr(AppUtil.getCommentService(),msgArr);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",jsonArray.size());
        jsonObject.put("data",jsonArray);
        jsonObject.put("tagName",tagName);
        jsonObject.put("offset",offset);
        jsonObject.put("len",len);
        response.getWriter().print(jsonObject);
    }
}
