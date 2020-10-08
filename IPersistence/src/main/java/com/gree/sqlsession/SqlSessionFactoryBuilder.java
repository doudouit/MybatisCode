package com.gree.sqlsession;

import com.gree.config.XMLConfigBuilder;
import com.gree.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @Auther: allen
 * @Date: 2020/9/2 18:40
 * @Description:
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build (InputStream inputStream) throws DocumentException, PropertyVetoException {
        // 使用dom4j解析配置文件，解析出来的内容封装到configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);

        // 创建sqlsessionfactory对象 工厂类：生产sqlSession:会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }
}
