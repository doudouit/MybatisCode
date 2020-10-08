package com.gree.sqlsession;

import com.gree.pojo.Configuration;
import com.gree.pojo.MappedStatement;

import java.util.List;

/**
 * @Auther: allen
 * @Date: 2020/9/2 19:34
 * @Description:
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        // 将要完成对simpleExecutor
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);

        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String satementId, Object... params) throws Exception {
        List<Object> objects = selectList(satementId, params);
        if (objects.size() == 1) {
            return (T) objects.get(0);
        }else {
            throw new  RuntimeException("查询结果为空或者返回结果过多");
        }
    }
}
