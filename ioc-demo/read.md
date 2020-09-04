


1、实现InitializingBean的bean，在所有的属性被设置后，会被容器回调afterPropertiesSet()方法

public interface InitializingBean {
	void afterPropertiesSet() throws Exception;
}

2、实现DisposableBean的bean，在被容器销毁时，会被容器回调destroy()方法

public interface DisposableBean {
	void destroy() throws Exception;
}

3、使用 @PostConstruct 注解方法，在bean初始化完成后被调用

4、使用@PreDestroy注解通方法，在被容器销毁时调用
  
5、在xml或者注解配置上，使用init-method和destroy-method对象定义元数据

6、实现ApplicationContextAware获取ApplicationContext
    public interface ApplicationContextAware extends Aware {
        void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
    }
    通过自动装配的方式获取，更好
    
    @Autowired
    ApplicationContext applicationContext;

https://blog.csdn.net/wd2014610/article/details/80403804