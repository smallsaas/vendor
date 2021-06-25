package com.jfeat.am.module.cinema.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.cinema.menu.CinemaImportStatus;
import com.jfeat.am.module.cinema.menu.CinemaPermList;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaDao;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaOrgDao;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaUser;
import com.jfeat.am.module.cinema.services.domain.model.CinemaRecord;
import com.jfeat.am.module.cinema.services.domain.model.CinemaUserType;
import com.jfeat.am.module.cinema.services.domain.model.OrgCodeType;
import com.jfeat.am.module.cinema.services.domain.service.CinemaInitSQLService;
import com.jfeat.am.module.cinema.services.domain.service.CinemaOrgService;
import com.jfeat.am.module.cinema.services.domain.service.CinemaService;
import com.jfeat.am.module.cinema.services.domain.service.CinemaUserService;
import com.jfeat.am.module.cinema.services.gen.crud.service.impl.CRUDCinemaServiceImpl;
import com.jfeat.am.module.cinema.services.gen.persistence.dao.CinemaLineMapper;
import com.jfeat.am.module.cinema.services.gen.persistence.dao.CinemaMapper;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Advertiser;
import com.jfeat.am.module.cinema.services.gen.persistence.model.Cinema;
import com.jfeat.am.module.cinema.services.gen.persistence.model.CinemaLine;
import com.jfeat.am.module.cinema.util.CinemaUserUtil;
import com.jfeat.am.uaas.perm.services.domain.model.SysRoleMadeByType;
import com.jfeat.am.uaas.perm.services.persistence.dao.SysRoleMapper;
import com.jfeat.am.uaas.perm.services.persistence.model.SysRole;
import com.jfeat.am.uaas.system.services.domain.service.SysUserService;
import com.jfeat.am.uaas.system.services.transfer.RoleWrapper;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUD;
import com.jfeat.org.services.domain.model.SysOrgBType;
import com.jfeat.org.services.domain.service.SysOrgService;
import com.jfeat.org.services.persistence.dao.SysUserMapper;
import com.jfeat.org.services.persistence.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.jfeat.am.module.cinema.menu.GenAccountString.CINEMA_USER_ACCOUNT;
import static com.jfeat.am.module.cinema.util.CheckPhoneUtil.checkPhone;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */
@Service("cinemaService")
public class CinemaServiceImpl extends CRUDCinemaServiceImpl implements CinemaService {

    protected final static Logger log = LoggerFactory.getLogger(CinemaServiceImpl.class);

    @Resource
    SysUserService sysUserService;
    @Resource
    CinemaMapper cinemaMapper;
    @Resource
    CinemaOrgService cinemaOrgService;
    @Resource
    QueryCinemaOrgDao queryCinemaOrgDao;

    @Resource
    CinemaUserUtil cinemaUserUtil;
    @Resource
    CinemaInitSQLService cinemaInitSQLService;
    @Resource
    SysRoleMapper sysRoleMapper;
    @Resource
    CinemaLineMapper cinemaLineMapper;
    @Resource
    SysOrgService sysOrgService;
    @Resource
    QueryCinemaUser queryCinemaUser;
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    QueryCinemaDao queryCinemaDao;
    @Resource
    CinemaUserService cinemaUserService;

    @Override
    @Transactional
    public Integer createCinema(CinemaRecord cinemaRecord) {

        //电话去空格处理
        cinemaRecord.setContact(cinemaRecord.getContact().trim());

        //检查影院电话的用户是否存在 不存在才创建基本账户
        SysUser sysUser = cinemaUserService.getUserByPhone(cinemaRecord.getContact());
        if (sysUser != null) {
            throw new BusinessException(BusinessCode.DuplicateKey, "手机号已被注册");
        }

        Integer i = 0;
        /**设置影线名**/
        if (cinemaRecord.getLineId() != null) {
            CinemaLine cinemaLine = null;
            cinemaLine = cinemaLineMapper.selectById(cinemaRecord.getLineId());
            cinemaRecord.setLineName(cinemaLine.getName());
        }


        //检查手机号正确性
        boolean checkPhone = checkPhone(cinemaRecord.getContact());

        //检查注册状态
        cinemaUserService.checkUser(cinemaRecord.getContact());

        if(checkPhone){
            /**根据影院获取组织逻辑 */
            //*****************************************************************************************
            JSONObject org = createCinemaOrgAndAdmin(cinemaRecord);
            i += org.getInteger("i");
            //*****************************************************************************************
            cinemaRecord.setOrgId(Long.parseLong(org.get("id").toString()));
        }

        cinemaRecord.setAssistantId(JWTKit.getUserId());
        //新增影院
        i += cinemaMapper.insert(cinemaRecord);

        return i;
    }

