package com.gupao.vip.pattern.singleton.lazy;

/**
 * @Author: QuZheng
 * @Date: 2019/3/12.
 * @Description: ${DESCRIPTION}
 */
public class LazyInnerClassSingleton {
    //私有构造函数
    private LazyInnerClassSingleton(){
        //防止反射通过构造函数初始化实例
        if (LazyHolder.lazy!=null){
            throw new RuntimeException("不允许构造多个实例");
        }
    }
    public static LazyInnerClassSingleton getInstance(){
        return LazyHolder.lazy;
    }

    private static class LazyHolder{
//        private static final LazyInnerClassSingleton lazy = new LazyInnerClassSingleton();
        private static final LazyInnerClassSingleton lazy;
        static {
            lazy = new LazyInnerClassSingleton();
        }
    }

    //防止序列化的方式破环单例模式
//    private Object readResolve(){
//        return LazyHolder.lazy;//返回单例的私有静态变量
//    }
}
