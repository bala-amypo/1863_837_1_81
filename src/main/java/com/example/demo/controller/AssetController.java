// src/main/java/com/example/demo/controller/AssetController.java
@PutMapping("/status/{id}")
public ResponseEntity<Asset> updateAssetStatus(@PathVariable Long id, @RequestBody AssetStatusUpdateRequest statusUpdateRequest) {
    return ResponseEntity.ok(assetService.updateStatus(id, statusUpdateRequest.getStatus())); // ← changed
}