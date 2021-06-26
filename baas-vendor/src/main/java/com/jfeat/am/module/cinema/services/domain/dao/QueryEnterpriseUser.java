package com.jfeat.am.module.cinema.services.domain.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.org.services.persistence.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QueryEnterpriseUser {
    List<SysUser> findAdvertiserPage(Page<SysUser> page, @Param("search") String search
            , @Param("roleCode")String roleCode, @Param("orgId")Long orgId);

    List<SysUser> getOrgUser(@Param("orgId") Long orgId);

    Integer setUserDeleteFlag(@Param("ids")List<Long> ids,@Param("deleteFlag")Long deleteFlag);

    Integer deleteUser(@Param("ids")List<Long> ids);

    Integer deletePermGroupByOrgId(@Param("orgId") Long orgId);

    Integer deleteSysRoleByOrgId(@Param("orgId") Long orgId);

    SysUser getUserByPhone(@Param("phone")String phone);

    Integer updateUserOrgId(@Param("id")Long id,@Param("orgId")Long orgId);

    Integer insertRole(@Param("name")String name,@Param("orgId")Long orgId,@Param("tips")String tips);

    //更改影院登录电话
    Integer updateCinemaPhone(@Param("id")Long id,@Param("phone")String phone);

    //将用户输入的影院城市 与 PCD数据对齐 更新影院数据
     Integer repairPCDData();
     //获取错误的导入影院的id
    Integer updateErrorImportPCDDataCinema();
}
