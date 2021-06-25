package com.jfeat.am.module.cinema.services.domain.dao.model;

import com.jfeat.org.services.persistence.model.SysUser;

import java.util.List;

public class CinemaUser extends SysUser {

    private List<Long> roleIds;

    private String orgCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    private Integer managerId ;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}