    private JSONObject createCinemaOrgAndAdmin(CinemaRecord cinemaRecord) {
        Integer i = 0;
        //随机生成orgCode
        cinemaRecord.setOrgCode(CinemaUserUtil.genOrgCode());
        //调用方法创建组织
        JSONObject org = cinemaOrgService.createOrg(OrgCodeType.CINEMA, cinemaRecord, SysOrgBType.USER);
        Long pOrgId = Long.parseLong(org.get("pOrgId").toString());
        Long newOrgId = Long.parseLong(org.get("id").toString());


        //检查影院电话的用户是否存在 不存在才创建基本账户
        SysUser sysUser = cinemaUserService.getUserByPhone(cinemaRecord.getContact());
        if (sysUser == null) {
            //获取影院管理人角色
            SysRole sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>()
                    .eq("org_id", OrgCodeType.SYSTEM_ORG_ID)
                    .eq("role_code", CinemaUserType.CINEMA_MANAGER.toString()));
            List<Long> ids = List.of(sysRole.getId());

            //创建基本账户 自身id作为租户id 同时分配角色
            cinemaOrgService.createOrgAdmin(newOrgId, newOrgId, sysUserService.genAccountByString(CINEMA_USER_ACCOUNT),
                    cinemaRecord.getContact(),
                    SysOrgBType.USER, OrgCodeType.CINEMA_ADMIN_NAME, ids, cinemaRecord.getPassword());

        } else {
            //更新对应账户的orgId
           // queryCinemaUser.updateUserOrgId(sysUser.getId(), newOrgId);
        }
        //创建空角色
        i += queryCinemaUser.insertRole("运营人员", newOrgId, "");
        i += queryCinemaUser.insertRole("财务人员", newOrgId, "");

