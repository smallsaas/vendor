package com.jfeat.am.module.cinema.services.domain.service;

import com.jfeat.am.module.cinema.services.domain.model.AdvertiserDTO;
import com.jfeat.am.module.cinema.services.domain.model.AdvertiserRecord;
import com.jfeat.am.module.cinema.services.gen.crud.service.CRUDAdvertiserService;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Advertiser;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vincent on 2017/10/19.
 */
public interface AdvertiserService extends CRUDAdvertiserService {

    public Integer createAdvertiser(AdvertiserDTO advertiserRecord, Boolean comeFromSystem);

    public Integer updateAdvertiser(AdvertiserDTO advertiserRecord);

    public Integer deleteAdvertiser(Long id);

    Integer setAssistant(Long id, Long assistantId);

    void createAdvertiserRole(Long id);

    @Transactional
    void setAdvertiserRole(Long id);

    void verifyPhone(String phone);
}