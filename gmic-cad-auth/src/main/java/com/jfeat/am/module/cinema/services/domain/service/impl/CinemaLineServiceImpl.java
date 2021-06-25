package com.jfeat.am.module.cinema.services.domain.service.impl;

import com.jfeat.am.module.cinema.services.domain.model.CinemaLineRecord;
import com.jfeat.am.module.cinema.services.domain.service.CinemaLineService;
import com.jfeat.am.module.cinema.services.domain.service.CinemaOrgService;
import com.jfeat.am.module.cinema.services.gen.crud.service.impl.CRUDCinemaLineServiceImpl;
import com.jfeat.am.module.cinema.util.CinemaUserUtil;
import com.jfeat.am.uaas.system.services.domain.service.SysUserService;
import com.jfeat.org.services.domain.service.SysOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service("cinemaLineService")
public class CinemaLineServiceImpl extends CRUDCinemaLineServiceImpl implements CinemaLineService {

    @Resource
    SysUserService sysUserService;

    @Resource
    SysOrgService sysOrgService;
    @Resource
    private CinemaOrgService cinemaOrgService;
    @Resource
    CinemaUserUtil cinemaUserUtil;

    @Override
    public Integer createCinemaLine(CinemaLineRecord cinemaLineRecord) {
         return null;
    }

}
