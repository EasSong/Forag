package cuit.service;

import cuit.model.MessageBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Esong on 2017/4/28.
 */
public interface MessageService {
    int insert(MessageBean messageBean);
    int delete(MessageBean messageBean);
    int update(MessageBean messageBean);
    MessageBean selectById(int mId);
    ArrayList<MessageBean> selectAll();
    ArrayList<MessageBean> selectByTag(int tag);
    int updateLikeCountById(int mId);
    int updateCollectCountById(int mId);
    int updateTransmitCountById(int mId);
    ArrayList<Integer> selectHotTagByDate(String nowDate);
    JSONArray selectHotTagNameByTags(ArrayList<Integer> tags);
    JSONObject selectTagListByMId(int MId);
}
