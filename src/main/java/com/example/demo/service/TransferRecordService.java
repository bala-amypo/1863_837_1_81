package com.example.demo.service;

import com.example.demo.entity.TransferRecord;

import java.util.List;

public interface TransferRecordService {

    TransferRecord transferAsset(Long assetId, Long fromUserId, Long toUserId);

    List<TransferRecord> getTransfersForAsset(Long assetId);

    TransferRecord getTransfer(Long id);
}
