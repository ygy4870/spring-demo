package com.ygy.study.exceptioninternationaldemo.common;

/**
 * 业务码
 */
public enum EduBusinessCode {
	PARAM_ERROR(1, "参数错误"),
	NOT_EXIST(2, "对象不存在"),
	BUSINESS_ERROR(3, "业务错误"),
	ALREADY_EXIST(4, "对象已经存在");

	EduBusinessCode(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	private final int value;
	private final String reasonPhrase;

	public int value() {
		return value;
	}

	public String reasonPhrase() {
		return reasonPhrase;
	}
}
