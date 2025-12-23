package com.example.demo.repository;

import com.example.demo.entity.TransferRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransferRecordRepository extends JpaRepository<TransferRecord, Long> {
    // Tests expect exactly this name (with underscore)
    List<TransferRecord> findByAsset_Id(Long assetId);
}