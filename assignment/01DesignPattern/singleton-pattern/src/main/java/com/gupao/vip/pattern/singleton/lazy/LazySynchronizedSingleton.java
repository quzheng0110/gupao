package com.gupao.vip.pattern.singleton.lazy;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: 懒汉式单例：被外部类调用的时才创建实例
 * <p>
 * 缺点：线程安全，存在一定的性能问题，synchronized锁方法可能会把类也锁定
 */
public class LazySynchronizedSingleton {
    private static LazySynchronizedSingleton singleton;
    private LazySynchronizedSingleton() { }

    public static synchronized LazySynchronizedSingleton getInstance() {

        if (singleton == null) {
            singleton = new LazySynchronizedSingleton();
        }
        return singleton;
    }
}
