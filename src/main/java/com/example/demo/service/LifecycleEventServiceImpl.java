package com.example.demo.service;

import com.example.demo.entity.LifecycleEvent;
import com.example.demo.repository.LifecycleEventRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LifecycleEventServiceImpl {

    private final LifecycleEventRepository repository;

    public LifecycleEventServiceImpl(LifecycleEventRepository repository) {
        this.repository = repository;
    }

    public LifecycleEvent save(LifecycleEvent event) {
        return repository.save(event);
    }

    public List<LifecycleEvent> getByAssetId(Long assetId) {
        return repository.findByAsset_Id(assetId);
    }
}
