package com.gupao.vip.pattern.singleton.threadlocal;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: ${DESCRIPTION}
 */
public class ThreadLocalSingleton {
    private ThreadLocalSingleton(){}
    private static final ThreadLocal<ThreadLocalSingleton> threadLoclInstance =
            new ThreadLocal<ThreadLocalSingleton>(){
                @Override
                protected ThreadLocalSingleton initialValue() {
                    return new ThreadLocalSingleton();
                }
            };
    public static ThreadLocalSingleton getInstance(){
        return threadLoclInstance.get();
    }
}
