package com.ygy.study.exceptioninternationaldemo.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by panjinghong on 04/01/2018.
 * 拓展 ResponseEntity，增加一个 httpStatus：（当业务错误需要前端处理时，统一返回 httpStatus：BUSINESS_ERROR 299）
 */
public class EduResponseEntity<T> extends ResponseEntity<T> {

	public EduResponseEntity(HttpStatus status) {
		super(status);
	}

	/**
	 * Create a builder with the status set to {@linkplain EduHttpStatus#BUSINESS_ERROR BUSINESS_ERROR}.
	 *
	 * @return the created builder
	 * @since 4.1
	 */
	private static BodyBuilder error() {
		return status(EduHttpStatus.BUSINESS_ERROR.value());
	}


	/**
	 * 服务器处理正常，返回执行结果
	 * 如果结果存在时，httpStatus=200，body {...}
	 * 如果结果不存在时，httpStatus=299，body {code:2,msg:"object not exist"}{@linkplain EduHttpStatus#BUSINESS_ERROR }{@linkplain EduBusinessCode#NOT_EXIST}.
	 * @param body
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseEntity<T> ok(T body) {
		if(null==body) {
			return buildNotExistError();
		}
		ResponseEntity.BodyBuilder builder = ok();
		return builder.body(body);
	}
	/**
	 * 无明确字段的参数错误 httpStatus=299，body {code:1,msg:"msg"}{@linkplain EduHttpStatus#BUSINESS_ERROR }{@linkplain EduBusinessCode#PARAM_ERROR}.
	 * @param msg
	 * @return
	 */
	public static ResponseEntity buildParamError(String msg) {
		BodyBuilder builder = error();
		return builder.body(new EduErrorResponse(EduBusinessCode.PARAM_ERROR.value(), msg));
	}

	/**
	 * 字段明确的参数错误 httpStatus=299，body {code:1,msg:"param error"content:errMap}{@linkplain EduHttpStatus#BUSINESS_ERROR }{@linkplain EduBusinessCode#PARAM_ERROR}.
	 * @param errMap
	 * @return
	 */
	public static ResponseEntity buildParamError(Map<String,String> errMap) {
		BodyBuilder builder = error();
		EduErrorResponse response = new EduErrorResponse(EduBusinessCode.PARAM_ERROR.value(), Constants.BussinessErrorMsg.paramError);
		response.setContent((HashMap) errMap);
		return builder.body(response);
	}

	/**
	 * 字段明确的参数错误 httpStatus=299，body {code:1,msg:"msg"content:errMap}{@linkplain EduHttpStatus#BUSINESS_ERROR }{@linkplain EduBusinessCode#PARAM_ERROR}.
	 * @param errMap
	 * @return
	 */
	public static ResponseEntity buildParamError(String msg,Map<String,String> errMap) {
		BodyBuilder builder = error();
		EduErrorResponse response = new EduErrorResponse(EduBusinessCode.PARAM_ERROR.value(),msg);
		response.setContent((HashMap) errMap);
		return builder.body(response);
	}

	/**
	 * 资源不存在 httpStatus=299，body {code:2,msg:"not exist"}{@linkplain EduHttpStatus#BUSINESS_ERROR }{@linkplain EduBusinessCode#NOT_EXIST}.
	 * @param
	 * @return
	 */
	public static ResponseEntity buildNotExistError() {
		BodyBuilder builder = error();
		return builder.body(new EduErrorResponse(EduBusinessCode.NOT_EXIST.value(),Constants.BussinessErrorMsg.objectNotExist));
	}
	/**
	 * 资源不存在 httpStatus=299，body {code:2,msg:"msg"}{@linkplain EduHttpStatus#BUSINESS_ERROR }{@linkplain EduBusinessCode#NOT_EXIST}.
	 * @param
	 * @return
	 */
	public static ResponseEntity buildNotExistError(String msg) {
		BodyBuilder builder = error();
		return builder.body(new EduErrorResponse(EduBusinessCode.NOT_EXIST.value(),msg));
	}

	/**
	 * 业务异常 httpStatus=299，body {code:3,msg:"msg"}{@linkplain EduHttpStatus#BUSINESS_ERROR }{@linkplain EduBusinessCode#BUSINESS_ERROR}.
	 * @param
	 * @return
	 */
	public static ResponseEntity buildBusinessError(String msg) {
		BodyBuilder builder = error();
		return builder.body(new EduErrorResponse(EduBusinessCode.BUSINESS_ERROR.value(),msg));
	}

	/**
	 * 业务异常 httpStatus=299，body {code:3,msg:"msg"}{@linkplain EduHttpStatus#BUSINESS_ERROR }{@linkplain EduBusinessCode#BUSINESS_ERROR}.
	 * @param
	 * @return
	 */
	@Deprecated //错误用法
	public static ResponseEntity buildBusinessError(int code,String msg) {
		BodyBuilder builder = error();
		return builder.body(new EduErrorResponse(code,msg));
	}

	/**
	 * 业务异常 httpStatus=299，body {code:3,msg:"msg"}{@linkplain EduHttpStatus#BUSINESS_ERROR }{@linkplain EduBusinessCode#BUSINESS_ERROR}.
	 * @param
	 * @return
	 */
	public static ResponseEntity buildBusinessError(String msg,String ... params) {
		BodyBuilder builder = error();
		return builder.body(new EduErrorResponse(EduBusinessCode.BUSINESS_ERROR.value(),msg,params));
	}

	/**
	 * 不允许手工调用，服务端错误
	 * @param serviceErrorCode
	 * @return
	 */
	public static ResponseEntity buildServiceBusinessError(int serviceErrorCode, String serviceErrorMsg) {
		BodyBuilder builder = error();
		EduErrorResponse response = new EduErrorResponse(EduBusinessCode.BUSINESS_ERROR.value());
		response.setServiceErrorMsg(serviceErrorCode, serviceErrorMsg);
		return builder.body(response);
	}


}
