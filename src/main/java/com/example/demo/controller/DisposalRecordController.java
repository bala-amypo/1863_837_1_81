package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.DisposalRecord;
import com.example.demo.service.DisposalRecordService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/disposals")
@Tag(name = "Disposal Record Management")
public class DisposalRecordController {

    private final DisposalRecordService disposalRecordService;

    public DisposalRecordController(DisposalRecordService disposalRecordService) {
        this.disposalRecordService = disposalRecordService;
    }

    @PostMapping("/{assetId}")
    public ResponseEntity<DisposalRecord> createDisposalRecord(@PathVariable Long assetId, @RequestBody DisposalRecord disposal) {
        return ResponseEntity.ok(disposalRecordService.createDisposal(assetId, disposal));
    }

    @GetMapping
    public ResponseEntity<List<DisposalRecord>> getAllDisposals() {
        return ResponseEntity.ok(disposalRecordService.getAllDisposals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisposalRecord> getDisposal(@PathVariable Long id) {
        return ResponseEntity.ok(disposalRecordService.getDisposal(id));
    }
}
