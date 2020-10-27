package com.guide.batch.jobs.file_reader_file_writer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class GoodCustomer {

    private int custNo;
    private String custName;
    private int payment;

    @Builder
    public GoodCustomer(int custNo, String custName, int payment) {
        this.custNo = custNo;
        this.custName = custName;
        this.payment = payment;
    }
}
