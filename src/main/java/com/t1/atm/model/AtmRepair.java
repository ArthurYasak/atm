package com.t1.atm.model;

import com.poiji.annotation.ExcelCellName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class AtmRepair {

    @Id
    @ExcelCellName("CASE ID")
    private Long caseId;

    @ExcelCellName("ATM ID")
    private String atmId;

    @ExcelCellName("Название управляющей причины")
    private String reason;

    @ExcelCellName("Начало")
    private String startDate;

    @ExcelCellName("Окончание")
    private String endDate;

    @ExcelCellName("Серийный номер")
    private String serialNum;

    @ExcelCellName("BANK_NM")
    private String bankName;

    @ExcelCellName("Канал связи")
    private String linkChannel;
}
