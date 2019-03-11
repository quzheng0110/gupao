package com.gupao.vip.abstractFactory;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class HRFactory implements IFactory {
    @Override
    public IAirConditioner createAirConditioner() {
        return new HRAirConditioner();
    }

    @Override
    public IWashingMachine createWashingMachine() {
        return new HRWashingMachine();
    }

    @Override
    public IWaterHeater createWaterHeater() {
        return new HRWaterHeater();
    }
}
