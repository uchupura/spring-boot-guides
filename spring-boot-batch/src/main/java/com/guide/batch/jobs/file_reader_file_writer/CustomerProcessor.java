package com.guide.batch.jobs.file_reader_file_writer;

import com.guide.batch.jobs.file_reader_file_writer.model.Customer;
import com.guide.batch.jobs.file_reader_file_writer.model.GoodCustomer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<Customer, GoodCustomer> {

    @Override
    public GoodCustomer process(Customer item) throws Exception {

        return GoodCustomer.builder()
                .custNo(item.getCustNo())
                .custName(item.getCustName())
                .payment(item.getPayment())
                .build();
    }
}
