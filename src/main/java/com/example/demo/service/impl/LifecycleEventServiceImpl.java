package com.example.demo.service.impl;

import com.example.demo.entity.LifecycleEvent;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.LifecycleEventRepository;
import com.example.demo.service.LifecycleEventService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LifecycleEventServiceImpl implements LifecycleEventService {

    private final LifecycleEventRepository lifecycleEventRepository;

    public LifecycleEventServiceImpl(LifecycleEventRepository lifecycleEventRepository) {
        this.lifecycleEventRepository = lifecycleEventRepository;
    }

    @Override
    public LifecycleEvent createEvent(LifecycleEvent event) {
        return lifecycleEventRepository.save(event);
    }

    @Override
    public List<LifecycleEvent> getAllEvents() {
        return lifecycleEventRepository.findAll();
    }

    @Override
    public List<LifecycleEvent> getEventsByAssetId(Long assetId) {
        List<LifecycleEvent> events = lifecycleEventRepository.findByAsset_Id(assetId);
        if (events.isEmpty()) {
            throw new ResourceNotFoundException("Lifecycle events not found");
        }
        return events;
    }

    @Override
    public LifecycleEvent getEvent(Long id) {
        return lifecycleEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lifecycle event not found"));
    }
}
