package com.gree.sqlsession;

/**
 * @Auther: allen
 * @Date: 2020/9/2 18:44
 * @Description:
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
