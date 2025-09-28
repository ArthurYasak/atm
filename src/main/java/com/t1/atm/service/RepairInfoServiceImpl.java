package com.t1.atm.service;

import com.poiji.bind.Poiji;
import com.poiji.exception.InvalidExcelFileExtension;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import com.t1.atm.model.AtmRepair;
import com.t1.atm.repository.RepairInfoRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.id.IdentifierGenerationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.stream.Collectors;

import static com.poiji.exception.PoijiExcelType.XLS;
import static com.poiji.exception.PoijiExcelType.XLSX;

@Service
@RequiredArgsConstructor
public class RepairInfoServiceImpl implements RepairInfoService {

    private final RepairInfoRepository repairInfoRepo;

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
                .dateTimeFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy  hh:mm:ss")) // TODO: NOT CORRECTLY PARSING DATE
                .build();
        List<AtmRepair> repairs = Poiji.fromExcel(file.getInputStream(), excelType, AtmRepair.class, options);
        repairs = repairInfoRepo.saveAll(repairs);

        return repairs.size();
    }

    @Override
    public void deleteAll() { // TODO:NOW
        repairInfoRepo.deleteAll();
    }

    @Override
    public List<AtmRepair> getAll() {
        return repairInfoRepo.findAll();
    }

    public List<Map.Entry<String, List<AtmRepair>>> getTop3Reasons() {
        return getAll().stream()
                .collect(Collectors.groupingBy(AtmRepair::getReason))
                .entrySet()
                .stream()
                .sorted((o1, o2) ->
                        o2.getValue().size() - o1.getValue().size())
                .limit(3)
                .collect(Collectors.toList()); // todo: TreeMap
    }
}
