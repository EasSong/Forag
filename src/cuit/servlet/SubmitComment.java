package cuit.servlet;

import cuit.log.UserLog;
import cuit.log.impl.UserLogImpl;
import cuit.model.CommentBean;
import cuit.model.LogInfo;
import cuit.model.UserBean;
import cuit.service.CommentService;
import cuit.service.UserService;
import cuit.util.AppUtil;
import cuit.util.ConstantDeclare;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Esong on 2017/5/2.
 */
public class SubmitComment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String cParent_id = request.getParameter("cParent_id");
        String context = request.getParameter("context");
        String mId = request.getParameter("mId");
        String cRoot_Id = request.getParameter("cRoot_Id");
        HttpSession session = request.getSession();
        UserBean userBean = (UserBean) session.getAttribute("userShowInfor");
        String uId = String.valueOf(userBean.getUtId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CommentBean commentBeanData = new CommentBean();
        commentBeanData.setcParent_Id(Integer.parseInt(cParent_id));
        commentBeanData.setcRoot_Id(Integer.parseInt(cRoot_Id));
        commentBeanData.setcCommentText(context);
        commentBeanData.setmId(Integer.parseInt(mId));
        commentBeanData.setuId(Integer.parseInt(uId));
        commentBeanData.setcTime(timestamp);
        CommentService commentService = AppUtil.getCommentService();
        UserService userService = AppUtil.getUserService();
        int state = commentService.insert(commentBeanData);
        ArrayList<CommentBean> listCommentBean = commentService.selectByMId(Integer.parseInt(mId));
        PrintWriter out = response.getWriter();
        JSONObject jsonData = new JSONObject();
        System.out.println(context);
        if (state == ConstantDeclare.ERROR_COMMENT_INSERT){
            jsonData.put("state","errorComment");
            jsonData.put("commentData","errorComment");
        }
        else if (state == ConstantDeclare.SUCCESS_COMMENT_INSERT){
            jsonData.put("state","successComment");
            UserLog userLog = new UserLogImpl();
            Date date = new Date(System.currentTimeMillis());
            String objStr = "message " + mId;
            if (!cParent_id.equals("-1")){
                objStr = "otherComment " + cParent_id;
            }
            LogInfo logInfo = new LogInfo(date.toString(),userBean.getUtId()+" comment "+objStr ,context);
            userLog.writeUserLog(String.valueOf(userBean.getUtId()),logInfo);
            //封装评论信息，判断是否有，无则封装标记字符串(notComment)
            if (listCommentBean.size() > 0){
                JSONArray jsonArrayRootCOM = new JSONArray();
                JSONArray jsonArrayNodeCOM = new JSONArray();
                int rootCommentCount = 0;
                int nodeCommentCount = 0;
                for (CommentBean commentBean:listCommentBean){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("cId",commentBean.getcId());
                    jsonObject.put("cParent_Id",commentBean.getcParent_Id());
                    jsonObject.put("cTime",commentBean.getcTime().toString().substring(0,commentBean.getcTime().toString().length()-2));
                    UserBean userBean1 = userService.getUserDetail(commentBean.getuId());
                    jsonObject.put("userName",userBean1.getUtName());
                    jsonObject.put("cRoot_Id",commentBean.getcRoot_Id());
                    jsonObject.put("cCommentText",commentBean.getcCommentText());
                    if (commentBean.getcParent_Id() == -1) {
                        jsonObject.put("comment_username", "unKnown");
                        rootCommentCount++;
                        jsonArrayRootCOM.add(jsonObject);
                    }
                    else{
                        CommentBean tempCommentBean = commentService.selectById(commentBean.getcParent_Id());
                        UserBean userBean2 = userService.getUserDetail(tempCommentBean.getuId());
                        jsonObject.put("comment_username", userBean2.getUtName());
                        nodeCommentCount++;
                        jsonArrayNodeCOM.add(jsonObject);
                    }
                }
                jsonData.put("rootCommentCount",rootCommentCount);
                jsonData.put("nodeCommentCount",nodeCommentCount);
                jsonData.put("commentRootData",jsonArrayRootCOM);
                jsonData.put("commentNodeData",jsonArrayNodeCOM);
            }
            else{
                jsonData.put("commentData","notComment");
            }
        }
        out.print(jsonData.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
