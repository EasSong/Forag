package cuit.dao;

import cuit.model.MessageBean;
import net.sf.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esong on 2017/4/28.
 */
public interface MessageDao {
    int insert(MessageBean messageBean);
    int delete(MessageBean messageBean);
    int update(MessageBean messageBean);
    MessageBean selectById(int mId);
    ArrayList<MessageBean> selectAll();
    ArrayList<MessageBean> selectByTag(String tag);
    int updateLikeCountById(int mId);
    int updateDisLikeCountById(int mId);
    int updateCollectCountById(int mId);
    int updateTransmitCountById(int mId);
    String selectHotTagByDate(String nowDate);
    List selectHotTagNameByTags(ArrayList<Integer> tags);
    List selectTagIdByNames(String[] names);
    List selectTagListByMId(int MId);
}
