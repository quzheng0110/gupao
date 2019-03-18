package com.gupao.vip.pattern.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: ${DESCRIPTION}
 */
public class ContainerSingleton {
    //ConcurrentHashMap是线程安全的
    private static Map<String, Object> ioc = new ConcurrentHashMap<String, Object>();

    public static Object getBean(String className) {
        //getBean线程不安全，需要加synchronized锁
        synchronized (ioc) {
            if (!ioc.containsKey(className)) {
                try {
                    Object obj = Class.forName(className).newInstance();
                    ioc.put(className, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return ioc.get(className);
        }
    }
}
