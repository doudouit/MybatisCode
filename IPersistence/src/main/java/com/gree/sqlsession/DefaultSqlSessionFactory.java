package com.gree.sqlsession;

import com.gree.pojo.Configuration;

/**
 * @Auther: allen
 * @Date: 2020/9/2 19:28
 * @Description:
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
