package com.gupao.vip.simpleFactory;

import java.util.Scanner;

/**
 * @Author: QuZheng
 * @Date: 2019/3/11.
 * @Description: ${DESCRIPTION}
 */
public class simpleFactoryTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入第一个数字：");
        float firstNum = scanner.nextFloat();
        System.out.println("请输入第二个数字：");
        float secondNum = scanner.nextFloat();
        System.out.println("请输入运算符号：");
        String operator = scanner.next();
        System.out.println("计算结果： " + getResult(firstNum, secondNum, operator));
    }

    private static float getResult(float firstNum, float secondNum, String operator) {
        Operation operation = OperationFactory.getOperation(operator);
        return operation.getResult(firstNum, secondNum);
    }
}
