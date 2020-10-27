package com.guide.batch.jobs.custom_db_chunk_writer;

import com.guide.batch.domain.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@StepScope
@RequiredArgsConstructor
@Component
public class CustomWriter implements ItemWriter<MemberInfo> {

    private final JdbcTemplate jdbcTemplate;
    private final CustomJobParameter jobParameter;

    private final String SQL = "INSERT INTO member_info (member_id, name, address, role) VALUES (?,?,?,?)";

    @Override
    public void write(List<? extends MemberInfo> items) throws Exception {

        System.out.println(jobParameter.getVersion());
        System.out.println(jobParameter.getStatus());
        jdbcTemplate.batchUpdate(SQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                MemberInfo memberInfo = items.get(i);
                ps.setLong(1, memberInfo.getMemberId());
                ps.setString(2, memberInfo.getName());
                ps.setString(3, memberInfo.getAddress());
                ps.setString(4, memberInfo.getRole());
            }

            @Override
            public int getBatchSize() {
                return items.size();
            }
        });
    }
}
