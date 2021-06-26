package com.jfeat.am.module.cinema.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.cinema.menu.CinemaUserRole;
import com.jfeat.am.module.cinema.services.domain.dao.QueryEnterpriseUser;
import com.jfeat.am.module.cinema.services.domain.model.*;
import com.jfeat.am.module.cinema.services.domain.service.AdvertiserService;
import com.jfeat.am.module.cinema.services.domain.service.CinemaOrgService;
import com.jfeat.am.module.cinema.services.domain.service.CinemaUserService;
import com.jfeat.am.module.cinema.services.gen.crud.service.impl.CRUDAdvertiserServiceImpl;
import com.jfeat.am.module.cinema.services.gen.persistence.dao.AdvertiserMapper;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Advertiser;
import com.jfeat.am.module.cinema.util.OrgCodeUtil;
import com.jfeat.am.uaas.perm.services.persistence.dao.SysRoleMapper;
import com.jfeat.am.uaas.perm.services.persistence.model.SysRole;
import com.jfeat.am.uaas.system.services.domain.service.SysUserService;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUD;
import com.jfeat.org.services.domain.model.SysOrgBType;
import com.jfeat.org.services.domain.service.SysOrgService;
import com.jfeat.org.services.persistence.dao.SysUserMapper;
import com.jfeat.org.services.persistence.model.SysUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.jfeat.am.module.cinema.menu.GenAccountString.ADV_USER_ACCOUNT;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service("advertiserService")
public class AdvertiserServiceImpl extends CRUDAdvertiserServiceImpl implements AdvertiserService {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysOrgService sysOrgService;
    @Resource
    private AdvertiserMapper advertiserMapper;
    @Resource
    private CinemaOrgService cinemaOrgService;
/*    @Resource
    private QueryCinemaOrgDao queryCinemaOrgDao;*/
/*    @Resource
    CinemaInitSQLService cinemaInitSQLService;*/

    @Resource
    OrgCodeUtil cinemaUserUtil;

    @Resource
    SysRoleMapper sysRoleMapper;
    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    QueryEnterpriseUser queryEnterpriseUser;

    @Resource
    CinemaUserService cinemaUserService;



    @Override
    @Transactional
    public Integer createAdvertiser(AdvertiserDTO advertiserDTO, Boolean comeFromSystem) {

        Integer i = 0;
        //验证手机号
        verifyPhone(advertiserDTO.getContactPhone());
        //随机生成orgCode
        advertiserDTO.setOrgCode(OrgCodeUtil.genOrgCode());

        //设置来自 平台 或 来自用户自己注册
        //设置 店小二id 或者 主管id
        if(comeFromSystem){
            advertiserDTO.setComeFrom(AdvertiserStatus.COME_FROM_SYSTEM);
            advertiserDTO.setAssistantId(JWTKit.getUserId());
        }else
        {
            //注册的话设置广告主默认名字
            advertiserDTO.setCompanyName("总组织");
            advertiserDTO.setComeFrom(AdvertiserStatus.COME_FROM_USER);
            advertiserDTO.setAssistantId(0L);
        }
        //*****************************************************************************************

        //调用方法创建组织
        JSONObject org = cinemaOrgService.createOrg (OrgCodeType.ADVERTISER ,advertiserDTO,SysOrgBType.USER);
        Long pOrgId = Long.parseLong(org.get("pOrgId").toString());
        Long newOrgId = Long.parseLong(org.get("id").toString());

        //获得审核前的角色
        SysRole sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>()
                .eq("org_id", OrgCodeType.SYSTEM_ORG_ID)
                .eq("role_code", UserType.ADVERTISERS_TENANT.toString()));
        List<Long> ids = List.of(sysRole.getId());

        //创建基本账户 自身id作为租户id
        cinemaOrgService.createOrgAdmin(newOrgId, newOrgId, sysUserService.genAccountByString(ADV_USER_ACCOUNT),
                advertiserDTO.getContactPhone(),
                SysOrgBType.USER, OrgCodeType.ADVERTISER_ADMIN_NAME,ids,advertiserDTO.getPassword());

        //*****************************************************************************************


        i += Integer.parseInt(org.get("count").toString());


        //联系人id不为空 查用户表 设置联系人名字和电话
        setContactInfo(advertiserDTO);

