package com.gupao.vip.pattern.singleton.lazy;

import com.gupao.vip.ThreadExecutors;
import org.junit.Test;

import java.lang.reflect.Constructor;

/**
 * @Author: QuZheng
 * @Date: 2019/3/12.
 * @Description: ${DESCRIPTION}
 */
public class LazyInnerClassSingletonTest {
    @Test
    public void getInstance() throws Exception {
//        Thread t1 = new Thread(new ThreadExecutors());
//        Thread t2 = new Thread(new ThreadExecutors());
//        t1.start();
//        t2.start();

        Class<?> clazz = LazyInnerClassSingleton.class;
        Constructor constructor = clazz.getDeclaredConstructor(null);
        constructor.setAccessible(true);//授权访问
        Object o1 = constructor.newInstance();
        Object o2 = LazyInnerClassSingleton.getInstance();

        System.out.println(o1);
        System.out.println(o2);
    }

}