package com.gupao.vip.abstractFactory;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public interface IFactory {
    IAirConditioner createAirConditioner();
    IWashingMachine createWashingMachine();
    IWaterHeater createWaterHeater();
}
