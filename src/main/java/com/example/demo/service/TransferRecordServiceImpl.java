package com.example.demo.service;

import com.example.demo.entity.TransferRecord;
import com.example.demo.repository.TransferRecordRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransferRecordServiceImpl {

    private final TransferRecordRepository repository;

    public TransferRecordServiceImpl(TransferRecordRepository repository) {
        this.repository = repository;
    }

    public TransferRecord save(TransferRecord record) {
        return repository.save(record);
    }

    public List<TransferRecord> getByAssetId(Long assetId) {
        return repository.findByAsset_Id(assetId);
    }
}
