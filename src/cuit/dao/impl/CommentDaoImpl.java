package cuit.dao.impl;

import cuit.dao.CommentDao;
import cuit.model.CommentBean;
import cuit.util.ConstantDeclare;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esong on 2017/4/28.
 */
public class CommentDaoImpl implements CommentDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*
    插入CommentBean一个对象到Hibernate缓存中，并且提交事务，持久化到数据库中
     */
    @Override
    public int insert(CommentBean commentBean) {
        int state = ConstantDeclare.ERROR_COMMENT_INSERT;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.save(commentBean);
            session.getTransaction().commit();
            state = ConstantDeclare.SUCCESS_COMMENT_INSERT;
        }catch (HibernateException e){
            session.getTransaction().rollback();
            state = ConstantDeclare.ERROR_COMMENT_INSERT;
            e.printStackTrace();
        }
        return state;
    }

    /*
    删除一个CommentBean对象
     */
    @Override
    public int delete(CommentBean commentBean) {
        return 0;
    }
    /*
    更新一个CommentBean对象
     */
    @Override
    public int update(CommentBean commentBean) {
        return 0;
    }
    /*
    根据评论ID查询评论的详细信息
    返回CommentBean
     */
    @Override
    public CommentBean selectById(int cId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        //使用自定义的HQL语句时，表名一定要用别名，并且字段一定要用别名来引用
        Query query = session.createQuery("from CommentBean c where c.cId=?");
        query.setParameter(0,cId);
        List listBean = query.list();
        session.getTransaction().commit();
        if (listBean.size() <= 0) {
            return null;
        }
        return (CommentBean)listBean.get(0);
    }
    /*
    根据文章ID查询评论的所有信息
    返回一个CommentBean的集合
     */
    @Override
    public ArrayList<CommentBean> selectByMId(int mId) {
        ArrayList<CommentBean> listCommentBean = new ArrayList<>();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from CommentBean c where c.mId=?");
        query.setParameter(0,mId);
        List listBean = query.list();
        session.getTransaction().commit();
        if (listBean.size() == 0){
            return listCommentBean;
        }
        for (int i = 0; i < listBean.size(); i++){
            listCommentBean.add((CommentBean) listBean.get(i));
        }

        return listCommentBean;
    }

    @Override
    public int selectCommentCountByMId(int mId) {
        int count = 0;

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(c.cId) as count from CommentBean c where c.mId=?");
        query.setParameter(0,mId);
        Object object = query.uniqueResult();
        session.getTransaction().commit();
        count = ((Number)object).intValue();

        return count;
    }
}
