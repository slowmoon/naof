package com.yipin.commons.utils.usertype;

import com.fangjs.commons.core.valueobject.SupportElements;
import com.fangjs.commons.core.valueobject.SupportElementsFactory;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;

public class SupportElementType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return SupportElementType.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o==o1) return true;
        if (o==null || o1==null) return false;
        SupportElementType o2 = (SupportElementType) o;
        SupportElementType o3 = (SupportElementType) o1;
        return o2.equals(o3);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        String string = resultSet.getString(strings[0]);
        return SupportElementsFactory.getInstance().makeByExpression(string);
    }
    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        preparedStatement.setString(i,this.getValue(o));
    }

    private String getValue(Object o){
        return (o instanceof SupportElements) ?SupportElementsFactory.getInstance().getExpression((SupportElements) o):"";
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
        return o;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return o;
    }
}
