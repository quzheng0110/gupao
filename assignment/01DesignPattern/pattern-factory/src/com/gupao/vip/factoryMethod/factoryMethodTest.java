package com.gupao.vip.factoryMethod;

import java.util.Scanner;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class factoryMethodTest {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入第一个数字：");
        float firstNum = scanner.nextFloat();
        System.out.println("请输入第二个数字：");
        float secondNum = scanner.nextFloat();
        System.out.println("请输入运算符号：");
        String operator = scanner.next();

        IOperationFactory factory;
        switch (operator){
            case "+" : factory = new AddOperationFactory(); break;
            case "-" : factory = new SubtractOperationFactory(); break;
            case "*" : factory = new MultiplyOperationFactory(); break;
            case "/" : factory = new DivideOperationFactory(); break;
            default: throw new Exception("operator is null");
        }

        System.out.println("计算结果： " + factory.getOperation().getResult(firstNum, secondNum));
    }
}
