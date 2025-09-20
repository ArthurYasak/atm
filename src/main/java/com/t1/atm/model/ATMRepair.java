package com.t1.atm.model;

import com.poiji.annotation.ExcelCellName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class ATMRepair {

    @Id
    @ExcelCellName("CASE ID")
    private BigDecimal caseId;

    @ExcelCellName("ATM ID")
    private String atmId;

    @ExcelCellName("Название управляющей причины")
    private String reason;

    @ExcelCellName("Начало")
    private LocalDateTime startDate;

    @ExcelCellName("Окончание")
    private LocalDateTime endDate;

    @ExcelCellName("Серийный номер")
    private String serialNum;

    @ExcelCellName("BANK_NM")
    private String bankName;

    @ExcelCellName("Канал связи")
    private String linkChannel;
}
