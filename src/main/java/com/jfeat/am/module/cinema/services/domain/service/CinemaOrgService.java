package com.jfeat.am.module.cinema.services.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.org.services.persistence.model.SysUser;

import java.util.List;

public interface CinemaOrgService {
     public <T> JSONObject createOrg(String OrgCodeType, T t,String bType);

     public String getCodeByCinemaLineId(Long id);

     SysUser createOrgAdmin(Long orgId, Long tenantOrgId, String account, String phone
             , String userType, String name, List<Long> ids, String password);

     SysUser createNormalUser(Long orgId, Long tenantOrgId, String orgName, String name
             , String userType, List<Long> ids);
}
