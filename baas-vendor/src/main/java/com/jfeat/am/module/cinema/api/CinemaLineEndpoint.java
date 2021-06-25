package com.jfeat.am.module.cinema.api;

import com.jfeat.am.module.cinema.services.gen.persistence.dao.CinemaLineMapper;
import com.jfeat.am.module.cinema.services.gen.persistence.model.CinemaLine;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crud/cinemaLine")
public class CinemaLineEndpoint {
    CinemaLineMapper cinemaLineMapper;

    //public static final String CINEMA_LINE_ORG_ID ;

    @PostMapping("/")
    public Tip createCinemaLine(@RequestBody CinemaLine cinemaLine){
        //cinemaLine.setOrgId();
        cinemaLineMapper.insert(cinemaLine);

        return SuccessTip.create();
    }
}
