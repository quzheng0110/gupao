package com.gupao.vip.abstractFactory;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class HRWashingMachine implements IWashingMachine {
    @Override
    public void getType() {
        System.out.println("海儿洗衣机");
    }
}
