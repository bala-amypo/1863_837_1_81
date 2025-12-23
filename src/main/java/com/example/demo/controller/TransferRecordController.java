package com.example.demo.controller;

import com.example.demo.entity.TransferRecord;
import com.example.demo.service.TransferRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
@Tag(name = "Transfer Record Management")
public class TransferRecordController {

    private final TransferRecordService transferRecordService;

    public TransferRecordController(TransferRecordService transferRecordService) {
        this.transferRecordService = transferRecordService;
    }

    @PostMapping("/{assetId}")
    public ResponseEntity<TransferRecord> createTransferRecord(
            @PathVariable Long assetId,
            @RequestBody TransferRecord record) {
        return ResponseEntity.ok(transferRecordService.createTransfer(assetId, record));
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<TransferRecord>> getTransferHistory(@PathVariable Long assetId) {
        return ResponseEntity.ok(transferRecordService.getTransfersForAsset(assetId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferRecord> getTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(transferRecordService.getTransfer(id));
    }
}