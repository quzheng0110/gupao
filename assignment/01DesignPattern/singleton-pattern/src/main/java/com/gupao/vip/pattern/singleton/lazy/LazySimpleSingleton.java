package com.gupao.vip.pattern.singleton.lazy;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: 懒汉式单例：被外部类调用的时才创建实例
 *
 * 缺点：线程不安全，多线程情况下创建多个实例
 */
public class LazySimpleSingleton {
    private static LazySimpleSingleton singleton;

    private LazySimpleSingleton() {}

    public static LazySimpleSingleton getInstance() {
        if (singleton == null) {
            singleton = new LazySimpleSingleton();
        }
        return singleton;
    }
}
