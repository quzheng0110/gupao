package com.gupao.vip.abstractFactory;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class MDAirConditioner implements IAirConditioner {
    @Override
    public void getType() {
        System.out.println("美的空调");
    }
}
