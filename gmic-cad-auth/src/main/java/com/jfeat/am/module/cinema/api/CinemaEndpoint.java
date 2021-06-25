package com.jfeat.am.module.cinema.api;


import com.jfeat.am.module.cinema.menu.Phone;
import com.jfeat.am.module.cinema.services.domain.dao.model.CinemaUser;
import com.jfeat.am.module.cinema.services.domain.model.AdvertiserDTO;
import com.jfeat.am.module.cinema.services.domain.service.CinemaService;
import com.jfeat.org.services.persistence.model.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.BagUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.am.module.cinema.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import com.jfeat.am.module.cinema.services.domain.model.CinemaRecord;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Cinema;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2020-09-10
 */
@RestController

@Api("Cinema")
@RequestMapping("/api/crud/cinema/cinemas")
public class CinemaEndpoint {


    @Resource
    CinemaService cinemaService;

    @Resource
    QueryCinemaDao queryCinemaDao;

    @BusinessLog(name = "Cinema", value = "create Cinema")
    @Permission(CinemaPermission.CINEMA_NEW)
    @PostMapping
    @ApiOperation(value = "新建 Cinema", response = Cinema.class)
    public Tip createCinema(@RequestBody CinemaRecord entity) {

        Integer affected = 0;
        try {

            affected = cinemaService.createCinema(entity);

        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey,"联系人电话已存在");
        }

        return SuccessTip.create(affected);
    }


    @BusinessLog(name = "分配单个影院账户", value = "为某个影院分配账户")
    @Permission(CinemaPermission.CINEMA_EDIT)
    @PostMapping("/setUser/{id}")
    @ApiOperation(value = "为某个影院分配账户", response = Cinema.class)
    public Tip setCinemaUser( @PathVariable Long id) {

        Integer affected = 0;
        try {

            affected = cinemaService.setCinemaUser(id);

        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @Permission(CinemaPermission.CINEMA_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 Cinema", response = CinemaRecord.class)
    public Tip getCinema(@PathVariable Long id) {
        return SuccessTip.create(cinemaService.findCinemaDetail(id));
    }

    @BusinessLog(name = "Cinema", value = "update Cinema")
    @Permission(CinemaPermission.CINEMA_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 Cinema", response = Cinema.class)
    public Tip updateCinema(@PathVariable Long id, @RequestBody Cinema entity) {
        entity.setId(id);
        /**设置影线名**/
        Integer i = cinemaService.updateCinema(entity);
        return SuccessTip.create(i);
    }

    @BusinessLog(name = "Cinema", value = "delete Cinema")
    @Permission(CinemaPermission.CINEMA_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 Cinema")
    public Tip deleteCinema(@PathVariable Long id)
    {
        return SuccessTip.create(cinemaService.deleteCinema(id));
    }


    @PutMapping("/setAssistant/{id}")
    @Permission(CinemaPermission.CINEMA_SET_ASSISTANT)
    @ApiOperation(value = "设置商务", response = CinemaUser.class)
    public Tip setAssistant(@PathVariable Long id,@RequestBody AdvertiserDTO entity) {

        Integer i = cinemaService.setAssistant(id,entity.getAssistantId());

        return SuccessTip.create(i);
    }


    @BusinessLog(name = "影院", value = "给所有导入的影院分配账号")
    @Permission(CinemaPermission.CINEMA_EDIT)
    @PutMapping("/importCinemaUser")
    @ApiOperation(value = "给所有导入的影院分配账号", response = Cinema.class)
    public Tip setImportCinemaUser() {

        Integer i = 0 ;
        i = cinemaService.setImportCinemaUser();
        return SuccessTip.create(i);
    }


    @BusinessLog(name = "影院", value = "单独分配账号 账号激活")
    @Permission(CinemaPermission.CINEMA_EDIT)
    @PutMapping("/importCinemaUser/{id}")
    @ApiOperation(value = "给影院分配账号", response = Cinema.class)
    public Tip setImportCinemaUserOne(@PathVariable Long id) {
        Integer i = 0 ;
        i = cinemaService.setImportCinemaUser(id);
        if(i==0){throw new BusinessException(BusinessCode.CRUD_UPDATE_FAILURE,"激活失败:手机号不正确 或 手机号已被注册");}
        return SuccessTip.create(i);
    }


    @BusinessLog(name = "影院", value = "修改影院登录电话")
    @Permission(CinemaPermission.CINEMA_EDIT)
    @PutMapping("/changePhone/{id}")
    public Tip changePhone(@PathVariable Long id,@RequestBody Phone cinemaPhone){
        Integer integer = 0 ;
        try{
            integer = cinemaService.changePhone(id, cinemaPhone.getPhone());
            //检查影院电话的用户是否存在 不存在才创建基本账户
        }catch (DuplicateKeyException e){
            throw new BusinessException(BusinessCode.DuplicateKey,"手机号已存在");
        }

        return SuccessTip.create(integer);
    }


    @BusinessLog(name = "影院" , value = "处理导入影院数据")
    @Permission(CinemaPermission.CINEMA_EDIT)
    @PutMapping("/repair/importedData")
    public Tip repairImportedData(){
        Integer integer = cinemaService.repairPCDData();
        return SuccessTip.create(integer);
    }

}
