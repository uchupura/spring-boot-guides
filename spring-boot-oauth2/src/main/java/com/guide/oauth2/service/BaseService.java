package com.guide.oauth2.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface BaseService <T, KEY extends Serializable> {

    public List<T> readAll();
    public Page<T> search(Pageable pageable);

    public T create(T dto);
    public T read(KEY id);
    public T update(KEY id, T dto);
    public void delete(KEY id);
}
