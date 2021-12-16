package com.demo.config;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatternConfigService {


    private final PatternConfigRepository patternConfigRepository;

    public PatternConfigService(PatternConfigRepository patternConfigRepository) {
        this.patternConfigRepository = patternConfigRepository;
    }

    public List<PatternConfig> findAllConfig() {
        return patternConfigRepository.findAll();
    }


    public PatternConfig save(PatternConfig patternConfig) {
        PatternConfig ready2Save = patternConfigRepository.findByProductTypeId(patternConfig.getProductTypeId());
        if (null == ready2Save) {
            ready2Save = patternConfig;
        } else {
            ready2Save.setStart(patternConfig.getStart());
            ready2Save.setEnd(patternConfig.getEnd());
            ready2Save.setProductType(patternConfig.getProductType());
        }
        return patternConfigRepository.save(ready2Save);
    }


}
