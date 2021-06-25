package com.jfeat.am.module.cinema.services.domain.service;

import com.jfeat.am.uaas.perm.services.persistence.model.SysPerm;
import com.jfeat.am.uaas.perm.services.persistence.model.SysPermGroup;
import com.jfeat.am.uaas.system.services.transfer.RoleWrapper;

import java.util.List;

public interface CinemaInitSQLService {

    Integer initRole(List<String> permList, Long orgId, RoleWrapper roleWrapper);

    void initNoPermRole(Long orgId, RoleWrapper roleWrapper);

    //设置对应角色的权限
    void setRolePerm(List<String> permList, Long orgId);

    List<SysPerm> getPermByPermIdentifier(List<String> permList);



    //获取权限列表 根据identifier列表
    List<Long>  getPermIdsByPermIdentifier(List<String> permList, Long orgId);

    //获取权限组 根据identifier
    List<SysPermGroup> getPermGroup(List<String> permList);
}
