###Spring的核心原理


**Spring IOC: 控制反转 Inverse of Control**
是一种思想，就是将原来在程序中手动创建对象的控制权，交给Spring框架管理


**DI：依赖注入 Dependency Injection**
是IOC的一种实现，Spring创建对象的过程中，将对象依赖属性（简单值，集合，对象）通过配置设置给对象

**Spring MVC:** 
Spring MVC是一个基于Java的实现了MVC设计模式的请求驱动类型的轻量级Web框架，通过把Model，View，Controller分离，将web层进行职责解耦，把复杂的web应用分成逻辑清晰的几部分，简化开发，减少出错，方便组内开发人员之间的配合

**Spring MVC的执行流程：**
	1. 用户发送请求至前段控制器DispatcherServlet
	2. DispatcherServlet收到请求调用HandlerMapping处理器映射器
	3. 处理器映射器找到具体的处理器（可以根据XML配置、注解进行查找），生成处理器对象及处理器拦截器一并返回给DispatcherServlet
	4. DispatcherServlet调用HandlerAdapter处理器适配器
	5. HandlerAdapter经过适配调用具体的处理器Controller
	6. Controller执行完成返回ModelAndView
	7. HandlerAdapter将controller执行结果ModelAndView返回给DispatcherServlet
	8. DispatcherServlet将ModelAndView传给ViewResolver视图解析器
	9. ViewResolver解析后返回具体View
	10. DispatcherServlet根据View进行渲染视图
	11. DispatcherServlet相应用户
