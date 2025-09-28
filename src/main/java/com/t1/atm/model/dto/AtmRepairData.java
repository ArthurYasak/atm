package com.t1.atm.model.dto;

import com.poiji.annotation.ExcelCellName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtmRepairData {

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
