package com.gupao.vip.abstractFactory;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class GLWaterHeater implements IWaterHeater {
    @Override
    public void getType() {
        System.out.println("格力热水器");
    }
}
