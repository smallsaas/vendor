package com.jfeat.am.module.cinema.services.domain.service;

import com.jfeat.am.module.cinema.services.domain.model.CinemaLineRecord;
import com.jfeat.am.module.cinema.services.gen.crud.service.CRUDCinemaLineService;

/**
 * Created by vincent on 2017/10/19.
 */
public interface CinemaLineService extends CRUDCinemaLineService {
    public Integer  createCinemaLine(CinemaLineRecord cinemaLineRecord);
}