package com.example.demo.controller;

import com.example.demo.entity.TransferRecord;
import com.example.demo.service.TransferRecordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferRecordController {

    private final TransferRecordService transferRecordService;

    public TransferRecordController(TransferRecordService transferRecordService) {
        this.transferRecordService = transferRecordService;
    }

    @PostMapping
    public TransferRecord createTransfer(@RequestBody TransferRecord record) {
        return transferRecordService.createTransferRecord(record);
    }

    @GetMapping
    public List<TransferRecord> getAllTransfers() {
        return transferRecordService.getAllTransferRecords();
    }

    @GetMapping("/asset/{assetId}")
    public List<TransferRecord> getTransfersByAsset(@PathVariable Long assetId) {
        return transferRecordService.getTransferRecordsByAssetId(assetId);
    }

    @GetMapping("/{id}")
    public TransferRecord getTransfer(@PathVariable Long id) {
        return transferRecordService.getTransfer(id);
    }
}
