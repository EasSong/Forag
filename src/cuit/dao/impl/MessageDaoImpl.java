package cuit.dao.impl;

import cuit.dao.MessageDao;
import cuit.model.MessageBean;
import cuit.util.ConstantDeclare;
import javafx.beans.binding.ObjectExpression;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esong on 2017/4/28.
 */
public class MessageDaoImpl implements MessageDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    /*
    向Hibernate缓存中插入一个MessageBean对象，并提交到数据中进行持久化
     */
    @Override
    public int insert(MessageBean messageBean) {
        return 0;
    }
    /*
    从Hibernate缓存中删除一个MessageBean对象，并同步到数据库
     */
    @Override
    public int delete(MessageBean messageBean) {
        return 0;
    }
    /*
    更新一个Hibernate缓存中的对象，并同步到数据库
     */
    @Override
    public int update(MessageBean messageBean) {
        return 0;
    }
    /*
    根据文章ID(mId)查询一篇文章的详细信息
    返回一个MessageBean
     */
    @Override
    public MessageBean selectById(int mId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from MessageBean m where m.mId=?");
        query.setParameter(0,mId);
        List list = query.list();
        if (list.size() == 0){
            return null;
        }
        session.getTransaction().commit();
        return (MessageBean) list.get(0);
    }
    /*
    查询数据库中所有的文章信息
    返回MessageBean的集合（List）
     */
    @Override
    public ArrayList<MessageBean> selectAll() {
        ArrayList<MessageBean> listMessage = new ArrayList<>();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from MessageBean m");
        List list = query.list();
        if (list.size() == 0){
            return null;
        }
        for (int i = 0; i < list.size(); i++){
            listMessage.add((MessageBean) list.get(i));
        }
        session.getTransaction().commit();

        return listMessage;
    }
    /*
    根据标签（Tag）查询文章的信息
    返回所有符合tag的所有文章的集合
     */
    @Override
    public ArrayList<MessageBean> selectByTag(String tag) {
        ArrayList<MessageBean> listMessage = new ArrayList<>();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query sqlQueryMId = session.createNativeQuery("select mid,mtweight,tname from foragOwner.msgtotagtab where tname='"+tag+"' order by mtweight desc");
        List midList = sqlQueryMId.getResultList();
        String hqlQueryString = "from MessageBean m where m.mId in (";
        if (midList.size() <= 0){
            session.getTransaction().commit();
            return null;
        }
        for (Object temp:midList){
            Object[] cell= (Object[])temp;
            hqlQueryString += cell[0]+",";
        }
        hqlQueryString = hqlQueryString.substring(0,hqlQueryString.length()-1);
        hqlQueryString += ")";
        //使用自定义的HQL语句时，表名一定要用别名，并且字段一定要用别名来引用
        Query hqlQuery = session.createQuery(hqlQueryString);
        List list = hqlQuery.getResultList();
        if (list.size() == 0){
            return null;
        }
        for (int i = 0; i < list.size(); i++){
            listMessage.add((MessageBean) list.get(i));
        }
        session.getTransaction().commit();

        return listMessage;
    }
    /*
    更新一个文章的点赞数
    参数：文章ID  mId
    返回：更新结果编码
     */
    @Override
    public int updateLikeCountById(int mId) {
        int stateCode = ConstantDeclare.ERROR_DB_ERROR;

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update MessageBean m set m.mLikeCount=m.mLikeCount+1 where m.mId=?");
        query.setParameter(0,mId);
        int state = query.executeUpdate();
        if (state <= 0){
            stateCode = ConstantDeclare.ERROR_UPDATE_LIKE;
        }
        session.getTransaction().commit();

        return stateCode;
    }
    /*
        更新一个文章的不喜欢数
        参数：文章ID  mId
        返回：更新结果编码
         */
    @Override
    public int updateDisLikeCountById(int mId) {
        int stateCode = ConstantDeclare.ERROR_DB_ERROR;

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update MessageBean m set m.mDislikeCount=m.mDislikeCount+1 where m.mId=?");
        query.setParameter(0,mId);
        int state = query.executeUpdate();
        if (state <= 0){
            stateCode = ConstantDeclare.ERROR_UPDATE_LIKE;
        }
        session.getTransaction().commit();

        return stateCode;
    }

    /*
        更新一个文章的收藏数
        参数：文章ID  mId
        返回：更新结果编码
         */
    @Override
    public int updateCollectCountById(int mId) {
        int stateCode = ConstantDeclare.ERROR_DB_ERROR;

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update MessageBean m set m.mCollectCount=m.mCollectCount+1 where m.mId=?");
        query.setParameter(0,mId);
        int state = query.executeUpdate();
        if (state <= 0){
            stateCode = ConstantDeclare.ERROR_UPDATE_COLLECT;
        }
        session.getTransaction().commit();

        return stateCode;
    }
    /*
        更新一个文章的转发数
        参数：文章ID  mId
        返回：更新结果编码
         */
    @Override
    public int updateTransmitCountById(int mId) {
        int stateCode = ConstantDeclare.ERROR_DB_ERROR;

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update MessageBean m set m.mTransmitCount=m.mTransmitCount+1 where m.mId=?");
        query.setParameter(0,mId);
        int state = query.executeUpdate();
        if (state <= 0){
            stateCode = ConstantDeclare.ERROR_UPDATE_TRANSMIT;
        }
        session.getTransaction().commit();

        return stateCode;
    }

    @Override
    public String selectHotTagByDate(String nowDate) {
        String hotTagData = "";

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createNativeQuery("select * from foragOwner.hottagtab where to_char(htdate,'yyyy-mm-dd') = '"+nowDate+"'");
        List list = query.getResultList();
        if (list.size() > 0){
            Object[] cell = (Object[]) list.get(0);
            hotTagData = String.valueOf(cell[2]);
        }
        session.getTransaction().commit();

        return hotTagData;
    }

    @Override
    public List selectHotTagNameByTags(ArrayList<Integer> tags) {
        if (tags.size() <= 0){
            return null;
        }
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String sqlQueryStr = "select tname from tag_tab where tid in (";
        for (Integer tag:tags){
            sqlQueryStr += tag+",";
        }
        sqlQueryStr = sqlQueryStr.substring(0,sqlQueryStr.length()-1);
        sqlQueryStr += ")";
        Query sqlQuery = session.createNativeQuery(sqlQueryStr);
        List list = sqlQuery.getResultList();
        session.getTransaction().commit();
        return list;
    }

    @Override
    public List selectTagIdByNames(String[] names) {
        if (names.length <= 0){
            return null;
        }
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String sqlQueryStr = "select tid from tag_tab where tname in ('";
        for (String name:names){
            sqlQueryStr += name+"','";
        }
        sqlQueryStr = sqlQueryStr.substring(0,sqlQueryStr.length()-2);
        sqlQueryStr += ")";
        Query sqlQuery = session.createNativeQuery(sqlQueryStr);
        List list = sqlQuery.getResultList();
        session.getTransaction().commit();
        return list;
    }

    @Override
    public List selectTagListByMId(int MId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String sqlQueryStr = "select tname,mtweight from foragOwner.msgtotagtab where mid="
                +MId+" order by mtweight desc";
        Query sqlQuery = session.createNativeQuery(sqlQueryStr);
        List list = sqlQuery.getResultList();
        session.getTransaction().commit();
        return list;
    }
}
