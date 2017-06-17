package cuit.servlet;

import cuit.log.UserLog;
import cuit.log.impl.UserLogImpl;
import cuit.model.CommentBean;
import cuit.model.LogInfo;
import cuit.model.MessageBean;
import cuit.model.UserBean;
import cuit.service.CommentService;
import cuit.service.MessageService;
import cuit.service.UserService;
import cuit.util.AppUtil;
import cuit.util.MySocket;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Esong on 2017/5/2.
 */
public class LoadArticle extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//同步request的编码
        response.setCharacterEncoding("UTF-8");//同步response的编码
        HttpSession httpSession = request.getSession();
        String mid = request.getParameter("mId");
        MessageService messageService = AppUtil.getMessageService();
        CommentService commentService = AppUtil.getCommentService();
        UserService userService = AppUtil.getUserService();
        MessageBean messageBean = messageService.selectById(Integer.parseInt(mid));
        ArrayList<CommentBean> listCommentBean = commentService.selectByMId(Integer.parseInt(mid));
        //初始化封装一部数据的JSON对象
        JSONObject jsonData = new JSONObject();
        //封装文章(Message)信息
        JSONObject jsonMSG = new JSONObject();
        jsonMSG.put("mId",messageBean.getmId());
        jsonMSG.put("mTime",messageBean.getmPublishTime().toString());
        jsonMSG.put("mSource",messageBean.getmSource());
        jsonMSG.put("mTags",messageBean.getmTags());
        jsonMSG.put("mTitle",messageBean.getmTitle());
        jsonMSG.put("mIntro",messageBean.getmIntro());
        MySocket mySocket = new MySocket();
        JSONObject contextData = mySocket.runSocket("{\"name\":\"generatePage\",\"params\":{\"pageid\":\""+messageBean.getmId()+"\",\"template\":\"articleTemplate.json\", \"userid\":\"123\"}}\r\n");
        jsonMSG.put("mContext",contextData.get("result"));
        jsonMSG.put("mLike_count",messageBean.getmLikeCount());
        jsonMSG.put("mCollect_count",messageBean.getmCollectCount());
        jsonMSG.put("mTransmit_count",messageBean.getmTransmitCount());
        jsonData.put("MSG",jsonMSG);
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
                jsonObject.put("cTime",commentBean.getcTime().toString());
                UserBean userBean1 = userService.getUserDetail(commentBean.getuId());
                jsonObject.put("userName",userBean1.getUtName());
                jsonObject.put("cRoot_Id",commentBean.getcRoot_Id());
                jsonObject.put("cCommentText",commentBean.getcCommentText());
                if (commentBean.getcParent_Id() != -1) {
                    CommentBean tempCommentBean = commentService.selectById(commentBean.getcParent_Id());
                    UserBean userBean2 = userService.getUserDetail(tempCommentBean.getuId());
                    nodeCommentCount++;
                    jsonObject.put("comment_username", userBean2.getUtName());
                    jsonArrayNodeCOM.add(jsonObject);
                }
                else{
                    jsonObject.put("comment_username", "unKnown");
                    rootCommentCount++;
                    jsonArrayRootCOM.add(jsonObject);
                }
            }
            //根据评论类型来封装数据
            jsonData.put("rootCommentCount",rootCommentCount);
            jsonData.put("nodeCommentCount",nodeCommentCount);
            jsonData.put("commentRootData",jsonArrayRootCOM);
            jsonData.put("commentNodeData",jsonArrayNodeCOM);
        }
        else{
            jsonData.put("commentData","notComment");
        }
        //判断是否有用户登录，有则封装详细信息，无则封装一个字符串(notLogin)
        if (httpSession.getAttribute("isLogin") == null || !(Boolean) httpSession.getAttribute("isLogin")){
           jsonData.put("state","notLogin");
           jsonData.put("userInfo","notLogin");
        }
        else{
            jsonData.put("state","isLogin");
            UserBean userBean = (UserBean) httpSession.getAttribute("userShowInfor");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uId",userBean.getUtId());
            jsonObject.put("uName",userBean.getUtName());
            jsonObject.put("uEmail",userBean.getUtMail());
            jsonObject.put("uPic",userBean.getuPic());
            jsonObject.put("uProfession",userBean.getUtPro());
            jsonObject.put("uDate",userBean.getUtDate().toString().substring(0,userBean.getUtDate().toString().length()-2));
            jsonData.put("userInfo",jsonObject);
            UserLog userLog = new UserLogImpl();
            Date date = new Date(System.currentTimeMillis());
            LogInfo logInfo = new LogInfo(date.toString(),"browse","browse msg",mid,messageBean.getmTags(),"null");
            userLog.writeUserLog(String.valueOf(userBean.getUtId()),logInfo);
        }
        System.out.println(jsonData.toString());
        response.getWriter().print(jsonData.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
