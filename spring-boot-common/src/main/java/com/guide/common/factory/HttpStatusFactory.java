package com.guide.common.factory;

import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import com.guide.common.enumtype.ExceptionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static com.guide.common.enumtype.ExceptionType.INVALID_PARAM;
import static com.guide.common.enumtype.ExceptionType.USER_NOT_FOUND;


public class HttpStatusFactory {
    private static Map<HttpStatus, List<ExceptionType>> map = new HashMap<>();

    static {
        map.put(BAD_REQUEST, Lists.newArrayList(
                INVALID_PARAM
        ));
        map.put(NOT_FOUND, Lists.newArrayList(
                USER_NOT_FOUND
        ));
        map.put(UNAUTHORIZED, Lists.newArrayList(

        ));
        map.put(INTERNAL_SERVER_ERROR, Lists.newArrayList(

        ));
    }

    public static HttpStatus getStatus(ExceptionType type) {
        return map.entrySet().stream()
                .filter(e -> e.getValue().contains(type))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(OK);
    }
}
