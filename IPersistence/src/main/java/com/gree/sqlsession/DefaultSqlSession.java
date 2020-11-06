package com.gree.sqlsession;

import com.gree.pojo.Configuration;
import com.gree.pojo.MappedStatement;

import java.lang.reflect.*;
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

    @Override
    public int delete(String statementId, Object... params) throws Exception {
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        int i = simpleExecutor.delete(configuration, mappedStatement, params);
        return i;
    }

    @Override
    public int updata(String statementId, Object... params) throws Exception {
        return delete(statementId, params);
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用jdk动态代理，为dao接口生成代理对象，并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层都还是去执行JDBC代码 //根据不同情况，来调用selctList或者selectOne
                // 准备参数 1：statmentid :sql语句的唯一标识：namespace.id= 接口全限定名.方法名
                // 方法名：findAll
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();

                String statementId = className + "." + methodName;

                String sql = configuration.getMappedStatementMap().get(statementId).getSql();
                if (sql.startsWith("select")) {
                    // 准备参数二
                    // 获取被调用方法的返回值类型
                    Type returnType = method.getGenericReturnType();
                    // 判断是否进行了 泛型类型参数化
                    if (returnType instanceof ParameterizedType) {
                        List<Object> objects = selectList(statementId, args);
                        return objects;
                    }
                    return selectOne(statementId, args);
                } else if (sql.startsWith("update")) {
                    return updata(statementId, args);
                } else if (sql.startsWith("delete")) {
                    return delete(statementId, args);
                } else {

                }
                return null;
            }
        });
        return (T) proxyInstance;
    }
}
