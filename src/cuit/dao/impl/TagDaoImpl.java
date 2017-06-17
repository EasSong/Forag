package cuit.dao.impl;

import cuit.dao.TagDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Esong on 2017/6/17.
 */
public class TagDaoImpl implements TagDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List getTagListByLikeName(String likeName) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String sqlStr = "select tName from foragOwner.TagMsg where lower(tName) like '%"+likeName+"%'";
        Query sqlQuery = session.createNativeQuery(sqlStr);
        //sqlQuery.setParameter(1,likeName);
        List tagList = sqlQuery.getResultList();
        session.getTransaction().commit();
        return tagList;
    }

    @Override
    public List getMsgIdListByLikeName(String likeName) {
        return null;
    }
}
