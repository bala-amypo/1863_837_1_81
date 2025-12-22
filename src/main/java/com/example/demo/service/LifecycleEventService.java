package com.example.demo.service;

import com.example.demo.entity.LifecycleEvent;
import java.util.List;

public interface LifecycleEventService {

    LifecycleEvent createEvent(LifecycleEvent event);

    List<LifecycleEvent> getAllEvents();

    List<LifecycleEvent> getEventsByAssetId(Long assetId);

    LifecycleEvent getEvent(Long id);
}
