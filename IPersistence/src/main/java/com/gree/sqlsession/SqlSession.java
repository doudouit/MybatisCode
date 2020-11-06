package com.gree.sqlsession;

import java.util.List;

/**
 * @Auther: allen
 * @Date: 2020/9/2 19:33
 * @Description:
 */
public interface SqlSession {

    // 查询所有
    public <E> List<E> selectList(String statementId, Object... params) throws Exception;

    // 根据条件查询单个记录
    public  <T> T selectOne(String satementId,Object... params) throws Exception;

    public int delete(String statementId, Object... params) throws Exception;

    public int updata(String statementId, Object... params) throws Exception;

    public <T> T getMapper(Class<?> mapperClass);
}
