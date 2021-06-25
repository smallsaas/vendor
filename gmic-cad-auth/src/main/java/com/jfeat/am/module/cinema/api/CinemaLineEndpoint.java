package com.jfeat.am.module.cinema.api;


import com.jfeat.am.module.cinema.services.domain.service.CinemaLineService;
import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaLineDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.am.module.cinema.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import com.jfeat.am.module.cinema.services.domain.model.CinemaLineRecord;
import com.jfeat.am.module.cinema.services.gen.persistence.model.CinemaLine;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;


/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2020-09-10
 */
@RestController

@Api("CinemaLine")
@RequestMapping("/api/crud/cinemaLine/cinemaLines")
public class CinemaLineEndpoint {


    @Resource
    CinemaLineService cinemaLineService;

    @Resource
    QueryCinemaLineDao queryCinemaLineDao;

    @BusinessLog(name = "CinemaLine", value = "新增影线")
    @Permission(CinemaLinePermission.CINEMALINE_NEW)
    @PostMapping
    @ApiOperation(value = "新建 影线", response = CinemaLineRecord.class)
    public Tip createCinemaLine(@RequestBody CinemaLineRecord entity) {
        Integer affected = 0;
        affected = cinemaLineService.createMaster(entity);
        return SuccessTip.create(affected);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查看 CinemaLine", response = CinemaLine.class)
    public Tip getCinemaLine(@PathVariable Long id) {
        return SuccessTip.create(cinemaLineService.queryMasterModel(queryCinemaLineDao, id));
    }

    @BusinessLog(name = "CinemaLine", value = "update CinemaLine")
    @Permission(CinemaLinePermission.CINEMALINE_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 CinemaLine", response = CinemaLine.class)
    public Tip updateCinemaLine(@PathVariable Long id, @RequestBody CinemaLine entity) {
        entity.setId(id);
        return SuccessTip.create(cinemaLineService.updateMaster(entity));
    }

    @BusinessLog(name = "CinemaLine", value = "delete CinemaLine")
    @Permission(CinemaLinePermission.CINEMALINE_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 CinemaLine")
    public Tip deleteCinemaLine(@PathVariable Long id) {
        return SuccessTip.create(cinemaLineService.deleteMaster(id));
    }

    @ApiOperation(value = "CinemaLine 列表信息", response = CinemaLineRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "name", dataType = "String"),
            @ApiImplicitParam(name = "orgId", dataType = "Long"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryCinemaLines(Page<CinemaLineRecord> page,
                                @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                @RequestParam(name = "search", required = false) String search,
                                @RequestParam(name = "id", required = false) Long id,
                                @RequestParam(name = "name", required = false) String name,
                                @RequestParam(name = "orgId", required = false) Long orgId,
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                @RequestParam(name = "createTime", required = false) Date createTime,
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                @RequestParam(name = "updateTime", required = false) Date updateTime,
                                @RequestParam(name = "note", required = false) String note,
                                @RequestParam(name = "orderBy", required = false) String orderBy,
                                @RequestParam(name = "sort", required = false) String sort) {
        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String pattern = "(ASC|DESC|asc|desc)" ;
                if (!sort.matches(pattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
                }
            } else {
                sort = "ASC" ;
            }
            orderBy = "`" + orderBy + "`" + " " + sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        CinemaLineRecord record = new CinemaLineRecord();
        record.setId(id);
        record.setName(name);
        if (META.enabledSaaS()) {
            record.setOrgId(JWTKit.getOrgId());
        }
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);
        record.setNote(note);
        page.setRecords(queryCinemaLineDao.findCinemaLinePage(page, record, search, orderBy, null, null));

        return SuccessTip.create(page);
    }
}
