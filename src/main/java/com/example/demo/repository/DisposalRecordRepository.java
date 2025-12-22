package com.example.demo.repository;

import com.example.demo.entity.DisposalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DisposalRecordRepository extends JpaRepository<DisposalRecord, Long> {

    Optional<DisposalRecord> findByAsset_Id(Long assetId);
}
