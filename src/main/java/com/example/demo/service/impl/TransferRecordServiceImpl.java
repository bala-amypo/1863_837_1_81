package com.example.demo.service;

import com.example.demo.entity.Asset;
import com.example.demo.entity.TransferRecord;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.TransferRecordRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferRecordServiceImpl implements TransferRecordService {

    private final TransferRecordRepository transferRecordRepository;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    public TransferRecordServiceImpl(TransferRecordRepository transferRecordRepository, AssetRepository assetRepository, UserRepository userRepository) {
        this.transferRecordRepository = transferRecordRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TransferRecord createTransfer(Long assetId, TransferRecord record) {
        Asset asset = assetRepository.findById(assetId).orElseThrow(() -> new ResourceNotFoundException("Asset not found"));

        if (record.getTransferDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Transfer date cannot be in the future");
        }

        if (record.getFromDepartment().equals(record.getToDepartment())) {
            throw new ValidationException("From and To departments cannot be the same");
        }
        
        User approvedBy = userRepository.findById(record.getApprovedBy().getId()).orElseThrow(() -> new ResourceNotFoundException("Approver not found"));
        if (!"ADMIN".equals(approvedBy.getRole())) {
            throw new ValidationException("Transfer must be approved by an ADMIN");
        }

        record.setAsset(asset);
        asset.setStatus("TRANSFERRED");
        assetRepository.save(asset);

        return transferRecordRepository.save(record);
    }

    @Override
    public List<TransferRecord> getTransfersForAsset(Long assetId) {
        return transferRecordRepository.findByAssetId(assetId);
    }

    @Override
    public TransferRecord getTransfer(Long id) {
        return transferRecordRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transfer record not found"));
    }
}
