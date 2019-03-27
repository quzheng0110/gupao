package com.gupaoedu.mvcframework.v1;

import com.gupaoedu.mvcframework.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @Author: QuZheng
 * @Date: 2019/3/27.
 * @Description: ${DESCRIPTION}
 */
public class GPDispatcherServlet extends HttpServlet {
    //保存application.properties配置文件中的内容
    private Properties contextConfig = new Properties();

    //保存所有扫描到的类的类名
    private List<String> classNames = new ArrayList<String>();

    //创建一个IOC容器来保存所有初始化bean <类名,类的实例>
    private Map<String, Object> ioc = new HashMap<String, Object>();

    //创建一个容器来保存所有url和method的一对一关系
    private Map<String, Method> handlerMappings = new HashMap<String, Method>();

    //参数名和索引映射
    private Map<String, Integer> parameterIndexMapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //绝对路径
        String url = req.getRequestURI();
        //处理成相对路径
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

        if (!this.handlerMappings.containsKey(url)) {
            resp.getWriter().write("404 Not Found!");
            return;
        }

        //获取到方法并用放射执行
        Method method = this.handlerMappings.get(url);

        putParameterIndexMapping(method);

        //从request中拿到url传过来的参数
        Map<String, String[]> params = req.getParameterMap();
        //获取方法形参列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] paramsValues = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class parameterType = parameterTypes[i];
            if (parameterType == HttpServletRequest.class) {
                paramsValues[i] = req;
                continue;
            } else if (parameterType == HttpServletResponse.class) {
                paramsValues[i] = resp;
                continue;
            } else if (parameterType == String.class) {
                //由于反射的方法在获取getParameterTypes的时候是无法拿到参数的Annotation的，所以可以先在method上获取所有参数的注解method.getParameterAnnotations
                //然后放到一个map中把GPRequestParam注解的value和对应的索引存在map中，然后在通过索引找出对应GPRequestParam的value从请求参数列表中获取参数的值
                for (Map.Entry<String, Integer> entry : parameterIndexMapping.entrySet()) {
                    if (entry.getValue() == i){
                        if (params.containsKey(entry.getKey())) {
                            for (Map.Entry<String, String[]> param : params.entrySet()) {
                                String value = Arrays.toString(param.getValue())
                                        .replaceAll("\\[|\\]", "")
                                        .replaceAll("\\s", ",");
                                paramsValues[i] = value;
                            }
                        }
                        break;
                    }
                }
            }
        }

        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName), paramsValues);
    }

    private void putParameterIndexMapping(Method method){
        Annotation [] [] annotations = method.getParameterAnnotations();
        for (int j = 0;j <annotations.length;j++){
            for (Annotation annotation : annotations[j]) {
                if (annotation instanceof GPRequestParam){
                    String paramName = ((GPRequestParam) annotation).value();
                    if (!"".equals(paramName.trim())){
                        parameterIndexMapping.put(paramName,j);
                    }
                }
            }
        }
    }

    //初始化所有相关的类：IOC容器、servletBean、DI、HandlerMapping
    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2.扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));

        //3.初始化扫描到的类，并且将他们放入到IOC容器中
        doInstance();

        //4.完成依赖注入
        doAutowired();

        //5.初始化HandlerMapping
        initHandlerMapping();

        System.out.println("GP Spring framework is init.");
    }

    //初始化url和Method的一对一关系
    private void initHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            //扫描GPController注解的类
            if (!clazz.isAnnotationPresent(GPController.class)) {
                continue;
            }

            String baseUrl = "/";
            //1.当GPRequestMapping写在类上时@GPRequestMapping("/demo")
            if (clazz.isAnnotationPresent(GPRequestMapping.class)) {
                GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                baseUrl += requestMapping.value().trim() + "/";
            }
            //2.当GPRequestMapping写在方法上时@GPRequestMapping("/query.*")
            //获取所有public方法
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(GPRequestMapping.class)) {
                    continue;
                }
                GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                String url = (baseUrl + requestMapping.value()).replaceAll("/+", "/");
                //method.setAccessible(true);
                handlerMappings.put(url, method);
                System.out.println("Mapped :" + url + "," + method);
            }
        }
    }

    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //Declared 所有的，特定的 字段，包括private/protected/default
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(GPAutowired.class)) {
                    continue;
                }
                GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                String beanName = autowired.value().trim();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }
                //强制将首字母转为小写
                beanName = toLowerFirstCase(beanName);
                //强制访问
                field.setAccessible(true);

                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //初始化所有扫描到的加了GPService和GPController类名，并放到全局IOC容器中
    private void doInstance() {
        if (classNames.isEmpty()) return;
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                //只有加了注解的类才需要初始化@GPController 和 @GPService注解
                if (clazz.isAnnotationPresent(GPController.class)) {
                    Object object = clazz.newInstance();
                    //Spring默认首字母小写
                    ioc.put(toLowerFirstCase(clazz.getSimpleName()), object);
                } else if (clazz.isAnnotationPresent(GPService.class)) {
                    //1.自定义的beanName
                    GPService service = clazz.getAnnotation(GPService.class);
                    String beanName = service.value().trim();
                    //2.默认类名首字母大写
                    if ("".equals(beanName)) {
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    Object newInstance = clazz.newInstance();
                    ioc.put(beanName, newInstance);
                    //根据类型自定赋值，把实现的接口也初始化
                    for (Class<?> cla : clazz.getInterfaces()) {
                        if (ioc.containsKey(cla.getName())) {
                            throw new Exception("The " + cla.getName() + " is exists!");
                        }
                        //把接口的类型直接当成key了
                        ioc.put(cla.getName(), newInstance);
                    }
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //将首字母转为小写
    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //判断如果首字母是小写直接返回
        if (chars[0] >= 97 && chars[0] <= 119) {
            return simpleName;
        }
        //把首字母大写转换为小写+32
        chars[0] += 32;
        return String.valueOf(chars);
    }

    //根据配置文件中配置的包的路径扫描这个包下面所有的类
    private void doScanner(String scanPackage) {
        //scanPackage = com.gupaoedu.demo ，存储的是包路径
        //转换为文件路径，实际上就是把.替换为/就OK了
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                //如果是文件夹就递归扫描
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName().replace(".class", ""));
                classNames.add(className);
            }
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        //直接从类路径下找到Spring主配置文件所在的路径
        //并且将其读取出来放到Properties对象中
        //相对于scanPackage=com.gupaoedu.demo 从文件中保存到了内存中
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
