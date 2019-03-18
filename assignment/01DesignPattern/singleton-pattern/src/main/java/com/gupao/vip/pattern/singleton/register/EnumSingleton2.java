package com.gupao.vip.pattern.singleton.register;

/**
 * @Author: QuZheng
 * @Date: 2019/3/18.
 * @Description: ${DESCRIPTION}
 */
public enum  EnumSingleton2 {
    INSTANCE;

    private Object data;

    public static EnumSingleton2 getInstance(){
        return INSTANCE;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
