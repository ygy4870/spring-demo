package com.ygy.study.exceptioninternationaldemo.service;

import com.ygy.study.exceptioninternationaldemo.common.Constants;
import com.ygy.study.exceptioninternationaldemo.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public void testException() throws BusinessException {
        throw new BusinessException(Constants.Test.NameEmpty);
    }

}
