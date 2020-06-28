package com.ygy.study.exceptioninternationaldemo.common;

/**
 * restful 接口返回值规范常量
 *
 */
public interface Constants {

	interface Test {

		String UidEmpty = "test.UidEmpty";
        String NameEmpty = "test.NameEmpty";
    }

	interface RootMapping {

	}

	
	interface DelFlag {
		Integer Normal = 0;
		Integer Delete = 1;
	}

	interface BussinessErrorMsg {

		String error = "system.error";
		/**
		 * 参数丢失
		 */
		String paramLost = "system.valid.paramLost";

		/**
		 * 参数类型错误
		 */
		String paramError = "system.valid.paramError";

		/**
		 * 无法解析传入参数，Http消息不可读取异常
		 */
		String paramNotReadError = "system.valid.httpMessageNotReadable";

		/**
		 * 对象不存在
		 */
		String objectNotExist = "system.valid.objectNotExist";

		/**
		 * 业务错误
		 */
		String businessError = "system.valid.businessError";

		/**
		 * 对象已经存在
		 */
		String alreadyExist ="system.valid.alreadyExist";

		String rcpUnknownError = "rpc.error.unknown";

		String rcpNetworkError = "rpc.error.network";

		String rcpTimeoutError = "rpc.error.timeout";

		String rcpBizError = "rpc.error.biz";

		String rcpForbiddenError = "rpc.error.forbidden";

		String rcpSerializationError = "rpc.error.serialization";
	}

}
