package cuit.util;

import cuit.log.impl.UserLogImpl;
import cuit.model.UserBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * Created by Esong on 2017/6/7.
 */
public class MySocket {
    public MySocket(){}
    private static JSONObject runSocket(String param) {
        JSONObject jsonData = new JSONObject();
        Socket socket = null;
        try {//10.18.48.248  192.168.43.170 192.168.1.116
            socket = new Socket("192.168.1.116",9999);
            OutputStream out = socket.getOutputStream();
//            OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
//            PrintWriter pw = new PrintWriter(osw);
            System.out.println("param: "+param);
            out.write(param.getBytes("UTF-8"));
            out.flush();
            socket.shutdownOutput();
            InputStream in = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while((info=br.readLine()) != null){
                System.out.println("Service Information:"+info);
                jsonData = JSONObject.fromObject(info);
            }
            br.close();
            in.close();
            out.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public static JSONObject getHotTags(int offset, int len){
        String param = "{\"name\":\"getHotTag\",\"params\":{\"len\":\""+len+"\",\"offset\":\""+offset+"\"}}";
        JSONObject jsonObject = runSocket(param);
        if (jsonObject.getString("state").equals("success")) {
            JSONObject tagJson = new JSONObject();
            tagJson.put("offset", offset);
            tagJson.put("length", len);
            tagJson.put("tags",jsonObject.getString("result"));
            return tagJson;
        }
        else{
            return null;
        }
    }
    public static JSONObject getMsgDetail(String mId,String uId){
        String param = "{\"name\":\"generatePage\",\"params\":{\"pageid\":\""+mId+"\",\"template\":\"articleTemplate.json\", \"userid\":\""+uId+"\"}}";
        JSONObject jsonObject = runSocket(param);
        if (jsonObject.getString("state").equals("success")){
            return jsonObject;
        }else {
            return null;
        }
    }

    public static JSONObject getUserInterestMsg(UserBean userBean, int len, int offset,JSONArray history){
        JSONObject logJson = new UserLogImpl().readUserLogForSocket(String.valueOf(userBean.getUtId()),0,50,100);
        String jsonStr = userBean.toJSONString();
        String param = "{\"name\":\"getUserInterestPage\",\"params\":{\"user\":"+JSONObject.fromObject(jsonStr)+",\"log\":"+logJson.toString()+",\"len\":\""+len+"\",\"history\":"+(history == null?"[]":history.toString())+"}}";
        JSONObject jsonObject = runSocket(param);
        if (jsonObject.getString("state").equals("success")){
            return jsonObject.getJSONObject("result");
        }else {
            return null;
        }
    }
    public static JSONArray getMsgIntroByTag(String tagName, String uId, int len, int offset){
        String param = "{\"name\":\"getTagArticle\",\"params\":{\"type\":\"tag\",\"name\":\""+tagName+"\",\"len\":\""+len+"\", \"offset\":\""+offset+"\", \"userid\":\""+uId+"\"}}";
        JSONObject jsonObject = runSocket(param);
        if (jsonObject.getString("state").equals("success")){
            return jsonObject.getJSONArray("result");
        }else {
            return null;
        }
    }
    public static JSONArray getHotMsgIntro(String uId, int len, int offset){
        String param = "{\"name\":\"getHotArticle\",\"params\":{\"len\":\""+len+"\", \"userid\":\""+uId+"\", \"offset\":\""+offset+"\"}}";
        JSONObject jsonObject = runSocket(param);
        if (jsonObject.getString("state").equals("success")){
            return jsonObject.getJSONArray("result");
        }else {
            return null;
        }
    }
}
