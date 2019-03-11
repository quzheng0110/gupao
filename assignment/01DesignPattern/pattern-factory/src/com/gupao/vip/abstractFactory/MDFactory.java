package com.gupao.vip.abstractFactory;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class MDFactory implements IFactory {
    @Override
    public IAirConditioner createAirConditioner() {
        return new MDAirConditioner();
    }

    @Override
    public IWashingMachine createWashingMachine() {
        return new MDWashingMachine();
    }

    @Override
    public IWaterHeater createWaterHeater() {

        return new MDWaterHeater();
    }
}
