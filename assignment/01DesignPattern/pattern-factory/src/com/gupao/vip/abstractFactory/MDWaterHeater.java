package com.gupao.vip.abstractFactory;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class MDWaterHeater implements IWaterHeater{
    @Override
    public void getType() {
        System.out.println("美的热水器");
    }
}
