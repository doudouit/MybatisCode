package com.gree.io;

import java.io.InputStream;

/**
 * @Auther: allen
 * @Date: 2020/9/1 20:31
 * @Description:
 */
public class Resources {

    // 根据配置文件的路径，将配置文件加载成字节输入流，存储在内存中
    public static InputStream getResourceAsStream(String path) {
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}
