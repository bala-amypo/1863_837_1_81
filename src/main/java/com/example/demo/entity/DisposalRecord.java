package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DisposalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Asset asset;

    private String disposalMethod;
    private LocalDateTime disposalDate;

    @ManyToOne
    private User approvedBy;

    private String notes;
    private LocalDateTime createdAt;

    public DisposalRecord() {}

    public DisposalRecord(Long id, Asset asset, String disposalMethod,
                          LocalDateTime disposalDate, User approvedBy,
                          String notes, LocalDateTime createdAt) {
        this.id = id;
        this.asset = asset;
        this.disposalMethod = disposalMethod;
        this.disposalDate = disposalDate;
        this.approvedBy = approvedBy;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Asset getAsset() { return asset; }
    public String getDisposalMethod() { return disposalMethod; }
    public LocalDateTime getDisposalDate() { return disposalDate; }
    public User getApprovedBy() { return approvedBy; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setAsset(Asset asset) { this.asset = asset; }
    public void setDisposalMethod(String disposalMethod) { this.disposalMethod = disposalMethod; }
    public void setDisposalDate(LocalDateTime disposalDate) { this.disposalDate = disposalDate; }
    public void setApprovedBy(User approvedBy) { this.approvedBy = approvedBy; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
