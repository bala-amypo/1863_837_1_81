package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DisposalRecord;
import com.example.demo.repository.DisposalRecordRepository;
import com.example.demo.service.DisposalRecordService;
import org.springframework.stereotype.Service;

@Service
public class DisposalRecordServiceImpl implements DisposalRecordService {

    private final DisposalRecordRepository disposalRecordRepository;

    public DisposalRecordServiceImpl(DisposalRecordRepository disposalRecordRepository) {
        this.disposalRecordRepository = disposalRecordRepository;
    }

    @Override
    public DisposalRecord createDisposal(DisposalRecord record) {
        return disposalRecordRepository.save(record);
    }

    @Override
    public DisposalRecord getDisposal(Long id) {
        return disposalRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Disposal record not found"));
    }
}
