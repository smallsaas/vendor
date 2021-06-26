package com.jfeat.am.module.cinema.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.core.model.UserType;
import com.jfeat.am.module.cinema.services.domain.dao.model.EnterpriseUserSetting;
import com.jfeat.am.module.cinema.services.domain.model.OrgCodeType;
import com.jfeat.am.module.cinema.services.domain.service.CinemaOrgService;
import com.jfeat.am.uaas.system.services.domain.service.SysUserService;
import com.jfeat.am.uaas.system.services.transfer.UserWrapper;
import com.jfeat.org.services.domain.service.SysOrgService;
import com.jfeat.org.services.persistence.model.SysOrg;
import com.jfeat.org.services.persistence.model.SysUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CinemaOrgServiceImpl implements CinemaOrgService {

    @Resource
    SysUserService sysUserService;

    @Resource
    SysOrgService sysOrgService;

    //根据orgCodeType决定父组织
    @Override
    @Transactional
    public <T> JSONObject createOrg(String orgCodeType, T t,String bType){
 /*       Class<?> aClass = t.getClass();
        Class<?> superclass = t.getClass().getSuperclass();*/
        JSONObject jsonObject=new JSONObject();
        Integer i = 0;
        //获取父组织
        SysOrg advertiserOrg = sysUserService.getOrgByCode(orgCodeType);
        if(advertiserOrg == null){
            //如果目标code不存在则创建
            SysOrg pOrg = new SysOrg();
            pOrg.setOrgCode(orgCodeType);
            pOrg.setbType("SYSTEM");
            pOrg.setName(orgCodeType);
            pOrg.setOrgType(0);
            sysOrgService.createNewNode(OrgCodeType.SYSTEM_ORG_ID,pOrg,true);
            advertiserOrg = sysUserService.getOrgByCode(orgCodeType);
        }
        //组织
        SysOrg newOrg = new SysOrg();
        String name = getEntityString(t, "name", "companyName");
        //名字为空 则用手机号代替
        if(name == null) {name = getEntityString(t,"contactPhone");}
        newOrg.setName(name);
        newOrg.setbType(bType);
        newOrg.setFullName(name);
        newOrg.setPid(advertiserOrg.getId());
        newOrg.setNote(getEntityString(t,"note"));
        newOrg.setOrgType(0);
        String orgCode = getEntityString(t, "orgCode","OrgCode");
        newOrg.setOrgCode(orgCode);
        i += sysOrgService.createNewNode(advertiserOrg.getId(), newOrg, true);
        newOrg = sysUserService.getOrgByCode(orgCode);

        jsonObject.put("count",i);
        jsonObject.put("id",newOrg.getId());
        jsonObject.put("pOrgId",advertiserOrg.getId());

        return jsonObject;
    }



    //根据字段名获取字段
    private <T> String getEntityString(T t,String fieldName,String fieldOtherName){
        String value=null;

        Map<String, Field> fieldMap = getFieldMap(t);

        Field endlessField = fieldMap.get(fieldName)!=null?fieldMap.get(fieldName):fieldMap.get(fieldOtherName);
        if(endlessField!=null){
            try {
                Object o = endlessField.get(t);
                if(o == null){
                    value = null;
                }else{
                    value = o.toString();
                }

            } catch (IllegalAccessException e) {
                value = null;
            }
        }

        return value;

    }
    //根据字段名获取字段
    private <T> String getEntityString(T t,String fieldName){
        String value=null;

        Map<String, Field> fieldMap = getFieldMap(t);

        Field endlessField = fieldMap.get(fieldName);
        if(endlessField!=null){
            try {
                Object valueObject = endlessField.get(t);
                if(valueObject!=null){
                    value = valueObject.toString();
                }
                else value = null;

            } catch (IllegalAccessException e) {
                value = null;
            }
        }

        return value;
    }

    //获取字段map 过渡方法
    private <T> Map<String,Field> getFieldMap(T t){
        Field[] fields = t.getClass().getDeclaredFields();
        Map<String,Field> fieldMap=new HashMap();
        for(Field field:fields){
            field.setAccessible(true);
            fieldMap.put(field.getName(),field);
        }
        if(t.getClass().getSuperclass()!=null){
            Field[] supField = t.getClass().getSuperclass().getDeclaredFields();
            for(Field field:supField){
                field.setAccessible(true);
                fieldMap.put(field.getName(),field);
            }
        }
        return fieldMap;
    }

    @Override
    @Deprecated
    public String getCodeByCinemaLineId(Long id) {
        return null;
    }

    @Override
    public SysUser createOrgAdmin(Long orgId, Long tenantOrgId, String account, String phone
            , String userType, String name, List<Long> ids, String password){

        if(password==null){
            password= EnterpriseUserSetting.default_Password;
        }
        Integer i = 0;
        UserWrapper sysUser = new UserWrapper();
        sysUser.setOrgId(orgId);
        sysUser.setTenantOrgId(tenantOrgId);
        sysUser.setAccount(account);
        sysUser.setName(name);
        sysUser.setPhone(phone);
        sysUser.setPassword(password);
        sysUser.setbUserType(userType);
        sysUser.setUserType(UserType.SAAS_ORG_USER);
        sysUser.setRoleIds(ids);
        sysUser.setCheckTree(false);
        SysUser saveUser = sysUserService.saveUser(sysUser);
        return saveUser;
    }

    @Override
    public SysUser createNormalUser(Long orgId, Long tenantOrgId, String phone, String name
            , String userType, List<Long> ids) {
        Integer i = 0;
        UserWrapper sysUser = new UserWrapper();
        sysUser.setOrgId(orgId);
        sysUser.setTenantOrgId(tenantOrgId);
        sysUser.setAccount(phone);
        sysUser.setPhone(phone);
        sysUser.setName(name);
        sysUser.setPassword(EnterpriseUserSetting.default_Password);
        sysUser.setbUserType(userType);
        sysUser.setUserType(UserType.SAAS_ORG_USER);
        sysUser.setRoleIds(ids);
        sysUser.setCheckTree(false);
        SysUser saveUser = sysUserService.saveUser(sysUser);
        return saveUser;
    }


}
