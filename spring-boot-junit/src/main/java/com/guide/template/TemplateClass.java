package com.guide.template;

import com.guide.model.IdAble;

import java.util.List;

public class TemplateClass<T extends IdAble> {
    public T getData(Long id, T data) {
        data.setId(id);
        return data;
    }
}
