package com.guide.excel.api;

import com.guide.excel.OneSheetExcelFile;
import com.guide.excel.model.Car;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ExcelAPI.URL)
public class ExcelAPI {
    public static final String URL = "/v1/excel";

    @GetMapping
    public void download(HttpServletResponse response) throws IOException {
        // 1048575
        ArrayList<Object> cars = Lists.newArrayList();
        cars.add(new Car("현대", "아반떼\n제네시스", 2000, 0.5));
        cars.add(new Car("현대", "소나타", 3000, 0.6));
        cars.add(new Car("기아", "스포티지", 4000, 0.7));
        cars.add(new Car("기아", "K5", 4000, 0.8));

        OneSheetExcelFile<Car> excelFile = new OneSheetExcelFile(cars, Car.class);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + "custom.xlsx" + "\"");
        excelFile.write(response.getOutputStream());
    }

    @GetMapping("/big")
    public void downloadBig(HttpServletResponse response) throws IOException {
        // 1048575
        long beforeTime = System.currentTimeMillis();
        List<Car> cars = Lists.newArrayList();
        OneSheetExcelFile<Car> excelFile = new OneSheetExcelFile(Car.class);
        for (int i = 0; i < 500000; i++) {
            cars.add(new Car("현대", "아반떼"+i, 2000, 0.5));
        }
        excelFile.addRows(cars);
        cars.clear();
        for (int i = 0; i < 500000; i++) {
            cars.add(new Car("현대", "아반떼"+i, 2000, 0.5));
        }
        excelFile.addRows(cars);
        cars.clear();
        for (int i = 0; i < 48500; i++) {
            cars.add(new Car("현대", "아반떼"+i, 2000, 0.5));
        }
        excelFile.addRows(cars);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + "custom.xlsx" + "\"");
        excelFile.write(response.getOutputStream());

        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
        System.out.println("시간차이(m) : "+secDiffTime);
    }
}