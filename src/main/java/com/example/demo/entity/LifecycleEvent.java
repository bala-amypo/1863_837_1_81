package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LifecycleEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Asset asset;

    private String eventType;
    private String description;
    private LocalDateTime eventDate;

    @ManyToOne
    private User performedBy;

    public LifecycleEvent() {}

    public LifecycleEvent(Long id, Asset asset, String eventType,
                          String description, LocalDateTime eventDate,
                          User performedBy) {
        this.id = id;
        this.asset = asset;
        this.eventType = eventType;
        this.description = description;
        this.eventDate = eventDate;
        this.performedBy = performedBy;
    }

    public Long getId() { return id; }
    public Asset getAsset() { return asset; }
    public String getEventType() {
