package com.jfeat.am.module.cinema.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.cinema.menu.CinemaPermList;
import com.jfeat.am.module.cinema.services.domain.dao.model.CinemaUser;
import com.jfeat.am.module.cinema.services.domain.model.AdvertiserDTO;
import com.jfeat.am.module.cinema.services.domain.model.AdvertiserRecord;
import com.jfeat.am.module.cinema.services.domain.model.AdvertiserStatus;
import com.jfeat.am.module.cinema.services.domain.model.CinemaUserType;
import com.jfeat.am.module.cinema.services.domain.service.AdvertiserService;
import com.jfeat.am.module.cinema.services.gen.persistence.dao.AdvertiserMapper;
import com.jfeat.am.module.cinema.services.gen.persistence.dao.AdvertiserOperateLogMapper;
import com.jfeat.am.module.cinema.services.gen.persistence.model.AdvertiserOperateLog;
import com.jfeat.am.uaas.perm.services.domain.model.SysRoleMadeByType;
import com.jfeat.am.uaas.perm.services.persistence.model.SysRole;
import com.jfeat.am.uaas.system.services.transfer.RoleWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.cinema.services.domain.dao.QueryAdvertiserDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.am.module.cinema.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import com.jfeat.am.module.cinema.services.domain.service.FieldImageService;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Advertiser;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

@Api("Advertiser")
@RequestMapping("/api/crud/advertiser/advertisers")
public class AdvertiserEndpoint {


    @Resource
    AdvertiserService advertiserService;

    @Resource
    QueryAdvertiserDao queryAdvertiserDao;
    @Resource
    AdvertiserMapper advertiserMapper;

    @Resource
    AdvertiserOperateLogMapper advertiserOperateLogMapper;

    @BusinessLog(name = "广告主", value = "平台新增 广告主")
    @Permission(AdvertiserPermission.ADVERTISER_NEW)
    @PostMapping
    @ApiOperation(value = "平台新建广告主", response = AdvertiserDTO.class)
    public Tip createAdvertiser(@RequestBody AdvertiserDTO entity) {
        Integer affected = 0;
        try {
            affected = advertiserService.createAdvertiser(entity,true);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey,"该手机号已被注册，请更换一个。");
        }
        return SuccessTip.create(affected);
    }

    @Permission(AdvertiserPermission.ADVERTISER_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 广告主", response = Advertiser.class)
    public Tip getAdvertiser(@PathVariable Long id) {
        AdvertiserRecord detail = queryAdvertiserDao.findDetail(id);
        StringBuilder stringBuilder=new StringBuilder();
        if(detail.getxIndustry()!=null){stringBuilder.append(detail.getxIndustry());stringBuilder.append(" ");}
        if(detail.getlIndustry()!=null){stringBuilder.append(detail.getlIndustry());stringBuilder.append(" ");}
        if(detail.getmIndustry()!=null){stringBuilder.append(detail.getmIndustry());stringBuilder.append(" ");}
        detail.setIndustry(stringBuilder.toString());
        return SuccessTip.create(detail);
    }

    @BusinessLog(name = "广告主", value = "更新 广告主")
    @Permission(AdvertiserPermission.ADVERTISER_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 广告主", response = AdvertiserDTO.class)
    public Tip updateAdvertiser(@PathVariable Long id, @RequestBody AdvertiserDTO entity) {
        entity.setId(id);
        return SuccessTip.create(advertiserService.updateAdvertiser(entity));
    }

    @BusinessLog(name = "Advertiser", value = "delete Advertiser")
    @Permission(AdvertiserPermission.ADVERTISER_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 Advertiser")
    public Tip deleteAdvertiser(@PathVariable Long id)
    {
        return SuccessTip.create(advertiserService.deleteAdvertiser(id));
    }



    //广告主审核
    @BusinessLog(name = "广告主", value = "广告主审核")
    @Permission(AdvertiserPermission.ADVERTISER_EDIT)
    @PutMapping("/pass/{id}/{status}")
    @ApiOperation(value = "广告主审核", response = AdvertiserDTO.class)
    public Tip passAdvertiser(@PathVariable Long id,@PathVariable String status,
                              @RequestBody AdvertiserOperateLog advertiserOperateLog) {
        //如果是审核通过 检查是否有分配店小二
        Advertiser oldAdv = advertiserMapper.selectById(id);
        boolean isPass = status.equals(AdvertiserStatus.PASS);
        if(isPass && StringUtils.isEmpty(oldAdv.getCompanyName())){
            throw new BusinessException(BusinessCode.ErrorStatus,"该广告主未填写企业名");
        }

        if(isPass && (oldAdv.getAssistantId()==null||oldAdv.getAssistantId()==0L)){
            throw new BusinessException(BusinessCode.ErrorStatus,"该广告主未分配店小二");
        }
        //更新状态
        Advertiser entity = new Advertiser();
        entity.setId(id);
        entity.setStatus(status);
        int i = advertiserMapper.updateById(entity);
        //如果是审核通过 则分配广告主角色
        if(isPass && i > 0){
            advertiserService.setAdvertiserRole(id);
        }
        //审核相关
        advertiserOperateLog.setStatus(status);
        advertiserOperateLog.setAdvertiserId(id);
        i += advertiserOperateLogMapper.insert(advertiserOperateLog);

        return SuccessTip.create(i);
    }

    @Permission(AdvertiserPermission.ADVERTISER_VIEW)
    @GetMapping("/operateLog/{id}")
    @ApiOperation(value = "查看广告主审核日志", response = Advertiser.class)
    public Tip getAdvertiserLog(@PathVariable Long id) {
        List<AdvertiserOperateLog> logList = advertiserOperateLogMapper.selectLogList(id);

        return SuccessTip.create(logList);
    }



    //    /setAssistant/(id)

    @PutMapping("/setAssistant/{id}")
    @Permission(AdvertiserPermission.ADVERTISER_SET_ASSISTANT)
    @ApiOperation(value = "设置店小二", response = CinemaUser.class)
    public Tip setAssistant(@PathVariable Long id,@RequestBody AdvertiserDTO entity) {
        Integer i = advertiserService.setAssistant(id,entity.getAssistantId());
        return SuccessTip.create(i);
    }


    @BusinessLog(name = "广告主", value = "广告主修改自身信息")
    @PutMapping("/self")
    @ApiOperation(value = "广告主修改自身信息", response = AdvertiserDTO.class)
    public Tip updateAdvertiserSelf( @RequestBody AdvertiserDTO entity) {
        Long tenantOrgId = JWTKit.getTenantOrgId();
        Advertiser advertiser = advertiserMapper.selectOne(new QueryWrapper<Advertiser>()
                .eq("org_id", tenantOrgId));
        entity.setId(advertiser.getId());
        return SuccessTip.create(advertiserService.updateAdvertiser(entity));
    }

}
