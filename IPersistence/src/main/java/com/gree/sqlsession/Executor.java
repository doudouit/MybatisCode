package com.gree.sqlsession;

import com.gree.pojo.Configuration;
import com.gree.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: allen
 * @Date: 2020/9/2 19:55
 * @Description:
 */
public interface Executor {

    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, Exception;
}
