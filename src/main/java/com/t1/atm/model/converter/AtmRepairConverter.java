package com.t1.atm.model.converter;

import com.t1.atm.model.AtmRepairEntity;
import com.t1.atm.model.dto.AtmRepairData;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class AtmRepairConverter {

    public AtmRepairEntity toEntity(AtmRepairData data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy H:mm");
        return AtmRepairEntity.builder()
                .caseId(data.getCaseId())
                .atmId(data.getAtmId())
                .reason(data.getReason())
                .startDate(LocalDateTime.parse(Optional.ofNullable(data.getStartDate()).orElse(""), formatter))
                .endDate(LocalDateTime.parse(Optional.ofNullable(data.getEndDate()).orElse(""), formatter))
                .serialNum(data.getSerialNum())
                .bankName(data.getBankName())
                .linkChannel(data.getLinkChannel())
                .build();
    }

    public List<AtmRepairEntity> toEntityList(List<AtmRepairData> dataList) {
        return dataList.stream()
                .map(this::toEntity)
                .toList();
    }

    public com.t1.atm.model.dto.AtmRepairData toData(AtmRepairEntity entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");
        return com.t1.atm.model.dto.AtmRepairData.builder()
                .caseId(entity.getCaseId())
                .atmId(entity.getAtmId())
                .reason(entity.getReason())
                .startDate(Optional.ofNullable(entity.getStartDate())
                        .map(date -> date.format(formatter)).orElse(""))
                .endDate(Optional.ofNullable(entity.getEndDate())
                        .map(date -> date.format(formatter)).orElse(""))
                .serialNum(entity.getSerialNum())
                .bankName(entity.getBankName())
                .linkChannel(entity.getLinkChannel())
                .build();
    }

    public List<AtmRepairData> toDataList(List<AtmRepairEntity> entityList) {
        return entityList.stream()
                .map(this::toData)
                .toList();
    }
}
