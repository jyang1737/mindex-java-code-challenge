package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation for id [{}]", compensation.getEmployee().getEmployeeId());

        compensationRepository.insert(compensation);

        return compensation;
    }

    public Compensation read(String id) {
        LOG.debug("Getting compensation for id [{}]", id);

        Compensation compensation = compensationRepository.findByEmployeeEmployeeId(id);

        if (compensation == null) {
            throw new RuntimeException("No compensation found for " + id);
        }

        return compensation;
    }
}
