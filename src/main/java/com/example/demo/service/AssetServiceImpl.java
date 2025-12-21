// src/main/java/com/example/demo/service/AssetServiceImpl.java
@Override
public Asset updateStatus(Long assetId, String status) {   // ← rename
    Asset asset = getAsset(assetId);
    asset.setStatus(status);
    return assetRepository.save(asset);
}