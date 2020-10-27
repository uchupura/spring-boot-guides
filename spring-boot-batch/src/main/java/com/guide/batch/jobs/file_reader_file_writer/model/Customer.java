package com.guide.batch.jobs.file_reader_file_writer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class Customer {

    private int custNo;
    private String custName;
    private int payment;
}
