package com.jfeat.am.module.cinema.util;

import com.jfeat.am.uaas.system.services.domain.service.SysUserService;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class OrgCodeUtil {

    @Resource
    SysUserService sysUserService;

    public  boolean checkOrgCode(String orgCode){
        if (orgCode == null) {
            throw new BusinessException(BusinessCode.LoginFailure, "请填写组织编号");
        }
        if(sysUserService.getOrgByCode(orgCode)!=null){
            throw new BusinessException(BusinessCode.LoginFailure, "该组织编号已被注册，请更换一个。");
        }
        return true;
    }


    //根据uuid随机生成code
    public static String genOrgCode(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
