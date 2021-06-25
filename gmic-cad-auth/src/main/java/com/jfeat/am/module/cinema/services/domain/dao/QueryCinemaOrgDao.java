package com.jfeat.am.module.cinema.services.domain.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Code generator on 2020-09-10
 */
public interface QueryCinemaOrgDao  {
    public String getCodeByCinemaLineId(@Param("id") Long id);

    public Long getManagerId(@Param("roleId")Long roleId);

    //根据用户的orgId获取他pid的code
    public String getOrgCodeByOrgId(@Param("orgId")Long orgId);


}