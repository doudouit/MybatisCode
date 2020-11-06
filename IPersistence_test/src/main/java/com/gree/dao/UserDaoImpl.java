package com.gree.dao;

import com.gree.io.Resources;
import com.gree.pojo.User;
import com.gree.sqlsession.SqlSession;
import com.gree.sqlsession.SqlSessionFactory;
import com.gree.sqlsession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * @Auther: allen
 * @Date: 2020/10/9 19:55
 * @Description:
 */
public class UserDaoImpl implements IUserDao {
    public List<User> findAll() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> users = sqlSession.selectList("user.selectList");

        for (User user1 : users) {
            System.out.println(user1);
        }

        return users;
    }

    public User findByCondition(User user) throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();


        User user2 = sqlSession.selectOne("user.selectOne", user);
        System.out.println(user2);

        return user2;
    }

    public int deleteByCondition(User user) throws Exception {
        return 0;
    }

    public int updateById(User user) throws Exception {
        return 0;
    }
}
