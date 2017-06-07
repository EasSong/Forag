package cuit.servlet;

import cuit.model.MessageBean;
import cuit.service.CommentService;
import cuit.service.MessageService;
import cuit.util.AppUtil;
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
        if (type.equals("hot") || type.equals("special")/*测试用*/) {//指示无用户登录，返回给前台最热门的数据
            MessageService messageService = AppUtil.getMessageService();
            CommentService commentService = AppUtil.getCommentService();
            //封装用户登录状态
            jsonData.put("state", "noLogin");
            String changeState = request.getParameter("ChangeState");
            //以xxxx-xx-xx（年-月-日）的方式获取当前日期
            Date date = new Date(System.currentTimeMillis());
            String nowDateStr = date.toString();
            //获取当前热门的标签的id
            ArrayList<String> hotTags = messageService.selectHotTagByDate(nowDateStr);
            ArrayList<String> nowTags = new ArrayList<>();
            if (changeState.equals("changed")){
                String[] nowTempTags = request.getParameter("listTags").split(",");
                for (String temp:nowTempTags){
                    nowTags.add(temp);
                }
            }
            else{
                int count = 0;
                for (String temp:hotTags){
                    nowTags.add(temp);
                    count++;
                    if (count>=6){
                        break;
                    }
                }
            }
            if (hotTags == null || hotTags.size() == 0) {
                jsonData.put("dataState","notHaveData");
            } else {
                jsonData.put("dataState","haveData");
                JSONArray jsonArrTagName = MyUtil.getJsonArrTagsByListTags(hotTags);
                jsonData.put("tagName", jsonArrTagName);
                jsonData.put("nowHotTags", MyUtil.getJsonArrTagsByListTags(nowTags));
                JSONArray jsonArrTag = new JSONArray();
                JSONArray jsonObjMSGs = new JSONArray();
                for (String tag : nowTags) {
                    ArrayList<MessageBean> listBean = messageService.selectByTag(tag);
                    if (listBean == null) {
                        jsonArrTag.add(-1);
                        jsonObjMSGs.add("null");
                        continue;
                    }
                    jsonArrTag.add(tag);
                    JSONObject jsonObjMSG = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    int count = 0;
                    //封装文章信息
                    for (MessageBean messageBean : listBean) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("mId", messageBean.getmId());
                        jsonObject.put("mTime", messageBean.getmPublishTime().toString().substring(0, messageBean.getmPublishTime().toString().length() - 2));
                        jsonObject.put("mTagData", messageService.selectTagListByMId(messageBean.getmId()));
                        jsonObject.put("mTitle", messageBean.getmTitle());
                        jsonObject.put("mIntro", messageBean.getmIntro());
                        jsonObject.put("mHavePic", messageBean.getmPic());
                        if (messageBean.getmPic()!=null) {
                            jsonObject.put("mPicUri", messageBean.getmPic());
                        }
                        jsonObject.put("mLike_count", messageBean.getmLikeCount());
                        jsonObject.put("mComment_count", commentService.selectCommentCountByMId(messageBean.getmId()));
                        jsonObject.put("mTransmit_count", messageBean.getmTransmitCount());
                        jsonArray.add(jsonObject);
                        count++;
                    }
                    jsonObjMSG.put("count", count);
                    jsonObjMSG.put("data", jsonArray);
                    jsonObjMSGs.add(jsonObjMSG);
                }
                jsonData.put("tags", jsonArrTag);
                jsonData.put("msgData", jsonObjMSGs);
            }
        }
        //System.out.print(jsonData.toString());
//        else if (type.equals("special")){//指示已有用户登录，返回给用户特定的数据
//
//        }
        else if (type.equals("changeTags")){

        }
        System.out.println(jsonData.toString());
        response.getWriter().print(jsonData.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
