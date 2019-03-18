package com.gupao.vip.pattern.singleton.hungry;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: ${DESCRIPTION}
 *
 * 缺点：类被加载的时候就被初始化，浪费内存空间
 */
public class HungrySingleton {
    //final防止反射的时候静态变量被修改
    private static final HungrySingleton SINGLETON = new HungrySingleton();

    private HungrySingleton(){}

    public static HungrySingleton getInstance(){
        return SINGLETON;
    }
}
