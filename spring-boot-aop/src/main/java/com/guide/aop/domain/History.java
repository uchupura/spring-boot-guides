package com.guide.aop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private long userIdx;
    private LocalDateTime updateDate;

    public History(long userIdx, LocalDateTime updateDate) {
        this.userIdx = userIdx;
        this.updateDate = updateDate;
    }
}