        advertiserDTO.setOrgId(newOrgId);
        //广告主信息表 创建广告主的信息
        //注册账户 公司名设空
        if(!comeFromSystem){advertiserDTO.setCompanyName(null);}
        i += advertiserMapper.insert(advertiserDTO);

        return i;
    }

    @Override
    public Integer updateAdvertiser(AdvertiserDTO advertiserRecord) {
        Integer i = 0 ;
        //设置联系人
        setContactInfo(advertiserRecord);
        //设置状态为待审核
        advertiserRecord.setStatus(AdvertiserStatus.PENDING_APPROVAL);
        Advertiser advertiser = CRUD.castObject(advertiserRecord, Advertiser.class);
        i += advertiserMapper.updateById(advertiser);
        return i;
    }

    @Override
    @Transactional
    public Integer deleteAdvertiser(Long id) {
        Advertiser advertiser = advertiserMapper.selectById(id);
        Long orgId = advertiser.getOrgId();

       Integer affeat = 0;
        if(AdvertiserStatus.PASS.equals(advertiser.getStatus())){
                throw new BusinessException(BusinessCode.CRUD_DELETE_FAILURE,
                        "删除失败，审核通过的广告主不能删除");
            }

        //删除组织 角色 用户 权限
        affeat += cinemaUserService.deleteOrg(orgId);

        affeat +=advertiserMapper.deleteById(advertiser);

        return affeat;
    }

    @Override
    public Integer setAssistant(Long id, Long assistantId) {
        Advertiser advertiser = new Advertiser();
        advertiser.setAssistantId(assistantId);
        advertiser.setId(id);
        int i = advertiserMapper.updateById(advertiser);
        return i;
    }

    /*@Override
    @Transactional
    public void createAdvertiserRole(Long id) {
        Advertiser advertiser = advertiserMapper.selectById(id);

        //设置角色权限
        cinemaInitSQLService.setRolePerm(CinemaPermList.ADVERTISER_PERM_GROUP,advertiser.getOrgId());

      *//*  //获取角色
        SysRole sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>()
                .eq("org_id", advertiser.getOrgId())
                .eq("role_code", UserType.ADVERTISERS_TENANT.toString()));
        List<Long> ids = List.of(sysRole.getId());
        //*****************************************************************************************

        List<SysUser> orgUsers = queryEnterpriseUser.getOrgUser(advertiser.getOrgId());
        for (SysUser orgUser:orgUsers){
            sysUserService.updateUser(orgUser,ids);
        }*//*
    }
*/
    @Override
    @Transactional
    public void setAdvertiserRole(Long id) {
        Advertiser advertiser = advertiserMapper.selectById(id);

        List<SysUser> users = queryEnterpriseUser.getOrgUser(advertiser.getOrgId());

        //获得审核后的角色
        SysRole sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>()
                .eq("org_id", OrgCodeType.SYSTEM_ORG_ID)
                .eq("role_code", UserType.ADVERTISERS_TENANT_APPROVED.toString()));
        List<Long> ids = List.of(sysRole.getId());

        //分配角色
        for(SysUser sysUser:users){
            sysUserService.setRoles(sysUser.getId(),ids);
        }

    }

    //设置广告主联系人信息 如果有id则根据id 不然不改变
    private AdvertiserDTO setContactInfo(AdvertiserDTO advertiserRecord) {
        if (advertiserRecord.getContactId() != null) {
            SysUser contactUser = sysUserService.getById(advertiserRecord.getContactId());
            advertiserRecord.setContactName(contactUser.getName());
            advertiserRecord.setContactPhone(contactUser.getPhone());
        }
        return advertiserRecord;
    }


    //分配主管策略
/*    public Long getManagerId(){

       Long managerId = queryCinemaOrgDao.getManagerId(CinemaUserRole.ADMIN_CINEMA_MANAGER_MASTER_ROLE);
       if(StringUtils.isEmpty(managerId)){
          *//* sysUserService.createOrgAdmin(SYSTEM_ID,SYSTEM_ID,orgCode,userType);*//*
           throw new BusinessException(BusinessCode.CRUD_QUERY_FAILURE,"主管不存在");
       }
        return managerId;
    }*/


    @Override
    public void verifyPhone(String phone){
       // SysUser userByPhone = queryEnterpriseUser.getUserByPhone(phone);
        SysUser sysUser = sysUserService.checkUserExist(phone);
        if(sysUser!=null){
            throw new BusinessException(BusinessCode.DuplicateKey,"手机号已被使用，请更换一个。");
        }
    }



}
