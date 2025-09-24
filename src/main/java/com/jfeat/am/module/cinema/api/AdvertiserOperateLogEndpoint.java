package com.jfeat.am.module.cinema.api;


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
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.cinema.services.domain.dao.QueryAdvertiserOperateLogDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.am.module.cinema.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import java.math.BigDecimal;

import com.jfeat.am.module.cinema.services.domain.service.*;
import com.jfeat.am.module.cinema.services.domain.model.AdvertiserOperateLogRecord;
import com.jfeat.am.module.cinema.services.gen.persistence.model.AdvertiserOperateLog;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2020-10-08
 */
@RestController

@Api("AdvertiserOperateLog")
@RequestMapping("/api/crud/advertiserOperateLog/advertiserOperateLogs")
public class AdvertiserOperateLogEndpoint {

    @Resource
    AdvertiserOperateLogService advertiserOperateLogService;

    @Resource
    QueryAdvertiserOperateLogDao queryAdvertiserOperateLogDao;

    @PostMapping
    @ApiOperation(value = "新建 AdvertiserOperateLog", response = AdvertiserOperateLog.class)
    public Tip createAdvertiserOperateLog(@RequestBody AdvertiserOperateLog entity) {

        Integer affected = 0;
        try {
            affected = advertiserOperateLogService.createMaster(entity);

        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查看 AdvertiserOperateLog", response = AdvertiserOperateLog.class)
    public Tip getAdvertiserOperateLog(@PathVariable Long id) {
        return SuccessTip.create(advertiserOperateLogService.queryMasterModel(queryAdvertiserOperateLogDao, id));
    }


    @ApiOperation(value = "AdvertiserOperateLog 列表信息", response = AdvertiserOperateLogRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "advertiserId", dataType = "Long"),
            @ApiImplicitParam(name = "status", dataType = "String"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryAdvertiserOperateLogs(Page<AdvertiserOperateLogRecord> page,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                          @RequestParam(name = "search", required = false) String search,
                                          @RequestParam(name = "id", required = false) Long id,
                                          @RequestParam(name = "note", required = false) String note,
                                          @RequestParam(name = "advertiserId", required = false) Long advertiserId,
                                          @RequestParam(name = "status", required = false) String status,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                          @RequestParam(name = "createTime", required = false) Date createTime,
                                          @RequestParam(name = "orderBy", required = false) String orderBy,
                                          @RequestParam(name = "sort", required = false) String sort) {
        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String pattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(pattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
                }
            } else {
                sort = "ASC";
            }
            orderBy = "`" + orderBy + "`" + " " + sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        AdvertiserOperateLogRecord record = new AdvertiserOperateLogRecord();
        record.setId(id);
        record.setNote(note);
        record.setAdvertiserId(advertiserId);
        record.setStatus(status);
        record.setCreateTime(createTime);
        page.setRecords(queryAdvertiserOperateLogDao.findAdvertiserOperateLogPage(page, record, search, orderBy, null, null));

        return SuccessTip.create(page);
    }
}
