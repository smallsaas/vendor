package com.jfeat.am.module.cinema.menu;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.uaas.perm.services.persistence.model.SysPerm;
import com.jfeat.am.uaas.perm.services.persistence.model.SysPermGroup;

public class MiddlePerm {
    private Long id;
    private Long pid;

    private SysPermGroup sysPermGroup;
    private SysPerm sysPerm;
    private boolean isSysPerm;



    public boolean isSysPerm() {
        return isSysPerm;
    }

    public void setSysPerm(boolean sysPerm) {
        isSysPerm = sysPerm;
    }

    public SysPermGroup getSysPermGroup() {
        return sysPermGroup;
    }

    public void setSysPermGroup(SysPermGroup sysPermGroup) {
        this.sysPermGroup = sysPermGroup;
    }

    public SysPerm getSysPerm() {
        return sysPerm;
    }

    public void setSysPerm(SysPerm sysPerm) {
        this.sysPerm = sysPerm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }


}
