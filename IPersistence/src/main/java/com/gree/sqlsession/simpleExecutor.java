package com.gree.sqlsession;

import com.gree.config.BoundSql;
import com.gree.pojo.Configuration;
import com.gree.pojo.MappedStatement;
import com.gree.utils.GenericTokenParser;
import com.gree.utils.ParameterMapping;
import com.gree.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: allen
 * @Date: 2020/9/2 19:56
 * @Description:
 */
public class simpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 1.注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        // 2. 获取sql语句
            //转换sql语句 #{} --》 ？
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 3.获取预处理对象 preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4. 设置对象
            // 获取到参数的全路径
        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClassType(paramterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            // 反射
            Field declaredField = paramterTypeClass.getDeclaredField(content);
            // 暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i+1, o);
        }
        // 5. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);

        ArrayList<Object> objects = new ArrayList<Object>();

        // 6.封装返回结果集
        while (resultSet.next()) {
            Object o = resultTypeClass.newInstance();
            // 元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段值
                Object value = resultSet.getObject(columnName);
                // 使用反射或者内省， 根据数据库表跟实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                // 获取写方法
                Method writeMethod = propertyDescriptor.getWriteMethod();
                // 向方法中写入值
                writeMethod.invoke(o, value);

            }
            objects.add(o);
        }

        return (List<E>) objects;
    }

    @Override
    public int delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, Exception {
        // 1.注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        // 2. 获取sql语句
        //转换sql语句 #{} --》 ？
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 3.获取预处理对象 preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4. 设置对象
        // 获取到参数的全路径
        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClassType(paramterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            // 反射
            Field declaredField = paramterTypeClass.getDeclaredField(content);
            // 暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i+1, o);
        }
        // 5. 执行sql
        int i = preparedStatement.executeUpdate();
        return i;
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, Exception {
        return delete(configuration, mappedStatement, params);
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (paramterType != null) {
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }
        return null;
    }

    /**
      * @Decription  完成对#{}的解析工作，1.将#{} 使用？代替，2.解析出#{}里面的值进行储存
      * @Author      allen
      * @param       sql
      * @Date        2020/9/23 19:02
      */
    private BoundSql getBoundSql(String sql) {
        // 标记处理类，配置标记解析器来完成对应占位符的解析工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        // 解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        // #{} 里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }
}
