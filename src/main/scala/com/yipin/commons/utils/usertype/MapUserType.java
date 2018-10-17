package com.yipin.commons.utils.usertype;

import com.alibaba.druid.support.json.JSONUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class MapUserType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return Map.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o==o1)return true;
        if (o==null || o1==null) return false;
        Map o2 = (Map) o;
        Map o11 = (Map) o1;
        return o2.equals(o11);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        String string = resultSet.getString(strings[0]);
        if (string==null) return new HashMap<>();
        else{
            try {
                return JSONUtils.parse(string);
            }catch (Exception e){
                HashMap<Object, Object> maps = new HashMap<>();
                return maps.put("",string);
            }
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o==null) ps.setString(i,"");
        else{
            String s = JSONUtils.toJSONString(o);
            ps.setString(i, s);
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
        return o;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return o;
    }
}
