package com.gupao.vip.pattern.singleton.threadlocal;

import com.gupao.vip.ThreadExecutors;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: ${DESCRIPTION}
 */
public class ThreadLocalSingletonTest {
    @Test
    public void getInstance() throws Exception {
        System.out.println(Thread.currentThread().getName() + " : " +  System.currentTimeMillis() + " : " +ThreadLocalSingleton.getInstance());
        System.out.println(Thread.currentThread().getName() + " : " +  System.currentTimeMillis() + " : " +ThreadLocalSingleton.getInstance());
        System.out.println(Thread.currentThread().getName() + " : " +  System.currentTimeMillis() + " : " +ThreadLocalSingleton.getInstance());

        Thread t1 = new Thread(new ThreadExecutors());
        t1.start();
        Thread t2 = new Thread(new ThreadExecutors());
        t2.start();
        Thread t3 = new Thread(new ThreadExecutors());
        t3.start();
    }

}