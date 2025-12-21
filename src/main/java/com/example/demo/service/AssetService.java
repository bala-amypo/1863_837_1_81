// src/main/java/com/example/demo/service/AssetService.java
public interface AssetService {
    Asset createAsset(Asset asset);
    Asset getAsset(Long id);
    List<Asset> getAllAssets();
    Asset updateStatus(Long assetId, String status);   // ← rename method
}