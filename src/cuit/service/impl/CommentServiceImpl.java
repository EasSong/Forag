package cuit.service.impl;

import cuit.dao.CommentDao;
import cuit.model.CommentBean;
import cuit.service.CommentService;

import java.util.ArrayList;

/**
 * Created by Esong on 2017/4/28.
 */
public class CommentServiceImpl implements CommentService{
    private CommentDao commentDao;

    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public int insert(CommentBean commentBean) {
        return commentDao.insert(commentBean);
    }

    @Override
    public int delete(CommentBean commentBean) {
        return commentDao.delete(commentBean);
    }

    @Override
    public int update(CommentBean commentBean) {
        return commentDao.update(commentBean);
    }

    @Override
    public CommentBean selectById(int cId) {
        return commentDao.selectById(cId);
    }

    @Override
    public ArrayList<CommentBean> selectByMId(int mId) {
        return commentDao.selectByMId(mId);
    }

    @Override
    public int selectCommentCountByMId(int mId) {
        return commentDao.selectCommentCountByMId(mId);
    }
}
