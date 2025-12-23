package com.example.demo.service;

import com.example.demo.model.Asset;
import java.util.List;

public interface AssetService {

    Asset createAsset(Asset asset);

    Asset getAsset(Long id);

    List<Asset> getAllAssets();

    Asset updateStatus(Long assetId, String status);

    void deleteAsset(Long id);
}
