package com.example.demo.controller;

import com.example.demo.entity.LifecycleEvent;
import com.example.demo.service.LifecycleEventService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lifecycle-events")
public class LifecycleEventController {

    private final LifecycleEventService lifecycleEventService;

    public LifecycleEventController(LifecycleEventService lifecycleEventService) {
        this.lifecycleEventService = lifecycleEventService;
    }

    @PostMapping
    public LifecycleEvent createEvent(@RequestBody LifecycleEvent event) {
        return lifecycleEventService.createEvent(event);
    }

    @GetMapping
    public List<LifecycleEvent> getAllEvents() {
        return lifecycleEventService.getAllEvents();
    }

    @GetMapping("/asset/{assetId}")
    public List<LifecycleEvent> getEventsByAsset(@PathVariable Long assetId) {
        return lifecycleEventService.getEventsByAssetId(assetId);
    }

    @GetMapping("/{id}")
    public LifecycleEvent getEvent(@PathVariable Long id) {
        return lifecycleEventService.getEvent(id);
    }
}
