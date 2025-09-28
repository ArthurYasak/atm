package com.t1.atm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtmRepairEntity {

    @Id
    private Long caseId;

    private String atmId;

    private String reason;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String serialNum;

    private String bankName;

    private String linkChannel;
}
