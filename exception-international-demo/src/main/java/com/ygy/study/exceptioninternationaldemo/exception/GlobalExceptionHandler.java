package com.ygy.study.exceptioninternationaldemo.exception;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.ygy.study.exceptioninternationaldemo.common.Constants;
import com.ygy.study.exceptioninternationaldemo.common.EduErrorResponse;
import com.ygy.study.exceptioninternationaldemo.common.EduResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        logger.error("process the exception", ex);
        return bindException(ex.getBindingResult());
    }
    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<?> bindExceptionHandler(BindException ex) {
        logger.error("process the exception", ex);
        return bindException(ex.getBindingResult());
    }

    private ResponseEntity<?> bindException(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        HashMap<String, String> errorsMap = new HashMap<>();
        for (FieldError error : fieldErrors) {
            errorsMap.put(error.getField(), EduErrorResponse.getLocalMsg(error.getDefaultMessage()));
        }
        return EduResponseEntity.buildParamError(Constants.BussinessErrorMsg.paramError, errorsMap);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<?>  validationExceptionHandler(ValidationException ex) {
        logger.error("process the exception",ex);
        if(ex instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            HashMap<String, String> errorsMap = new HashMap<>();
            for (ConstraintViolation<?> violation : violations) {
                String message = violation.getMessage();
                errorsMap.put(violation.getPropertyPath().toString(),EduErrorResponse.getLocalMsg(message));
            }
            return  EduResponseEntity.buildParamError(Constants.BussinessErrorMsg.paramError,errorsMap);
        }
        return  EduResponseEntity.buildBusinessError(ex.getMessage());
    }

    /**
     * 参数缺失异常
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        logger.error("Missing Servlet Request Parameter Exception", ex);
        String parameterName = ex.getParameterName();
        HashMap<String, String> errorsMap = new HashMap<>();
        errorsMap.put(parameterName, EduErrorResponse.getLocalMsg(Constants.BussinessErrorMsg.paramLost));
        return EduResponseEntity.buildParamError(Constants.BussinessErrorMsg.paramLost,errorsMap);
    }

    /**
     * 参数类型异常
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        logger.error("Method Argument Type Mismatch Exception", ex);
        String name = ex.getName();
        HashMap<String, String> errorsMap = new HashMap<>();
        errorsMap.put(name, EduErrorResponse.getLocalMsg(Constants.BussinessErrorMsg.paramError));
        return EduResponseEntity.buildParamError(Constants.BussinessErrorMsg.paramError,errorsMap);
    }


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)  //不支持MediaType，如不支持：application/json.
    public EduErrorResponse httpMediaTypeExceptionHandler(HttpMediaTypeException ex) {
        return new EduErrorResponse(ex.getMessage());
    }

    /**
     * 无法解析传入参数，Http消息不可读取异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        if(ex.getCause() instanceof JsonMappingException){
            JsonMappingException err = (JsonMappingException)ex.getCause();
            HashMap<String, String> errorsMap = new HashMap<>();
            for (JsonMappingException.Reference ref : err.getPath()) {
                errorsMap.put(ref.getFieldName(),EduErrorResponse.getLocalMsg(Constants.BussinessErrorMsg.paramNotReadError)+ex.getMessage());
            }
            return EduResponseEntity.buildParamError(Constants.BussinessErrorMsg.paramNotReadError,errorsMap);
        }
        logger.error("httpMessageNotReadableException error", ex);
        return  EduResponseEntity.buildParamError(Constants.BussinessErrorMsg.paramNotReadError);
    }


    /**
     * 运行时异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public EduErrorResponse runtimeExceptionHandler(RuntimeException ex) {
        logger.error("runtimeException error", ex);
        return new EduErrorResponse("runtimeException error");
    }


    /**
     * 空指针异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public EduErrorResponse nullPointerExceptionHandler(NullPointerException ex) {
        logger.error("nullPointerException error", ex);
        return new EduErrorResponse("nullPointerException error");
    }

    /**
     * 服务端异常捕捉处理
     *
     * @param ex
     * @return
     */
//    @ResponseBody
//    @ExceptionHandler(value = ServiceException.class)
//    public ResponseEntity<?> serviceExceptionHandler(ServiceException ex) {
//        logger.error("process the exception from service", ex);
//        return EduResponseEntity.buildServiceBusinessError(ex.getCode(), ex.getMessage());
//    }


    /**
     * 业务异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> businessException(BusinessException ex) {
        logger.error("process the exception from business,{}", ex);
        if (ex.getCode() <= 0) {
            return EduResponseEntity.buildBusinessError(ex.getMessage());
        } else {
            return EduResponseEntity.buildBusinessError(ex.getCode(), ex.getMessage());
        }
    }


    /**
     * rpc异常捕获
     *
     * @param ex
     * @return
     */
//    @ResponseBody
//    @ExceptionHandler(value = RpcException.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public EduErrorResponse rpcExceptionHandler(RpcException ex) {
//        logger.error("process the RPC exception", ex);
//        switch (ex.getCode()) {
//        case RpcException.UNKNOWN_EXCEPTION:
//            return new EduErrorResponse(Constants.BussinessErrorMsg.rcpUnknownError);
//        case RpcException.NETWORK_EXCEPTION:
//            return new EduErrorResponse(Constants.BussinessErrorMsg.rcpNetworkError);
//        case RpcException.TIMEOUT_EXCEPTION:
//            return new EduErrorResponse(Constants.BussinessErrorMsg.rcpTimeoutError);
//        case RpcException.BIZ_EXCEPTION:
//            return new EduErrorResponse(Constants.BussinessErrorMsg.rcpBizError);
//        case RpcException.FORBIDDEN_EXCEPTION:
//            return new EduErrorResponse(Constants.BussinessErrorMsg.rcpForbiddenError);
//        case RpcException.SERIALIZATION_EXCEPTION:
//            return new EduErrorResponse(Constants.BussinessErrorMsg.rcpSerializationError);
//        default:
//            return new EduErrorResponse(Constants.BussinessErrorMsg.rcpUnknownError);
//        }
//    }

    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public EduErrorResponse httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        logger.error("global exception handler", ex);
        return new EduErrorResponse(ex.getMessage());
    }

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public EduErrorResponse errorHandler(Exception ex) {
        logger.error("global exception handler", ex);
        return new EduErrorResponse(Constants.BussinessErrorMsg.error);
    }
}
