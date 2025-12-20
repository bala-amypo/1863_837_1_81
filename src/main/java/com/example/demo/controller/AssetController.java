package com.example.demo.controller;

import com.example.demo.dto.AssetStatusUpdateRequest;
import com.example.demo.entity.Asset;
import com.example.demo.service.AssetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@Tag(name = "Asset Management")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        return ResponseEntity.ok(assetService.createAsset(asset));
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAsset(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAsset(id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Asset> updateAssetStatus(@PathVariable Long id, @RequestBody AssetStatusUpdateRequest statusUpdateRequest) {
        return ResponseEntity.ok(assetService.updateAssetStatus(id, statusUpdateRequest.getStatus()));
    }
}
