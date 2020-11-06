package com.gree.io;


import com.gree.dao.IUserDao;
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
        user.setUsername("allen");
        user.setPassword("coco1");
        user.setBirthday("1998-02-01");

        /*int updata = sqlSession.updata("user.updateById", user);
        System.out.println("影响行数" + updata);*/

        // 删除
        /*int i = sqlSession.delete("user.delete", user);
        System.out.println("影响行数" + i);*/

        /*User user2 = sqlSession.selectOne("user.selectOne", user);
        System.out.println(user2);

        List<User> users = sqlSession.selectList("user.selectList");
        for (User user1 : users) {
            System.out.println(user1);
        }*/

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> all = userDao.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }

        /*int i = userDao.deleteByCondition(user);
        System.out.println("影响行数");*/

        int i = userDao.updateById(user);
        System.out.println("影响行数" + i);
    }
}