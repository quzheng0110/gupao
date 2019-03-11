package com.gupao.vip.abstractFactory;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class GLFactory implements IFactory {
    @Override
    public IAirConditioner createAirConditioner() {
        return new GLAirConditioner();
    }

    @Override
    public IWashingMachine createWashingMachine() {
        return new GLWashingMachine();
    }

    @Override
    public IWaterHeater createWaterHeater() {
        return new GLWaterHeater();
    }
}
