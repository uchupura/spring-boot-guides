package com.guide.multidb.service;

import com.guide.multidb.entity.company.Company;
import com.guide.multidb.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CompanyService {

    private final CompanyRepository repository;

    public Company get(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void create(Company company) {
        repository.save(company);
    }

    @Transactional
    public void update(Long id) {
        Company company = repository.findById(id).orElse(null);
        company.setName("modified");
        //throw new RuntimeException();
    }
}
