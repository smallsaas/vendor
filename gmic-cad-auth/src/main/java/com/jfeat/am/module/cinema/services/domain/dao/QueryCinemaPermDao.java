package com.jfeat.am.module.cinema.services.domain.dao;

import com.jfeat.am.uaas.perm.services.persistence.model.SysPerm;
import com.jfeat.am.uaas.perm.services.persistence.model.SysPermGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QueryCinemaPermDao {
    //根据 identifier 获取权限列表
    List<SysPerm> getPermByList(@Param("identifierList")List<String> identifierList);

    List<SysPermGroup> getPermGroupByPermIdList(@Param("identifierList")List<String> identifierList);

    List<SysPermGroup> getPermGroupByList(@Param("permGroupIds")List<Long> permGroupIds);


    //根据 权限列表 获取权限id列表
    List<Long> getPermIdsByList(@Param("identifierList")List<String> identifierList,@Param("orgId")Long orgId);
}