        i += Integer.parseInt(org.get("count").toString());
        org.put("i", i);
        return org;
    }


    @Override
    @Transactional
    public Integer setCinemaUser(Long cinemaId) {
        Integer i = 0;
        Cinema cinema = cinemaMapper.selectById(cinemaId);
        //去空格
        cinema.setContact(cinema.getContact().trim());
        //检查电话号码
        //boolean truePhone = checkPhone(cinema.getContact());

        if(StringUtils.isEmpty(cinema.getContact()) ){
            //联系人为空  不做处理
        }else{
        //检查影院电话的用户是否存在 不存在才创建基本账户 不为空跳过
        SysUser sysUser = cinemaUserService.getUserByPhone(cinema.getContact());
        if (sysUser != null) {

        }else{
            /**影院新增逻辑 */
            CinemaRecord cinemaRecord = CRUD.castObject(cinema, CinemaRecord.class);

            /**根据影院获取组织逻辑 */
            //*****************************************************************************************
            JSONObject org = createCinemaOrgAndAdmin(cinemaRecord);
            i += org.getInteger("i");
            //*****************************************************************************************
            long orgId = Long.parseLong(org.get("id").toString());
            cinema.setOrgId(orgId);
            //更新影院
            i += cinemaMapper.updateById(cinema);
        }
        }

        return i;
    }


    @Override
    public Integer updateCinema(Cinema entity) {
        Integer i = 0;
        /**设置影线名**/
        CinemaLine cinemaLine = cinemaLineMapper.selectById(entity.getLineId());
        entity.setLineName(cinemaLine.getName());

        i += cinemaMapper.updateById(entity);
        return i;
    }

    @Override
    public Integer setAssistant(Long id, Long assistantId) {
        Cinema cinema = new Cinema();
        cinema.setAssistantId(assistantId);
        cinema.setId(id);

        int i = cinemaMapper.updateById(cinema);
        return i;
    }


    @Override
    @Transactional
    public Integer deleteCinema(Long id) {
        Cinema cinema = this.retrieveMaster(id);
        Long orgId = cinema.getOrgId();
        Integer affeat = 0;
        Integer relationCount = queryCinemaDao.cinemaOrderRelation(id);
        //有订单关联则删除失败
        if (relationCount > 0) {
            log.info("关联数量 count:{}", relationCount);
            throw new BusinessException(BusinessCode.CRUD_DELETE_FAILURE, "删除失败,该影院存在关联的子订单或广告计划，无法删除");
        }

        //删除组织 角色 用户 权限
        affeat += cinemaUserService.deleteOrg(orgId);

        //删影院
        affeat += cinemaMapper.deleteById(id);
        return affeat;
    }


    @Override
    public Integer setImportCinemaUser() {

        Integer i = 0;
        //获得所有orgId为空 执行人电话不为空 执行人不为空 的影院列表
        List<Cinema> cinemas = cinemaMapper.getImportCinema();
        List<Long> cinemaIds = cinemas.stream().map(Cinema::getId).collect(Collectors.toList());

        if (cinemaIds != null && cinemaIds.size() > 0) {
            //设置院线 院线根据lineName分配lineId
            cinemaMapper.setCinemaLineIdByLineName(cinemaIds);

            for (Long id : cinemaIds) {
                i += setCinemaUser(id);
            }
        }
        return i;
    }


    @Override
    public Integer setImportCinemaUser(Long id) {

        //检查注册状态
        Cinema cinema = cinemaMapper.selectById(id);

        if(StringUtils.isEmpty(cinema.getContactName())){
            throw new BusinessException(BusinessCode.CRUD_UPDATE_FAILURE,"激活失败:执行人不能为空");
        }
        cinemaUserService.checkUser(cinema.getContact());

        Integer i = 0;
        //设置院线 院线根据lineName分配lineId
        List<Long> cinemaIds = new ArrayList<>();
        cinemaIds.add(id);
        cinemaMapper.setCinemaLineIdByLineName(cinemaIds);
        i += setCinemaUser(id);
        return i;
    }

    @Override
    @Transactional
    public Integer changePhone(Long id,String phone){
        logger.info("更改手机号 phone:{} id:{}",phone,id);
        Integer i = queryCinemaUser.updateCinemaPhone(id, phone);
        return i;
    }

    @Override
    public Integer repairPCDData(){
        Integer i = 0;
        //更新所有导入影院
        i += queryCinemaUser.repairPCDData();
        //更新更新状态 最更新失败的数据上加上 &ERROR_CITY
        i += queryCinemaUser.updateErrorImportPCDDataCinema();

        return  i;
    }

    @Override
    public CinemaRecord findCinemaDetail(Long id){
        CinemaRecord cinemaDetail = queryCinemaDao.findCinemaDetail(id);

        //处理影院的导入信息
        StringBuilder stringBuilder =new StringBuilder();
        String importNote = cinemaDetail.getImportNote();
        if(!StringUtils.isEmpty(importNote)){
            String[] codeList = importNote.split(",");
            List<String> codeArray = Arrays.asList(codeList);
            if(codeArray.contains(CinemaImportStatus.ERROR_CITY.getName())){
                stringBuilder.append(CinemaImportStatus.ERROR_CITY.getInfo());
            }
        }


        //最后设置影院的导入信息
        String endImportInfoString = stringBuilder.toString();
        if(StringUtils.isEmpty(endImportInfoString)){
            cinemaDetail.setImportNote(CinemaImportStatus.IS_NULL.getInfo());
        }else{
            cinemaDetail.setImportNote(endImportInfoString);
        }

        return cinemaDetail;
    }



}
