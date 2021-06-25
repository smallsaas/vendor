package com.jfeat.am.module.cinema.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaDao;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaOrgDao;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaUser;
import com.jfeat.am.module.cinema.services.domain.dao.model.CinemaUser;
import com.jfeat.am.module.cinema.services.domain.model.CinemaUserType;
import com.jfeat.am.module.cinema.services.domain.model.OrgCodeType;
import com.jfeat.am.module.cinema.services.domain.service.CinemaOrgService;
import com.jfeat.am.module.cinema.services.domain.service.CinemaUserService;
import com.jfeat.am.module.cinema.services.gen.persistence.dao.AdvertiserMapper;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Advertiser;
import com.jfeat.am.module.cinema.util.CinemaUserUtil;
import com.jfeat.am.uaas.system.services.domain.model.SysUserRequest;
import com.jfeat.am.uaas.system.services.domain.service.SysUserService;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.org.services.domain.service.SysOrgService;
import com.jfeat.org.services.persistence.dao.SysUserMapper;
import com.jfeat.org.services.persistence.model.SysOrg;
import com.jfeat.org.services.persistence.model.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaUserServiceImpl implements CinemaUserService {

    @Resource
    SysUserService sysUserService;
    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    CinemaOrgService cinemaOrgService;

    @Resource
    CinemaUserUtil cinemaUserUtil;
    @Resource
    QueryCinemaOrgDao queryCinemaOrgDao;

    @Resource
    AdvertiserMapper advertiserMapper;

    @Resource
    QueryCinemaUser queryCinemaUser;

    @Resource
    SysOrgService sysOrgService;

    @Override
    public Integer createUser(CinemaUser entity){
        Integer i = 0;

        Long tenantOrgId = JWTKit.getTenantOrgId();
        SysOrg org = sysUserService.getOrgById(tenantOrgId);

        cinemaOrgService.createNormalUser(entity.getOrgId(),tenantOrgId
                ,entity.getPhone(),entity.getName(),org.getbType(),entity.getRoleIds());

        return i;
    }

    @Override
    public SysUser getUserInfo(){
        Long userId = JWTKit.getUserId();
        SysUser userInfo = sysUserService.getById(userId);
        return userInfo;
    }


    @Override
    public Advertiser getAdvInfo(){
        Long tenantOrgId = JWTKit.getTenantOrgId();
        Advertiser advertiser = advertiserMapper.selectOne(new QueryWrapper<Advertiser>().eq("org_id", tenantOrgId));
        return advertiser;
    }

    @Override
    public Integer deleteOrg(Long orgId){
        Integer affeat = 0;
        if(orgId!=null && !OrgCodeType.SYSTEM_ORG_ID.equals(orgId)){
            //删权限组
            affeat += queryCinemaUser.deletePermGroupByOrgId(orgId);
            //删角色用户关联
            affeat += queryCinemaUser.deleteSysRoleByOrgId(orgId);

            //删用户
            //获取当前组织下所有用户
            List<SysUser> orgUsers = queryCinemaUser.getOrgUser(orgId);
            List<Long> ids = orgUsers.stream().map(SysUser::getId).collect(Collectors.toList());
            if (ids != null && ids.size() > 0) {
                affeat += queryCinemaUser.deleteUser(ids);
            }

            //删组织
            affeat += sysOrgService.deleteNode(OrgCodeType.SYSTEM_ORG_ID, orgId);
        }
        return affeat;
    }


    @Override
    public void checkUser(String account){
        SysUser hasUser = sysUserService.checkUserExist(account);
        if(hasUser!=null){
            throw new BusinessException(BusinessCode.CRUD_GENERAL_ERROR,"手机号已被使用");
        }
    }

 /*   //创建店小二和平台主管
    @Override
    @Deprecated
    public Integer createCinemaManager(CinemaUser entity) {
        boolean isKeeper = isKeeper(entity);
        boolean isKeeperManager = isKeeperManager(entity);
        boolean isNormalAccount = isNormalAccount(entity);
        Integer i = 0 ;

        // ShiroKit.hasPermission()
        if(isKeeper){
            //店小二或商务
            Integer managerId = entity.getManagerId();
            if(managerId==null){
                throw new BusinessException(BusinessCode.CRUD_INSERT_FAILURE,"主管id不能为空");
            }else{
                SysUser managerUser = sysUserService.getById(managerId);
                if(managerUser==null){
                    throw new BusinessException(BusinessCode.CRUD_INSERT_FAILURE,"主管id对应的主管不存在");
                }
                List<Long> ids = new ArrayList<>();
                //是否是店小二
                if (entity.getbUserType().equals(CinemaUserType.SYSTEM_SHOPKEEPER)) {
                    ids = CinemaUserRole.ADMIN_SHOPKEEPER_ROLE_IDS;
                }else {
                    ids = CinemaUserRole.ADMIN_CINEMA_ROLE_IDS;
                }
                //创建用户
                cinemaOrgService.createNormalUser(managerUser.getOrgId(),OrgCodeType.SYSTEM_ORG_ID
                        ,entity.getAccount(),entity.getName(),entity.getbUserType(),ids);

            }
        }else if(isKeeperManager){
            //店小二主管 或 商务主管

            //检查orgCode
            cinemaUserUtil.checkOrgCode(entity.getOrgCode());
            JSONObject org = new JSONObject();

            org = cinemaOrgService.createOrg(OrgCodeType.SYSTEM ,entity );
            Long pOrgId = Long.parseLong(org.get("pOrgId").toString());
            Long newOrgId = Long.parseLong(org.get("id").toString());

            List<Long> managerRoleIds = new ArrayList<>();
            String orgCodeType = null;
            //根据不同类型 创建用户和组织
            if(entity.getbUserType().equals(CinemaUserType.SYSTEM_SHOPKEEPER_MANAGER)){
                //店小二主管账户
                managerRoleIds = CinemaUserRole.ADMIN_SHOPKEEPER_MANAGER_ROLE_IDS;
                orgCodeType = OrgCodeType.ADVERTISER_MANAGER_NAME;
            }else {
                //商务主管账户
                managerRoleIds = CinemaUserRole.ADMIN_CINEMA_MANAGER_ROLE_IDS;
                orgCodeType = OrgCodeType.CINEMA_MANAGER_NAME;
            }
                cinemaOrgService.createOrgAdmin(newOrgId, pOrgId, entity.getOrgCode(),
                        entity.getbUserType(), orgCodeType
                        ,managerRoleIds);

            i += Integer.parseInt(org.get("count").toString());
           // entity.setOrgId(Long.parseLong(org.get("id").toString()));
        }else if(isNormalAccount){
            //普通用户
            createCinemaUser(entity);
        }
        return i;
    }*/

    @Deprecated
    @Override
    public List<SysUserRequest> getUserByBUserType(Page<SysUserRequest> page, String type,String search) {
        List<SysUserRequest> userListByOrgId = sysUserService.getUserListByOrgId(page, JWTKit.getOrgId(), search, type);
        return userListByOrgId;
    }

    @Deprecated
    public boolean isKeeper(CinemaUser cinemaUser){
       boolean isKeeper = cinemaUser.getbUserType().equals(CinemaUserType.SYSTEM_SHOPKEEPER)
                        ||cinemaUser.getbUserType().equals(CinemaUserType.SYSTEM_BUSINESS);

        return isKeeper;
    }

    @Deprecated
    public boolean isKeeperManager(CinemaUser cinemaUser){
        boolean isKeeperManager = cinemaUser.getbUserType().equals(CinemaUserType.SYSTEM_SHOPKEEPER_MANAGER)
                ||cinemaUser.getbUserType().equals(CinemaUserType.SYSTEM_BUSINESS_MANAGER);

        return isKeeperManager;
    }

    //判断是否为普通账户
    @Deprecated
    public boolean isNormalAccount(CinemaUser cinemaUser){

        if(cinemaUser.getbUserType().equals(CinemaUserType.SYSTEM_USER)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public SysUser getUserByPhone(String phone){
        SysUser userByPhone = queryCinemaUser.getUserByPhone(phone);
        return userByPhone;
    }

}
