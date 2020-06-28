package com.ygy.study.exceptioninternationaldemo.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.HashMap;
import java.util.Locale;

/**
 * 错误返回体定义，该定义只是在错误时才返回
 *
 */
public class EduErrorResponse {

	private static ReloadableResourceBundleMessageSource messageSource;
	private static ReloadableResourceBundleMessageSource serviceMessageSource;

	static {
		messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("messages");
		serviceMessageSource = new ReloadableResourceBundleMessageSource();
		serviceMessageSource.setBasename("error_msg");
	}

//	@ApiModelProperty("错误返回码：1：参数错误，2：对象不存在，3：业务错误，4：对象已经存在")
	private Integer code;
//	@ApiModelProperty("错误返回信息，如：对象不存在")
	private String msg;
//	@ApiModelProperty("详细错误，如：{\"username\":\"用户名不能为空\"}")
	private HashMap<String, String> content;
//	@ApiModelProperty("traceId")
	private String traceId;
//	@ApiModelProperty("serviceCode")
	private Integer serviceCode;

	private EduErrorResponse() {
	}

	public EduErrorResponse(Integer code, String msg) {
		this(code, msg, null);
	}

	public EduErrorResponse(Integer code) {
		this(code, null, null);
	}

	public EduErrorResponse(String msg) {
		this(null, msg, null);
	}

	public EduErrorResponse(String msg, String... params) {
		this(null, msg, params);
	}

	public EduErrorResponse(Integer code, String msg, String... params) {
		this.traceId =	MDC.get("traceId");
		this.code = code;
		this.msg = msg != null ? getLocalMsg(msg) : null;
		if (StringUtils.isNotBlank(msg)) {
			if (null != params) {
				this.msg = String.format(getLocalMsg(msg), params);
			} else {
				this.msg = getLocalMsg(msg);
			}
		}
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public HashMap<String,String> getContent() {
		return content;
	}

	public void setContent(HashMap<String,String> content) {
		this.content = content;
	}

	public String getTraceId() {
		return traceId;
	}

	public Integer getServiceCode() {
		return serviceCode;
	}

	public void setServiceErrorMsg(int serviceErrorCode, String serviceErrorMsg) {
		this.serviceCode = serviceErrorCode;
		if (serviceErrorMsg != null) {
			this.msg = getServiceMsg(serviceErrorCode) + "(" + serviceErrorMsg + ")";
		}else {
			this.msg = getServiceMsg(serviceErrorCode);
		}
	}

	private static String getServiceMsg(int code) {
		try {
			Locale locale = LocaleContextHolder.getLocale();
			if (serviceMessageSource != null) {
				return serviceMessageSource.getMessage(String.valueOf(code), null, locale);
			} else {
				return String.valueOf(code);
			}
		} catch (Exception e) {
			return String.valueOf(code);
		}
	}

	public static String getLocalMsg(String msg) {
		try {
			Locale locale = LocaleContextHolder.getLocale();
			if(messageSource!=null){
				return messageSource.getMessage(msg, null, locale);
			}else {
				return msg;
			}
		}catch (Exception e){
			return msg;
		}
	}
}
