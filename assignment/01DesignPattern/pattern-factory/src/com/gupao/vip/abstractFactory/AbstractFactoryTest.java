package com.gupao.vip.abstractFactory;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class AbstractFactoryTest {
    public static void main(String[] args) {
        IFactory factory = new GLFactory();
        factory.createAirConditioner().getType();
        factory.createWashingMachine().getType();
        factory.createWaterHeater().getType();
    }
}
