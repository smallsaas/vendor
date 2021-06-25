package com.jfeat.am.module.cinema.services.domain.service;

import com.jfeat.am.module.cinema.services.domain.model.CinemaRecord;
import com.jfeat.am.module.cinema.services.gen.crud.model.CinemaModel;
import com.jfeat.am.module.cinema.services.gen.crud.service.CRUDCinemaService;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Cinema;
import com.jfeat.org.services.persistence.model.SysUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vincent on 2017/10/19.
 */
public interface CinemaService extends CRUDCinemaService{

    public Integer createCinema(CinemaRecord cinemaModel);

    @Transactional
    Integer setCinemaUser(Long cinemaId);

    Integer updateCinema(Cinema entity);

    Integer setAssistant(Long id, Long assistantId);

    Integer deleteCinema(Long id);

    Integer  setImportCinemaUser();

    Integer  setImportCinemaUser(Long id);

    Integer changePhone(Long id, String phone);

    Integer repairPCDData();

    CinemaRecord findCinemaDetail(Long id);
}