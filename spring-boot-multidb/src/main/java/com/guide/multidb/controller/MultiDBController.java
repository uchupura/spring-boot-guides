package com.guide.multidb.controller;

import com.guide.multidb.service.CompanyService;
import com.guide.multidb.service.TotalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(MultiDBController.URI)
public class MultiDBController {

    public static final String URI = "/api/multis";

    private final TotalService totalService;
    private final CompanyService companyService;

    @GetMapping("create")
    public void create() {
        totalService.create();
    }

    @GetMapping("modify")
    public void modify() {
        totalService.update();
    }
}
