package com.jfeat.am.module.cinema.services.domain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/*import com.jfeat.am.module.cinema.services.domain.dao.model.EnterpriseUser;*/
import com.jfeat.am.module.cinema.services.gen.persistence.model.Advertiser;
import com.jfeat.am.uaas.system.services.domain.model.SysUserRequest;
import com.jfeat.org.services.persistence.model.SysUser;

import java.util.List;

public interface CinemaUserService {
 /*   Integer createUser(EnterpriseUser entity);*/

/*    public Integer createCinemaManager(EnterpriseUser entity);*/


    SysUser getUserInfo();

    SysUser getUserByPhone(String phone);

    Advertiser getAdvInfo();

    Integer deleteOrg(Long orgId);

    void checkUser(String account);

    public List<SysUserRequest> getUserByBUserType(Page<SysUserRequest> page, String type, String search);
}
