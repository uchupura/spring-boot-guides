package com.guide.batch.jobs.custom_db_chunk_writer;

import com.guide.batch.domain.MemberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Description("DB에서 읽어서 DB로 쓰는 배치")
@Slf4j
@RequiredArgsConstructor
@Configuration(BatchConfigure.JOB_NAME + "Configure")
public class BatchConfigure {

    public static final String JOB_NAME = "CustomDbChunkWriterJob";
    public static final String BEAN_PREFIX = JOB_NAME + "_";

    private static final int chunkSize = 10;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource; // DataSource DI

    private final CustomWriter writer;

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
                .<MemberInfo, MemberInfo>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @StepScope
    @Bean(name = BEAN_PREFIX + "Reader")
    public JdbcPagingItemReader<MemberInfo> reader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add("MANAGER");
        roles.add("USER");
        parameterValues.put("role", roles);

        return new JdbcPagingItemReaderBuilder<MemberInfo>()
                .pageSize(chunkSize)
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(MemberInfo.class))
                .queryProvider(createQueryProvider())
                .parameterValues(parameterValues)
                .name("jdbcPagingItemReader")
                .build();
    }

    @Bean(name = BEAN_PREFIX + "Processor")
    @StepScope
    public ItemProcessor<MemberInfo, MemberInfo> processor() {
        return member -> {
            if(member.getAddress().equals("부산"))
                return member;
            else
                return null;
        };
    }

    /*@Bean(name = BEAN_PREFIX + "Writer")
    public ItemWriter<MemberInfo> writer() {
        return new JdbcBatchItemWriterBuilder<MemberInfo>()
                .dataSource(dataSource)
                .sql("insert into member_info(member_id, name, address, role) values (:memberId, :name, :address, :role)")
                .beanMapped()
                .build();
    }*/

    public PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource); // Database에 맞는 PagingQueryProvider를 선택하기 위해
        queryProvider.setSelectClause("m.id as member_id, m.name, m.address, r.role");
        queryProvider.setFromClause("from member m join role r on m.id = r.member_id");
        queryProvider.setWhereClause("where role in (:role)");
        //queryProvider.setWhereClause("where amount >= :amount");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("member_id", Order.DESCENDING);

        queryProvider.setSortKeys(sortKeys);

        return queryProvider.getObject();
    }
}
