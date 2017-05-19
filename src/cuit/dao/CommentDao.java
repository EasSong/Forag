package cuit.dao;

import cuit.model.CommentBean;

import java.util.ArrayList;

/**
 * Created by Esong on 2017/4/28.
 */
public interface CommentDao {
    int insert(CommentBean commentBean);
    int delete(CommentBean commentBean);
    int update(CommentBean commentBean);
    CommentBean selectById(int cId);
    ArrayList<CommentBean> selectByMId(int mId);
    int selectCommentCountByMId(int mId);
}
