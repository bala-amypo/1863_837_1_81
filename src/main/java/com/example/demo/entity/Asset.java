package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assetTag;
    private String assetType;
    private String model;
    private LocalDate purchaseDate;
    private String status;

    @ManyToOne
    private User currentHolder;

    private LocalDateTime createdAt;

    public Asset() {}

    public Asset(Long id, String assetTag, String assetType, String model,
                 LocalDate purchaseDate, String status, User currentHolder,
                 LocalDateTime createdAt) {
        this.id = id;
        this.assetTag = assetTag;
        this.assetType = assetType;
        this.model = model;
        this.purchaseDate = purchaseDate;
        this.status = status;
        this.currentHolder = currentHolder;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (status == null) status = "AVAILABLE";
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getAssetTag() { return assetTag; }
    public String getAssetType() { return assetType; }
    public String getModel() { return model; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public String getStatus() { return status; }
    public User getCurrentHolder() { return currentHolder; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setAssetTag(String assetTag) { this.assetTag = assetTag; }
    public void setAssetType(String assetType) { this.assetType = assetType; }
    public void setModel(String model) { this.model = model; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
    public void setStatus(String status) { this.status = status; }
    public void setCurrentHolder(User currentHolder) { this.currentHolder = currentHolder; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
