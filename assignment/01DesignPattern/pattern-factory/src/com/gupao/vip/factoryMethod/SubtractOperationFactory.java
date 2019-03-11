package com.gupao.vip.factoryMethod;

import com.gupao.vip.AddOperation;
import com.gupao.vip.SubtractOperation;
import com.gupao.vip.simpleFactory.Operation;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class SubtractOperationFactory implements IOperationFactory{
    @Override
    public Operation getOperation() {
        return new SubtractOperation();
    }
}
