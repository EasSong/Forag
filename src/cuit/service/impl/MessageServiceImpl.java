package cuit.service.impl;

import cuit.dao.MessageDao;
import cuit.model.MessageBean;
import cuit.service.MessageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esong on 2017/4/28.
 */
public class MessageServiceImpl implements MessageService {
    private MessageDao messageDao;

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public int insert(MessageBean messageBean) {
        return messageDao.insert(messageBean);
    }

    @Override
    public int delete(MessageBean messageBean) {
        return messageDao.delete(messageBean);
    }

    @Override
    public int update(MessageBean messageBean) {
        return messageDao.update(messageBean);
    }

    @Override
    public MessageBean selectById(int mId) {
        return messageDao.selectById(mId);
    }

    @Override
    public ArrayList<MessageBean> selectAll() {
        return messageDao.selectAll();
    }

    @Override
    public ArrayList<MessageBean> selectByTag(int tag) {
        return messageDao.selectByTag(tag);
    }

    @Override
    public int updateLikeCountById(int mId) {
        return messageDao.updateLikeCountById(mId);
    }

    @Override
    public int updateCollectCountById(int mId) {
        return messageDao.updateCollectCountById(mId);
    }

    @Override
    public int updateTransmitCountById(int mId) {
        return messageDao.updateTransmitCountById(mId);
    }

    @Override
    public ArrayList<Integer> selectHotTagByDate(String nowDate) {
        String[] result = messageDao.selectHotTagByDate(nowDate).split(",");
        if(result.length <= 0 || result == null){
            return null;
        }
        ArrayList<Integer> iResultArray = new ArrayList<>();
        for (String temp:result){
            iResultArray.add(Integer.parseInt(temp));
        }
        return iResultArray;
    }

    @Override
    public JSONArray selectHotTagNameByTags(ArrayList<Integer> tags) {
        List list = messageDao.selectHotTagNameByTags(tags);
        if (list.size() <= 0){
            System.out.println("The hot tags is empty");
            return null;
        }
       JSONArray arrResult = new JSONArray();
        for (Object obj:list){
            String temp = String.valueOf(obj);
            arrResult.add(temp);
        }
        return arrResult;
    }

    @Override
    public JSONObject selectTagListByMId(int MId) {
        JSONObject tagData = new JSONObject();
        JSONArray tagId = new JSONArray();
        JSONArray tagName = new JSONArray();
        JSONArray tagWeight = new JSONArray();
        List list = messageDao.selectTagListByMId(MId);
        for (Object obj:list){
            Object[] row = (Object[])obj;
            tagId.add(String.valueOf(row[0]));
            tagName.add(String.valueOf(row[1]));
            tagWeight.add(String.valueOf(row[2]));
        }
        tagData.put("tagId",tagId);
        tagData.put("tagName",tagName);
        tagData.put("tagWeight",tagWeight);
        return tagData;
    }
}
