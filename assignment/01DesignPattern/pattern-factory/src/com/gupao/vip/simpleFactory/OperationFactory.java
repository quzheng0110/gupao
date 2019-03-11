package com.gupao.vip.simpleFactory;

import com.gupao.vip.AddOperation;
import com.gupao.vip.DivideOperation;
import com.gupao.vip.MultiplyOperation;
import com.gupao.vip.SubtractOperation;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class OperationFactory {
    public static Operation getOperation(String operator){
        Operation obj = null;
        switch (operator){
            case "+" :
                obj = new AddOperation(); break;
            case "-" :
                obj = new SubtractOperation(); break;
            case "*" :
                obj = new MultiplyOperation(); break;
            case "/" :
                obj = new DivideOperation(); break;
            default: break;
        }

        return obj;
    }
}
