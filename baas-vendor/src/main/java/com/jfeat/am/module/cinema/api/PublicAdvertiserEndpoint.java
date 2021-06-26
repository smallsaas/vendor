package com.jfeat.am.module.cinema.api;


import com.jfeat.am.module.cinema.services.domain.dao.QueryAdvertiserDao;
import com.jfeat.am.module.cinema.services.domain.model.AdvertiserDTO;
import com.jfeat.am.module.cinema.services.domain.service.AdvertiserService;
import com.jfeat.am.module.log.annotation.BusinessLog;
import com.jfeat.am.uaas.system.services.domain.service.SysUserService;
import com.jfeat.am.uaas.system.services.domain.service.impl.SysUserServiceImpl;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/sys/oauth/advertiser/advertisers")
public class PublicAdvertiserEndpoint {
    protected final static Logger log = LoggerFactory.getLogger(PublicAdvertiserEndpoint.class);


    @Resource
    AdvertiserService advertiserService;

    @Resource
    QueryAdvertiserDao queryAdvertiserDao;
    @Resource
    SysUserService sysUserService;

    @BusinessLog(name = "广告主", value = "广告主注册申请")
    @PostMapping
    @ApiOperation(value = "广告主注册申请", response = AdvertiserDTO.class)
    public Tip registerAdvertiser(@RequestBody AdvertiserDTO entity) {
        Integer affected = 0;
        try {
            String code = entity.getValidateCode();
            Boolean verBoolean = sysUserService.verifyCode(entity.getContactPhone(), code);
            //验证手机号
            advertiserService.verifyPhone(entity.getContactPhone());

            if(verBoolean){
                affected = advertiserService.createAdvertiser(entity,false);
            }else{
                List<String> strings = sysUserService.genCodeList(entity.getContactPhone(), SysUserServiceImpl.VERIFY_CODE_TIME+1);
                log.info("code：{}",code);
                log.info("有效code: {}",strings);
                throw new BusinessException(BusinessCode.LoginFailure,"验证码错误");
            }

        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey,"该手机号已被注册，请更换一个。");
        }
        return SuccessTip.create(affected);
    }

}
