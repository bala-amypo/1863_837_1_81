package com.example.demo.service.impl;

import com.example.demo.entity.TransferRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TransferRecordRepository;
import com.example.demo.service.TransferRecordService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransferRecordServiceImpl implements TransferRecordService {

    private final TransferRecordRepository transferRecordRepository;

    public TransferRecordServiceImpl(TransferRecordRepository transferRecordRepository) {
        this.transferRecordRepository = transferRecordRepository;
    }

    @Override
    public TransferRecord createTransferRecord(TransferRecord record) {
        return transferRecordRepository.save(record);
    }

    @Override
    public List<TransferRecord> getAllTransferRecords() {
        return transferRecordRepository.findAll();
    }

    @Override
    public List<TransferRecord> getTransferRecordsByAssetId(Long assetId) {
        List<TransferRecord> records = transferRecordRepository.findByAsset_Id(assetId);
        if (records.isEmpty()) {
            throw new ResourceNotFoundException("Transfer records not found");
        }
        return records;
    }

    @Override
    public TransferRecord getTransfer(Long id) {
        return transferRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer record not found"));
    }
}
