package com.ygy.study.exceptioninternationaldemo.common;

/**
 * Created by panjinghong on 04/01/2018.
 */
public enum EduHttpStatus {
	BUSINESS_ERROR(299, "业务错误");

	private final int value;

	private final String reasonPhrase;

	EduHttpStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public int value() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return this.reasonPhrase;
	}
}
