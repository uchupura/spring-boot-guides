package com.guide.multidb.service;

import com.guide.multidb.entity.company.Company;
import com.guide.multidb.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TotalService {

    private final UserService userService;
    private final CompanyService companyService;

    @Transactional
    public void create() {
        User user = new User();
        user.setLoginId("uchupura");
        user.setPassword("jang3827");
        user.setName("장지현");
        user.setPhone("01000000000");
        userService.create(user);

        Company company = new Company();
        company.setName("SKP");
        company.setAddress("경기도 성남시");
        companyService.create(company);
    }

    @Transactional
    public void update() {
        userService.update(1L);
        companyService.update(1L);
        /*User user = userService.get(1L);
        user.setName("modified");
        Company company = companyService.get(1L);
        company.setName("modified");*/
    }
}