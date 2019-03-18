package com.gupao.vip.pattern.singleton.hungry;

import org.apache.logging.log4j.message.ReusableMessage;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: 静态代码块饿汉单例
 */
public class HungryStaticSingleton {
    //final防止反射的时候静态变量被修改
    private static final HungryStaticSingleton SINGLETON;

    static {
        SINGLETON = new HungryStaticSingleton();
    }

    private HungryStaticSingleton(){}

    public static HungryStaticSingleton getInstance(){
        return SINGLETON;
    }

}
