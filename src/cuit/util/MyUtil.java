package cuit.util;

import cuit.model.UserBean;
import cuit.service.CommentService;
import cuit.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esong on 2017/6/7.
 */
public class MyUtil {
    private static UserBean userBean = null;
    public static JSONArray getJsonArrTagsByArrTags(String[] tags) {
        if (tags.length <= 0){
            System.out.println("The hot tags is empty");
            return null;
        }
        JSONArray arrResult = new JSONArray();
        for (String obj:tags){
            arrResult.add(obj);
        }
        return arrResult;
    }
    public static JSONArray getJsonArrTagsByListTags(ArrayList<String> tags) {
        if (tags.size() <= 0){
            System.out.println("The hot tags is empty");
            return null;
        }
        JSONArray arrResult = new JSONArray();
        for (String obj:tags){
            arrResult.add(obj);
        }
        return arrResult;
    }
    public static boolean submitUserInterest(){
        if (userBean == null){
            return false;
        }
        UserService userService = AppUtil.getUserService();
        if (ConstantDeclare.SUCCESS_EDIT_USERHSOWINFOR != userService.editUserDetail(userBean)){
            return false;
        }
        return true;
    }
    public static void setUserInterest(UserBean userInfo, String interest){
        String tmp = userInfo.getUtInterest();
        JSONObject oldInterest = null;
        System.out.println(tmp);
        if (!tmp.equals("") && !tmp.equals("{}")){
             oldInterest = JSONObject.fromObject(tmp).getJSONObject("tag");
        }else {
            oldInterest = new JSONObject();
        }
        for (String str:interest.split(",")){
            if (!oldInterest.containsKey(str)){
                oldInterest.put(str,"20");
            }
        }
        JSONObject jsonObject = JSONObject.fromObject(userInfo.getUtInterest());
        System.out.println(jsonObject.toString());
        jsonObject.put("tag",oldInterest);
        userInfo.setUtInterest(jsonObject.toString());
        userBean = userInfo;
        System.out.println(userBean.toString());
    }
    public static ArrayList<String> getArrayListByArr(String[] tagArr){
        ArrayList<String> tagList = new ArrayList<>();
        for (String tag:tagArr){
            tagList.add(tag);
        }
        return tagList;
    }
    public static String[] getTagArrByJsonArr(JSONArray tagJsonArr){
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < tagJsonArr.size(); i++){
            tmp.append(tagJsonArr.getString(i));
            tmp.append(",");
        }
        return tmp.toString().substring(0,tmp.toString().length()-1).split(",");
    }
    public static JSONArray getMsgJsonArr(CommentService commentService, JSONArray msgArr) {
        JSONArray jsonArray = new JSONArray();
        //封装文章信息
        for (int k = 0; k < msgArr.size(); k++) {
            JSONArray msg = msgArr.getJSONArray(k);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mId", msg.getString(0));
            jsonObject.put("mTime", msg.getString(6));
            String msgTag = msg.getString(4);
            jsonObject.put("mTagData", msgTag.substring(1, msgTag.length() - 1));
            jsonObject.put("mTitle", msg.getString(1));
            jsonObject.put("mIntro", msg.getString(2));
            jsonObject.put("mPic", msg.getString(3));
            jsonObject.put("mLikeCount", msg.getInt(7));
            jsonObject.put("mCommentCount", commentService.selectCommentCountByMId(msg.getInt(0)));
            jsonObject.put("mDislikeCount", msg.getInt(8));
            jsonObject.put("mCollectCount", msg.getInt(9));
            jsonObject.put("mTransmitCount", msg.getInt(10));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
