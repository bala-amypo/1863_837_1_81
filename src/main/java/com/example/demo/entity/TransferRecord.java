package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransferRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Asset asset;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

    private LocalDateTime transferDate;

    @ManyToOne
    private User approvedBy;

    public TransferRecord() {}

    public TransferRecord(Long id, Asset asset, User fromUser,
                          User toUser, LocalDateTime transferDate,
                          User approvedBy) {
        this.id = id;
        this.asset = asset;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.transferDate = transferDate;
        this.approvedBy = approvedBy;
    }

    public Long getId() { return id; }
    public Asset getAsset() { return asset; }
    public User getFromUser() { return fromUser; }
    public User getToUser() { return toUser; }
    public LocalDateTime getTransferDate() { return transferDate; }
    public User getApprovedBy() { return approvedBy; }

    public void setId(Long id) { this.id = id; }
    public void setAsset(Asset asset) { this.asset = asset; }
    public void setFromUser(User fromUser) { this.fromUser = fromUser; }
    public void setToUser(User toUser) { this.toUser = toUser; }
    public void setTransferDate(LocalDateTime transferDate) { this.transferDate = transferDate; }
    public void setApprovedBy(User approvedBy) { this.approvedBy = approvedBy; }
}
