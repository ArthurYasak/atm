package com.t1.atm.repository;

import com.t1.atm.model.AtmRepairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface RepairInfoRepository extends JpaRepository<AtmRepairEntity, Long> {
}
