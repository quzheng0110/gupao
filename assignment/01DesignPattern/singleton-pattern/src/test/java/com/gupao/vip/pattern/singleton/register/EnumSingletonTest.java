package com.gupao.vip.pattern.singleton.register;

import com.gupao.vip.ThreadExecutors;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: ${DESCRIPTION}
 */
public class EnumSingletonTest {
    @Test
    public void getResource() throws Exception {
        Thread t1 = new Thread(new ThreadExecutors());
        Thread t2 = new Thread(new ThreadExecutors());
        t1.start();
        t2.start();

    }

}