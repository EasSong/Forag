package cuit.util;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

/**
 * Created by Esong on 2017/6/7.
 */
public class MyOracleDialect extends Oracle10gDialect {
    public MyOracleDialect() {
        super();
        registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.DECIMAL, StandardBasicTypes.DOUBLE.getName());
        registerHibernateType(Types.NCLOB, StandardBasicTypes.STRING.getName());


    }

}
