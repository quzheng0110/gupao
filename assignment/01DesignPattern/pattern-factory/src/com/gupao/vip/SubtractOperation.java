package com.gupao.vip;

import com.gupao.vip.simpleFactory.Operation;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class SubtractOperation extends Operation {
    @Override
    public float getResult(float firstNum, float secondNum) {
        return firstNum - secondNum;
    }
}
