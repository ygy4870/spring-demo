


1、实现InitializingBean的bean，在所有的属性被设置后，会被容器回调afterPropertiesSet()方法

public interface InitializingBean {
	void afterPropertiesSet() throws Exception;
}

2、实现DisposableBean的bean，在被容器销毁时，会被容器回调destroy()方法

public interface DisposableBean {
	void destroy() throws Exception;
}

3、使用 @PostConstruct 注解方法，在bean初始化完成后被调用

4、@PreDestroy注解通常被认为是在现代Spring应用程序中接收生命周期回调的最佳实践。使用这些注释意味着你的bean没有耦合到Spring特定的接口。有关详细信息，请参阅@PostConstruct和@PreDestroy。 
  如果您不想使用JSR-250注释，但仍想要移除耦合，请考虑使用init-method和destroy-method对象定义元数据。