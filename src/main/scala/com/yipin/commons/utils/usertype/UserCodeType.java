package com.yipin.commons.utils.usertype;

import com.yipin.commons.utils.Status;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;




public class UserCodeType implements UserType, ParameterizedType {
    private static final String property = "class";

    private Class<?> enumClass;


    @Override
    public void setParameterValues(Properties properties) {
        try {
            enumClass = Class.forName(properties.getProperty(property));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.INTEGER};
    }

    @Override
    public Class returnedClass() {
        return Status.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o1==o) return true;
        if (o1==null || o ==null) return false;
        if (o.getClass()!= o1.getClass()) return false;
        Status eo = (Status) o;
        Status eo1 = (Status) o1;
        return eo.getValue().equals(eo1.getValue());
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        int anInt = resultSet.getInt(strings[0]);
        if (resultSet.wasNull()){
            return null;
        }else if (Status.class.isAssignableFrom(enumClass)) {
                for (Object obj:enumClass.getEnumConstants()){
                    if (Objects.equals(anInt, ((Status)obj).getValue())){
                        return obj;
                    }
                }
        }
        throw new IllegalArgumentException("can not find value "+anInt+ " specific enum class for"+enumClass.getName());
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o==null) {
            preparedStatement.setInt(i,0);
        }else if (o instanceof Integer){
            preparedStatement.setInt(i, (Integer) o);
        }else {
            Status o1 = (Status) o;
            preparedStatement.setInt(i, o1.getValue());
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable)o;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return o;
    }
}
