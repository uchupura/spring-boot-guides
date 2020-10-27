package com.guide.batch.jobs.file_reader_file_writer;

import com.guide.batch.jobs.file_reader_file_writer.model.Customer;
import com.guide.batch.jobs.file_reader_file_writer.model.GoodCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.FileSystemResource;

@Description("File에서 읽어서 File로 쓰는 배치")
@Slf4j
@RequiredArgsConstructor
@Configuration(value = BatchConfigure.JOB_NAME + "Configure")
public class BatchConfigure {

    public static final String JOB_NAME = "File2FileJob";
    public static final String BEAN_PREFIX = JOB_NAME + "_";

    private static final int chunkSize = 10;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final JobParametersIncrementer jobParametersIncrementer;

    @Bean(name = JOB_NAME)
    public Job job() throws Exception {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(jobParametersIncrementer)
                .start(step())
                .build();
    }

    @Bean(name = BEAN_PREFIX + "Step")
    public Step step() throws Exception {
        return stepBuilderFactory.get(BEAN_PREFIX + "Step")
                .<Customer, GoodCustomer>chunk(chunkSize)
                .reader(reader(null))
                .processor(processor())
                .writer(writer(null))
                .build();
    }

    @StepScope
    @Bean(name = BEAN_PREFIX + "Reader")
    public FlatFileItemReader<Customer> reader(@Value("#{jobParameters[input]}") String input) throws Exception {

        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(input));
        reader.setLineMapper(new DefaultLineMapper<Customer>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setDelimiter(",");
                setNames(new String[] {"custNo", "custName", "payment"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {{
                setTargetType(Customer.class);
            }});
        }});

        return reader;
    }

    @Bean(name = BEAN_PREFIX + "Processor")
    @StepScope
    public ItemProcessor<Customer, GoodCustomer> processor() {
        return new CustomerProcessor();
    }

    @Bean(name = BEAN_PREFIX + "Writer")
    @StepScope
    public FlatFileItemWriter<GoodCustomer> writer(@Value("#{jobParameters[output]}") String output) {
        FlatFileItemWriter<GoodCustomer> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(output));
        writer.setLineAggregator(new DelimitedLineAggregator<GoodCustomer>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<GoodCustomer>() {{
                setNames(new String[] {"custNo", "custName", "payment"});
            }});
        }});

        return writer;
    }
}
