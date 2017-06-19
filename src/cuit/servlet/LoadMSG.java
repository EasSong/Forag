package cuit.servlet;

import cuit.model.MessageBean;
import cuit.model.UserBean;
import cuit.service.CommentService;
import cuit.service.MessageService;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Esong on 2017/5/2.
 */
public class LoadMSG extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String type = request.getParameter("type");
        JSONObject jsonData = new JSONObject();
        String changeState = request.getParameter("ChangeState");
        //获取当前热门的标签的id
        JSONObject tagJson = MySocket.getHotTags(0, 10);
        request.getSession().setAttribute("hotTag", tagJson);
        String[] hotTags = MyUtil.getTagArrByJsonArr(tagJson.getJSONArray("tags"));
        ArrayList<String> nowTags = new ArrayList<>();
        String hot = request.getParameter("hot");
        String recommend = request.getParameter("user");
        if (type.equals("hot")) {//指示无用户登录，返回给前台最热门的数据
            //封装用户登录状态
            jsonData.put("state", "noLogin");
            nowTags.add(hot);
        } else if (type.equals("special")) {//指示已有用户登录，返回给用户特定的数据
            jsonData.put("state", "isLogin");
            nowTags.add(recommend);
            nowTags.add(hot);
        }
        if (changeState.equals("changed")) {
            nowTags = MyUtil.getArrayListByArr(request.getParameter("listTags").split(","));
        } else {
            int count = nowTags.size();
            if (hotTags.length != 0) {
                for (String temp : hotTags) {
                    nowTags.add(temp);
                    if (count++ > 8) {
                        break;
                    }
                }
            }
        }
        if (hotTags.length == 0) {
            jsonData.put("dataState", "notHaveData");
        } else {
            jsonData.put("dataState", "haveData");
            JSONArray jsonArrTagName = MyUtil.getJsonArrTagsByArrTags(hotTags);
            jsonData.put("tagName", jsonArrTagName);
            jsonData.put("nowHotTags", MyUtil.getJsonArrTagsByListTags(nowTags));
            JSONArray jsonArrTag = new JSONArray();
            JSONArray jsonObjMSGs = new JSONArray();
            CommentService commentService = AppUtil.getCommentService();
            String oldTags = (String) request.getSession().getAttribute("oldTags");
            if (oldTags != null && oldTags.equals(nowTags.toString())) {
                jsonData = (JSONObject) request.getSession().getAttribute("msgInfo");
            } else {
                for (String tag : nowTags) {
                    JSONArray msgArr = new JSONArray();
                    String ip = request.getRemoteAddr();
                    if (tag.equals(hot)) {
                        msgArr = MySocket.getHotMsgIntro(ip, 8, 0);
                    } else if (tag.equals(recommend)) {
                        msgArr = MySocket.getUserInterestMsg((UserBean)request.getSession().getAttribute("userShowInfor"),50,100);
                    } else {
                        msgArr = MySocket.getMsgIntroByTag(tag, ip, 8, 0);
                    }
                    if (msgArr == null) {
                        jsonArrTag.add(-1);
                        jsonObjMSGs.add("null");
                        continue;
                    }
                    jsonArrTag.add(tag);
                    JSONObject jsonObjMSG = new JSONObject();
                    JSONArray jsonArray = MyUtil.getMsgJsonArr(commentService, msgArr);
                    jsonObjMSG.put("count", jsonArray.size());
                    jsonObjMSG.put("data", jsonArray);
                    jsonObjMSGs.add(jsonObjMSG);
                }
                jsonData.put("tags", jsonArrTag);
                jsonData.put("msgData", jsonObjMSGs);
                request.getSession().setAttribute("oldTags",nowTags.toString());
                request.getSession().setAttribute("msgInfo",jsonData);
            }
        }
        //System.out.print(jsonData.toString());
        System.out.println(jsonData.toString());
        response.getWriter().print(jsonData.toString());
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
