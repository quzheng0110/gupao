package com.gupao.edu.vip;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * enum实现单例模式
 * Created by QuZheng on 2018/11/6.
 */
public enum  Calculator {
    Instance;
    private final static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    public Object cal(String expression) throws ScriptException {
        return jse.eval(expression);
    }
}
