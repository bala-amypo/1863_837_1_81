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

    /**
     * Transfer an asset from one user to another
     */
    @PostMapping
    public ResponseEntity<TransferRecord> transferAsset(
            @RequestParam Long assetId,
            @RequestParam Long fromUserId,
            @RequestParam Long toUserId) {

        TransferRecord record =
                transferRecordService.transferAsset(assetId, fromUserId, toUserId);

        return ResponseEntity.ok(record);
    }

    /**
     * Get transfer history of an asset
     */
    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<TransferRecord>> getTransferHistory(@PathVariable Long assetId) {
        return ResponseEntity.ok(
                transferRecordService.getTransfersForAsset(assetId)
        );
    }

    /**
     * Get a specific transfer record
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransferRecord> getTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(
                transferRecordService.getTransfer(id)
        );
    }
}
