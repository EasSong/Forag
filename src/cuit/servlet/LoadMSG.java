package cuit.servlet;

import cuit.model.MessageBean;
import cuit.service.CommentService;
import cuit.service.MessageService;
import cuit.util.AppUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            //以xxxx-xx-xx（年-月-日）的方式获取当前日期
            Calendar calendar = Calendar.getInstance();
            String nowDate = calendar.get(Calendar.YEAR) + "-"
                    + (calendar.get(Calendar.MONTH) + 1) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH);
            //获取当前热门的标签的id
            ArrayList<Integer> listTag = messageService.selectHotTagByDate(nowDate);
            if (listTag == null) {
                jsonData.put("dataState","notHaveData");
            } else {
                jsonData.put("dataState","haveData");
                JSONArray jsonArrTagName = messageService.selectHotTagNameByTags(listTag);
                jsonData.put("tagName", jsonArrTagName);
                JSONArray jsonArrTag = new JSONArray();
                JSONArray jsonObjMSGs = new JSONArray();
                for (Integer tag : listTag) {
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
                        jsonObject.put("mTime", messageBean.getmTime().toString().substring(0, messageBean.getmTime().toString().length() - 2));
                        jsonObject.put("mTagData", messageService.selectTagListByMId(messageBean.getmId()));
                        jsonObject.put("mTitle", messageBean.getmTitle());
                        jsonObject.put("mIntro", messageBean.getmIntro());
                        jsonObject.put("mHavePic", messageBean.getmHavePic());
                        if (messageBean.getmHavePic() == 1) {
                            jsonObject.put("mPicUri", messageBean.getmPic());
                        }
                        jsonObject.put("mLike_count", messageBean.getmLike_Count());
                        jsonObject.put("mComment_count", commentService.selectCommentCountByMId(messageBean.getmId()));
                        jsonObject.put("mTransmit_count", messageBean.getmTransmit_Count());
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
        response.getWriter().print(jsonData.toString());
//        else if (type.equals("special")){//指示已有用户登录，返回给用户特定的数据
//
//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
