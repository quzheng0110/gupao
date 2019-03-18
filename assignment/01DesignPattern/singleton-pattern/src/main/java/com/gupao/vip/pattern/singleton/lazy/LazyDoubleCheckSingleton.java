package com.gupao.vip.pattern.singleton.lazy;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: 双重检查锁单例
 *
 *  synchronized放在方法里面锁初始化代码块+双重空判断
 *  缺点：cpu是通过指令执行的，多线程情况出现指令重排序的问题
 *  解决方案：在私有静态变量上加volatile关键字
 */
public class LazyDoubleCheckSingleton {
    private static LazyDoubleCheckSingleton singleton;
    //解决指令重排序问题
//    private volatile static LazyDoubleCheckSingleton singleton;
    private LazyDoubleCheckSingleton(){}
    public static LazyDoubleCheckSingleton getInstance(){
        if (singleton == null){
            synchronized (LazyDoubleCheckSingleton.class){
                if (singleton == null)
                    singleton = new LazyDoubleCheckSingleton();
            }
        }
        return singleton;
    }
}
