package cuit.util;

import cuit.model.UserBean;
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
             oldInterest = JSONObject.fromObject(tmp);
        }else {
            oldInterest = new JSONObject();
        }
        for (String str:interest.split(",")){
            if (!oldInterest.containsKey(str)){
                oldInterest.put(str,"20");
            }
        }
        userInfo.setUtInterest(oldInterest.toString());
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
}
