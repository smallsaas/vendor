package com.jfeat.am.module.cinema.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.cinema.api.permission.CinemaVendorPermission;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaDao;
import com.jfeat.am.module.cinema.services.domain.model.CinemaRecord;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Cinema;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.META;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

@Api("Cinema")
@RequestMapping("/api/crud/cinema/cinemas")
public class CinemaVendorEndpoint {




    @Resource
    QueryCinemaDao queryCinemaDao;


/*    @Permission(CinemaVendorPermission.CINEMA_VIEW)*/
    @ApiOperation(value = "Cinema 列表信息", response = CinemaRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "name", dataType = "String"),
            @ApiImplicitParam(name = "province", dataType = "String"),
            @ApiImplicitParam(name = "city", dataType = "String"),
            @ApiImplicitParam(name = "area", dataType = "String"),
            @ApiImplicitParam(name = "lineId", dataType = "Integer"),
            @ApiImplicitParam(name = "qualificationNumber", dataType = "String"),
            @ApiImplicitParam(name = "roomNumber", dataType = "Integer"),
            @ApiImplicitParam(name = "seatNumber", dataType = "Integer"),
            @ApiImplicitParam(name = "contactName", dataType = "String"),
            @ApiImplicitParam(name = "contact", dataType = "String"),
            @ApiImplicitParam(name = "businessArea", dataType = "String"),
            @ApiImplicitParam(name = "address", dataType = "String"),
            @ApiImplicitParam(name = "orgId", dataType = "Long"),
            @ApiImplicitParam(name = "note", dataType = "String"),
            @ApiImplicitParam(name = "createTime", dataType = "Date"),
            @ApiImplicitParam(name = "updateTime", dataType = "Date"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryCinemas(Page<CinemaRecord> page,
                            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(name = "search", required = false) String search,
                            @RequestParam(name = "id", required = false) Long id,
                            @RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "province", required = false) String province,
                            @RequestParam(name = "city", required = false) String city,
                            @RequestParam(name = "citys", required = false) String citys,
                            @RequestParam(name = "area", required = false) String area,
                            @RequestParam(name = "lineId", required = false) Long lineId,
                            @RequestParam(name = "qualificationNumber", required = false) String qualificationNumber,
                            @RequestParam(name = "roomNumber", required = false) Integer roomNumber,
                            @RequestParam(name = "seatNumber", required = false) Integer seatNumber,
                            @RequestParam(name = "contactName", required = false) String contactName,
                            @RequestParam(name = "contact", required = false) String contact,
                            @RequestParam(name = "businessArea", required = false) String businessArea,
                            @RequestParam(name = "address", required = false) String address,
                            @RequestParam(name = "orgId", required = false) Long orgId,
                            @RequestParam(name = "note", required = false) String note,
                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                            @RequestParam(name = "createTime", required = false) Date createTime,
                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                            @RequestParam(name = "updateTime", required = false) Date updateTime,
                            @RequestParam(name = "orderBy", required = false) String orderBy,
                            @RequestParam(name = "sort", required = false) String sort,
                            @RequestParam(name = "district",required = false)String district,
                            @RequestParam(name = "lineName",required = false) String lineName,
                            @RequestParam(name = "onlySearch",required = false)Boolean onlySearch,
                            @RequestParam(name = "Location",required = false) String Location) {
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

        String[] cityArray = null ;
        if(citys!=null && !StringUtils.isEmpty(citys)){
            cityArray = citys.split(",");
        }

        //处理省市区数据 从location中获取
        if(!StringUtils.isEmpty(Location)){
            String[] locationArray = Location.split("-");
            province = locationArray[0];
            if(locationArray.length>1){
                city = locationArray[1];
                if(locationArray.length>2){
                    district  = locationArray[2];
                }
            }
        }


        CinemaRecord record = new CinemaRecord();
        record.setId(id);
        record.setName(name);
        record.setProvince(province);
        record.setArea(area);
        record.setLineId(lineId);
        record.setQualificationNumber(qualificationNumber);
        record.setRoomNumber(roomNumber);
        record.setSeatNumber(seatNumber);
        record.setContactName(contactName);
        record.setContact(contact);
        record.setBusinessArea(businessArea);
        record.setAddress(address);
        record.setDistrict(district);
        record.setLineName(lineName);
        record.setCity(city);
        Integer userType = null;
        Long    userId = null;

        if (META.enabledSaaS()&&(onlySearch==null||!onlySearch)) {
            record.setOrgId(JWTKit.getOrgId());
            userType = JWTKit.getUserType();
            userId = JWTKit.getUserId();
        }
        record.setNote(note);
        record.setCreateTime(createTime);
        record.setUpdateTime(updateTime);

        page.setRecords(queryCinemaDao.findCinemaPage(page, record, search, orderBy, null, null,cityArray,userType,userId));

        return SuccessTip.create(page);
    }




}
