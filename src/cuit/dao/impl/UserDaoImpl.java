package cuit.dao.impl;

import cuit.dao.UserDao;
import cuit.model.UserBean;
import cuit.util.ConstantDeclare;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class UserDaoImpl implements UserDao{
	private SessionFactory sessionFactory;
	
	//为忽略不同数据库存储过程语法的差异，暂时使用标准sql语句，getdate()为SqlServer中获取当前时间的函数
	//以下语句均为HQL语句，表名必须用别名
    private final String SQL_USER_LOGIN = "from UserBean u where u.utMail=?";
	private final String SQL_QUERY_PASSWORD = "from UserBean u where u.id=?";
	private final String SQL_EDIT_PASSWORD = "update UserBean u set u.utPass=? where u.id=?";
	private final String SQL_GET_USER_SHOW_INFOR = "from UserBean u where u.id=?";
	private final String SQL_UPDATE_USER_SHOW_INFOR = "update UserBean u set u.utName=?,u.utMail=?,u.utAddr=?,u.utPro=?,u.utEdu=?,u.utIntro=?,u.utSkill=? where u.utId=?";
	
	private UserDaoImpl(){
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	注册一个用户
	参数：用户初始化信息 userbean
	返回：操作结果编码
	 */
	@Override
	public int registerUser(UserBean userBean) {
		int code = ConstantDeclare.ERROR_DB_ERROR;
        Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query query = session.createNativeQuery("insert into foragOwner.usertable(utId,utMail,utPass,Utname,Utdate) values(foragOwner.UTID_SEQ.NEXTVAL,?,?,?,sysdate)");
//			query.setParameter(1,"foragOwner.UTID_SEQ.NEXTVAL");
			query.setParameter(1,userBean.getUtMail());
			query.setParameter(2,userBean.getUtPass());
			query.setParameter(3,userBean.getUtName());
			query.executeUpdate();
//            session.save(userBean);
			code = ConstantDeclare.SUCCESS_USER_REGISTER;
		}catch (Exception e){
			code = ConstantDeclare.ERROR_DB_ERROR;
			transaction.rollback();
			e.printStackTrace();
		}
        finally {
		    transaction.commit();
        }
        return code;
	}
    /*
        用户登录
        参数：用户邮箱和密码
        返回：操作结果编码
         */
	@Override
	public int loginUser(String email, String password) {
		int code = ConstantDeclare.ERROR_DB_ERROR;
        Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			Query query = session.createQuery(SQL_USER_LOGIN);
			query.setParameter(0,email);
			List userBeanList = query.list();
//			transaction.commit();
            if (userBeanList.size() == 0){
				code = ConstantDeclare.ERROR_USER_UNEXISTS;
			}
			else{
				UserBean userBean = (UserBean) userBeanList.get(0);
				//UserBean userBean1 = session.get(UserBean.class,1);
				if (session.get(UserBean.class,1) == null) {
                    session.save(userBean);
                }
				if (userBean.getUtPass().equals(password)) {
                    code = userBean.getUtId();
                }
                else{
				    code = ConstantDeclare.ERROR_PASSWORD_LOGIN;
                }
			}
            transaction.commit();
		}catch (Exception e){
			code = ConstantDeclare.ERROR_DB_ERROR;
			//transaction.rollback();
			e.printStackTrace();
		}


		return code;
	}
    /*
        修改用户密码
        参数：用户初始化信息 userbean
        返回：操作结果编码
         */
	@Override
	public int editUserPassword(int id, String oldPassword, String newPassword) {
        int opCode = ConstantDeclare.ERROR_DB_ERROR;
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try{
            Query query = session.createQuery(SQL_QUERY_PASSWORD);
            query.setParameter(0,id);
            List userList = query.list();
            if (userList.size() != 0){
                String password = ((UserBean)userList.get(0)).getUtPass();
                if (password.equals(oldPassword)){
                    Query query1 = session.createQuery(SQL_EDIT_PASSWORD);
                    query1.setParameter(0,newPassword).setParameter(1,id);
                    int resultId = query1.executeUpdate();
                    if (resultId == 1){
                        opCode = ConstantDeclare.SUCCESS_EDIT_PASSWORD;
                    }
                    else {
                        opCode = ConstantDeclare.ERROR_PASSWORD_EDIT;
                    }
                }
            }else{
                opCode = ConstantDeclare.ERROR_USER_UNEXISTS;
            }
        }catch (Exception e){
            transaction.rollback();
            opCode = ConstantDeclare.ERROR_DB_ERROR;
            e.printStackTrace();
        }

		return opCode;
	}
    /*
        得到一个用户信息
        参数：用户ID id
        返回：用户对象
         */
	@Override
	public UserBean getUserDetail(int id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        UserBean userBean = new UserBean();
        try{
            Query query = session.createQuery(SQL_GET_USER_SHOW_INFOR);
            query.setParameter(0,id);
            userBean = (UserBean) query.list().get(0);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
        return userBean;
	}
    /*
        修改用户信息
        参数：用户初始化信息 userbean
        返回：操作结果编码
         */
	@Override
	public int editUserDetail(UserBean userBean) {
        int opCode = ConstantDeclare.ERROR_DB_ERROR;
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.update(userBean);
            opCode = ConstantDeclare.SUCCESS_EDIT_USERHSOWINFOR;
        }catch (Exception e){
            opCode = ConstantDeclare.ERROR_DB_ERROR;
            transaction.rollback();
            e.printStackTrace();
        } finally {
            transaction.commit();
        }

        return opCode;
	}
}
