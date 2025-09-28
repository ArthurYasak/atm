package com.t1.atm.service;

import com.poiji.bind.Poiji;
import com.poiji.exception.InvalidExcelFileExtension;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import com.t1.atm.model.AtmRepairEntity;
import com.t1.atm.model.converter.AtmRepairConverter;
import com.t1.atm.model.dto.AtmRepairData;
import com.t1.atm.repository.RepairInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.poiji.exception.PoijiExcelType.XLS;
import static com.poiji.exception.PoijiExcelType.XLSX;

@Service
@RequiredArgsConstructor
public class RepairInfoServiceImpl implements RepairInfoService {

    private final RepairInfoRepository repairInfoRepo;
    private final AtmRepairConverter atmRepairConverter;

    @Override
    public long uploadExcel(MultipartFile file) throws IOException, InvalidExcelFileExtension, JpaSystemException {
        String fileName = file.getOriginalFilename();
        String extension = Optional.ofNullable(fileName).map(name -> name
                        .substring(fileName.lastIndexOf('.')))
                .orElse("");
        PoijiExcelType excelType;
        switch (extension) {
            case ".xlsx" -> excelType = XLSX;
            case ".xls" -> excelType = XLS;
            default -> throw new InvalidExcelFileExtension("This file is not Excel file.");
        }
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .dateTimeFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy  hh:mm:ss")) // TODO: date outer format (mb dto with Date/time)
                .build();
        List<AtmRepairData> repairs = Poiji.fromExcel(file.getInputStream(), excelType, AtmRepairData.class, options);

        return repairInfoRepo.saveAll(atmRepairConverter.toEntityList(repairs)).size();
    }

    @Override
    public void deleteAll() { // TODO:NOW
        repairInfoRepo.deleteAll();
    }

    @Override
    public List<AtmRepairData> getAll() {
        return atmRepairConverter.toDataList(getAllEntities());
    }

    public List<Map.Entry<String, List<AtmRepairData>>> getTop3Reasons() {
        return atmRepairConverter.toDataList(getAllEntities()).stream()
                .collect(Collectors.groupingBy(AtmRepairData::getReason))
                .entrySet()
                .stream()
                .sorted((o1, o2) ->
                        o2.getValue().size() - o1.getValue().size())
                .limit(3)
                .collect(Collectors.toList()); // todo: TreeMap
    }

    private Map<String, List<AtmRepairData>> getTop3Reasons2() { // todo: how (return Map)
        return getAll().stream()
                .collect(Collectors.groupingBy(AtmRepairData::getReason))
                .entrySet()
                .stream()
                .sorted((o1, o2) ->
                        o2.getValue().size() - o1.getValue().size())
//                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<AtmRepairData> getTop3DurationRepairs() {
        return getAllEntities().stream()
                .sorted(Comparator.comparingLong(repair ->
                                Duration.between(repair.getEndDate(), repair.getStartDate()).getSeconds()))
                .map(atmRepairConverter::toData)
                .limit(3)
                .toList();
    }

    @Override
    public List<AtmRepairData> getRepeatedRepairs() {

        List<AtmRepairData> repeatRepairs = new ArrayList<>();
        Map<String, Map<String, List<AtmRepairEntity>>> repeatRepairsMap = getAllEntities().stream()
                .collect(Collectors.groupingBy(AtmRepairEntity::getReason,
                        Collectors.groupingBy(AtmRepairEntity::getAtmId)));

        for (Map.Entry<String, Map<String, List<AtmRepairEntity>>> reasonEntry : repeatRepairsMap.entrySet()) {
            for (Map.Entry<String, List<AtmRepairEntity>> atmEntry : reasonEntry.getValue().entrySet()) {

                List<AtmRepairEntity> repairs = atmEntry.getValue().stream()
                        .sorted(Comparator.comparing(AtmRepairEntity::getStartDate))
                        .collect(Collectors.toList());
                AtmRepairEntity currentRepair = repairs.get(0);
                repairs.remove(0);
                for (AtmRepairEntity nextRepair : repairs) {

                    Period betweenRepairsPeriod = Period.between(LocalDate.from(currentRepair.getEndDate()),
                            LocalDate.from(nextRepair.getStartDate()));
                    if (betweenRepairsPeriod.getDays() >= 15) {
                        repeatRepairs.add(atmRepairConverter.toData(currentRepair));
                    }
                    currentRepair = nextRepair;
                }
            }
        }
        return repeatRepairs;
    }

    private List<AtmRepairEntity> getAllEntities() {
        return repairInfoRepo.findAll();
    }
}
