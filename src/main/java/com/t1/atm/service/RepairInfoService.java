package com.t1.atm.service;

import com.poiji.exception.InvalidExcelFileExtension;
import com.t1.atm.model.AtmRepair;
import org.hibernate.id.IdentifierGenerationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface RepairInfoService {

    long uploadExcel(MultipartFile file) throws IOException, InvalidExcelFileExtension, JpaSystemException;

    void deleteAll();

    List<AtmRepair> getAll();

    List<Map.Entry<String, List<AtmRepair>>> getTop3Reasons();

}
