package com.gree.dao;

import com.gree.pojo.User;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.util.List;

/**
 * @Auther: allen
 * @Date: 2020/10/9 19:53
 * @Description:
 */
public interface IUserDao {

    // 查询所有用户、
    public List<User> findAll() throws Exception;

    // 根据条件查询用户
    public User findByCondition(User user) throws Exception;

    // 根据条件删除用户
    int deleteByCondition(User user) throws Exception;

    // 根据id更新用户
    int updateById(User user) throws Exception;
}
