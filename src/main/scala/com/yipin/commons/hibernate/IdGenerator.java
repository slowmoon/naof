package com.yipin.commons.hibernate;


import com.alibaba.druid.util.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class IdGenerator implements IdentifierGenerator, Configurable {
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    private static final String TABLE = "table";
    private static final String COLUMN = "column";

    private static final String SCHEMA = "schema";

    private String sql;

    private String next;



    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        String table = properties.getProperty(TABLE);
        if (table==null) table = properties.getProperty(PersistentIdentifierGenerator.TABLE);
        String column = properties.getProperty(COLUMN);
        if (column==null) column =properties.getProperty(PersistentIdentifierGenerator.PK);
        String schema = properties.getProperty(SCHEMA);
        if (schema==null) schema = properties.getProperty(PersistentIdentifierGenerator.SCHEMA);
        this.sql = "select max( "+column+" ) from "+ (StringUtils.isEmpty(schema)?table:(schema+"."+table));
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        Connection connection = sharedSessionContractImplementor.connection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(this.sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            String currentTime =new SimpleDateFormat("yyyyMMdd").format(new Date());
            if (resultSet.next()){
                String string = resultSet.getString(1);
                System.out.println(string);
                if (!StringUtils.isEmpty(string)) {
                    String dataSuffix = string.substring(0,8);
                    String middle;
                    if (dataSuffix.equals(currentTime)) {
                        middle = string.substring(8, 12);
                    } else {
                        middle = "0000";
                    }
                    long l = Long.parseLong(middle);
                    next = currentTime + String.format("%04d",l + 1) + String.format("%05d", random.nextInt(100000));
                }else {
                    next = currentTime +"0000" + String.format("%05d", random.nextInt(100000));
                }
            }
            System.out.println(next);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return next;
    }

    public static void main(String[] args) {

        System.out.println(Long.parseLong("1233230002"));
        System.out.println(System.currentTimeMillis());
        System.out.println( String.format("%05d", random.nextInt(10000)));
    }
}
