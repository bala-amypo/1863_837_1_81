package com.example.demo.service;

import com.example.demo.entity.TransferRecord;
import java.util.List;

public interface TransferRecordService {

    TransferRecord createTransferRecord(TransferRecord record);

    List<TransferRecord> getAllTransferRecords();

    List<TransferRecord> getTransferRecordsByAssetId(Long assetId);

    TransferRecord getTransfer(Long id);
}
