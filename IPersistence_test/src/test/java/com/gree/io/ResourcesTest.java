package com.gree.io;


import com.gree.pojo.User;
import com.gree.sqlsession.SqlSession;
import com.gree.sqlsession.SqlSessionFactory;
import com.gree.sqlsession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

/**
 * @Auther: allen
 * @Date: 2020/9/1 20:34
 * @Description:
 */
public class ResourcesTest {

    @Test
    public void test() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 调用
        User user = new User();
        user.setId(3);
        user.setUsername("3213");
        User user2 = sqlSession.selectOne("user.selectOne", user);
        System.out.println(user2);

        List<User> users = sqlSession.selectList("user.selectList");
        for (User user1 : users) {
            System.out.println(user1);
        }
    }
}