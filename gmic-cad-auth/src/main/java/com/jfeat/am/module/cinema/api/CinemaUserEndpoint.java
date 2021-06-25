package com.jfeat.am.module.cinema.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.cinema.api.permission.CinemaLinePermission;
import com.jfeat.am.module.cinema.services.domain.dao.QueryAdvertiserDao;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaUser;
import com.jfeat.am.module.cinema.services.domain.dao.model.CinemaUser;
import com.jfeat.am.module.cinema.services.domain.service.CinemaUserService;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Advertiser;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.am.uaas.system.services.domain.model.SysUserRequest;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.org.services.persistence.model.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("CinemaUserEndpoint")
@RequestMapping("/api/crud/cinema/user")
public class CinemaUserEndpoint {
    @Resource
    CinemaUserService cinemaUserService;
    @Resource
    QueryCinemaUser queryCinemaUser;

    @BusinessLog(name = "cinemaUser", value = "创建用户")
    @Permission(CinemaLinePermission.CINEMALINE_NEW)
    @PostMapping
    @ApiOperation(value = "创建用户", response = CinemaUser.class)
    public Tip createCinemaManager(@RequestBody CinemaUser entity) {
        Integer affected = 0;
            affected = cinemaUserService.createUser(entity);
        return SuccessTip.create(affected);
    }




    @GetMapping
    @ApiOperation(value = "根据角色代码获取", response = CinemaUser.class)
    public Tip getUserList(Page<SysUser> page,
                                   @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(required = false) String search,
                                   @RequestParam(required = true) String roleCode) {
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        List<SysUser> userByBUserType = queryCinemaUser.findAdvertiserPage(page,search,roleCode, JWTKit.getOrgId());
        page.setRecords(userByBUserType);

        return SuccessTip.create(page);
    }

    @GetMapping("/userCenter/userInfo")
    @ApiOperation(value = "获取当前登录用户信息", response = CinemaUser.class)
    public Tip userCenter(){
        SysUser userInfo = cinemaUserService.getUserInfo();
        return SuccessTip.create(userInfo);
    }


    @GetMapping("/userCenter/advInfo")
    @ApiOperation(value = "获取广告主用户信息", response = CinemaUser.class)
    public Tip realNameAuthentication(){
        Advertiser advInfo = cinemaUserService.getAdvInfo();
        return SuccessTip.create(advInfo);
    }




}
