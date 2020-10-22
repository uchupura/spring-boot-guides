package com.guide.multidb.repository.company;

import com.guide.multidb.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
