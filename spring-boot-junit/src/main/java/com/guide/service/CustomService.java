package com.guide.service;

import com.guide.common.enumtype.ExceptionType;
import com.guide.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomService {

    public void getToken() {
        throw new BusinessException(ExceptionType.USER_NOT_FOUND);
    }
}
