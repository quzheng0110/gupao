package com.gupao.vip.pattern.singleton.register;

import org.apache.logging.log4j.message.ReusableMessage;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: ${DESCRIPTION}
 */
public class EnumSingleton {
    private enum MyEnumSingleton{
        INSTANCE;
        private Object resource;
        MyEnumSingleton(){
            resource = new Object();
        }
        public Object getResource(){
            return resource;
        }
    }

    public static Object getResource(){
        return MyEnumSingleton.INSTANCE.getResource();
    }
}
