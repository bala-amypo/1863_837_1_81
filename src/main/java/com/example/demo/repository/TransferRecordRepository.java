package com.example.demo.repository;

import com.example.demo.entity.TransferRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRecordRepository extends JpaRepository<TransferRecord, Long> {

    // CRITICAL: Must be findByAsset_Id (with underscore)
    List<TransferRecord> findByAsset_Id(Long assetId);
}