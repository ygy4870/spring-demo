package com.ygy.study.swaggerdemo.swaggerconfig;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Configuration
//@EnableSwagger2
//@Profile({"dev","dev2","test"})
//@Import({ springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class })
public class Swagger2 {

	@Bean
	public Docket createRestApi() {
		List<ResponseMessage> responseMessageList = new ArrayList<>();
		responseMessageList.add(new ResponseMessageBuilder().code(200).message("请求成功\n"
				+ "无匹配资源:response body 为 0 字节，不返回任何数据\n"
				+ "单个资源：{field1: value1, …}\n"
				+ "资源集合：[{field1: value1, …}]\n"
				+ "资源集合（带分页）：\n"
				+ "{\n"
				+ "  \"list\":  [{\"field1\": \"value1\", …}]\n"
				+ "  \"pageNo\": 0,\n"
				+ "  \"pageSize\": 0,\n"
				+ "  \"total\": 0\n"
				+ "}").build());
		responseMessageList.add(new ResponseMessageBuilder().code(299).message("业务异常错误，需要展示返回体中的msg").responseModel(new ModelRef("EduErrorResponse")).build());
		responseMessageList.add(new ResponseMessageBuilder().code(401).message("授权失败，需要引导用户登陆重试").build());
		responseMessageList.add(new ResponseMessageBuilder().code(403).message("授权失败，用户没有该资源访问权限,也可能用户未登陆，需要提示用户").build());
		responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源，请检查请求路径").build());
		responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").responseModel(new ModelRef("EduErrorResponse")).build());

		return new Docket(DocumentationType.SWAGGER_2)
				.globalResponseMessage(RequestMethod.GET, responseMessageList)
				.globalResponseMessage(RequestMethod.POST, responseMessageList)
				.globalResponseMessage(RequestMethod.PUT, responseMessageList)
				.globalResponseMessage(RequestMethod.DELETE, responseMessageList)
				.apiInfo(apiInfo()).directModelSubstitute(Date.class, Long.class).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("APP内RESTful APIs").description("").termsOfServiceUrl("http://hippo.100.com/app")
				.contact("Edu100").version("1.0").build();
	}


//	@Component
//	@Order(1)
//	public class DtoPropertyBuilder extends ApiModelPropertyPropertyBuilder {
//
//		private final DescriptionResolver descriptions;
//
//
//		public DtoPropertyBuilder(DescriptionResolver descriptions) {
//			super(descriptions);
//			this.descriptions = descriptions;
//		}
//
//		@Override
//		public void apply(ModelPropertyContext context) {
//			Optional<DtoProperty> annotation = Optional.absent();
//			//取实例化时的注解
//			if (context.getAnnotatedElement().isPresent()) {
//				annotation = annotation.or(Optional.fromNullable(AnnotationUtils.getAnnotation(
//						context.getAnnotatedElement().get(), DtoProperty.class)));
//			}
//
//			if (context.getBeanPropertyDefinition().isPresent()) {
//				//从getter上取出注解
//				if (context.getBeanPropertyDefinition().get().getGetter() != null) {
//					annotation = annotation.or(Optional.fromNullable(
//							context.getBeanPropertyDefinition().get().getGetter().getAnnotation(DtoProperty.class)));
//				}
//				//从字段上取出注解
//				annotation = annotation.or(Annotations
//						.findPropertyAnnotation(context.getBeanPropertyDefinition().get(), DtoProperty.class));
//
//			}
//			if (annotation.isPresent()) {
//				context.getBuilder().description(annotation.transform(toDescription(this.descriptions)).orNull())
//						.isHidden(annotation.transform(toHidden()).or(false))
//						.readOnly(annotation.transform(toReadOnly()).or(false))
//						.required(annotation.transform(toRequired()).or(false));
//			}
//		}
//
//		Function<DtoProperty, String> toDescription(final DescriptionResolver descriptions) {
//			return annotation -> {
//				String description = "";
//				if (!Strings.isNullOrEmpty(annotation.value())) {
//					description = annotation.value();
//				}
//				return descriptions.resolve(description);
//			};
//		}
//
//		Function<DtoProperty, Boolean> toHidden() {
//			return annotation -> annotation.hidden();
//		}
//	}
//
//	private Function<DtoProperty, Boolean> toRequired() {
//		return annotation -> annotation.required();
//	}
//
//	private Function<DtoProperty, Boolean> toReadOnly() {
//		return annotation -> annotation.accessMode()==DtoProperty.AccessMode.READ_ONLY;
//	}
}
