package com.gupao.vip;

import com.gupao.vip.pattern.singleton.hungry.HungrySingleton;
import com.gupao.vip.pattern.singleton.hungry.HungryStaticSingleton;
import com.gupao.vip.pattern.singleton.lazy.LazyDoubleCheckSingleton;
import com.gupao.vip.pattern.singleton.lazy.LazyInnerClassSingleton;
import com.gupao.vip.pattern.singleton.lazy.LazySimpleSingleton;
import com.gupao.vip.pattern.singleton.lazy.LazySynchronizedSingleton;
import com.gupao.vip.pattern.singleton.register.ContainerSingleton;
import com.gupao.vip.pattern.singleton.register.EnumSingleton;
import com.gupao.vip.pattern.singleton.register.EnumSingleton2;
import com.gupao.vip.pattern.singleton.threadlocal.ThreadLocalSingleton;

/**
 * @Author: QuZheng
 * @Date: 2019/3/12.
 * @Description: ${DESCRIPTION}
 */
public class ThreadExecutors implements Runnable{
    @Override
    public void run() {
//        LazyInnerClassSingleton singleton = LazyInnerClassSingleton.getInstance();
//        HungrySingleton singleton = HungrySingleton.getInstance();
//        HungryStaticSingleton singleton = HungryStaticSingleton.getInstance();
//        LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();
//        LazySynchronizedSingleton singleton = LazySynchronizedSingleton.getInstance();
//        LazyDoubleCheckSingleton singleton = LazyDoubleCheckSingleton.getInstance();
//        Object singleton = EnumSingleton.getResource();
//        EnumSingleton2 singleton = EnumSingleton2.getInstance();
//        singleton.setData(new Object());
//        System.out.println(Thread.currentThread().getName() + " : " + singleton.getData());

//        Object singleton = ContainerSingleton.getBean("com.gupao.vip.Pojo");
        ThreadLocalSingleton singleton1 = ThreadLocalSingleton.getInstance();
        ThreadLocalSingleton singleton2 = ThreadLocalSingleton.getInstance();
        ThreadLocalSingleton singleton3 = ThreadLocalSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + " : " +  System.currentTimeMillis() + " : " + singleton1);
        System.out.println(Thread.currentThread().getName() + " : " +  System.currentTimeMillis() + " : " + singleton2);
        System.out.println(Thread.currentThread().getName() + " : " +  System.currentTimeMillis() + " : " + singleton3);
    }
}
