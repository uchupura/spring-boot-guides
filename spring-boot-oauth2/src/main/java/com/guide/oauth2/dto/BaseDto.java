package com.guide.oauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseDto<T> {
    int count;
    T data;
}
