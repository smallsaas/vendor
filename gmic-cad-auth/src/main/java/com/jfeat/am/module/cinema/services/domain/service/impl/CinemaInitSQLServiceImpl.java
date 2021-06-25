package com.jfeat.am.module.cinema.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.cinema.services.domain.dao.QueryCinemaPermDao;
import com.jfeat.am.module.cinema.services.domain.service.CinemaInitSQLService;
import com.jfeat.am.uaas.common.BeanKit;
import com.jfeat.am.uaas.perm.services.domain.service.SysRoleService;
import com.jfeat.am.uaas.perm.services.persistence.dao.SysPermGroupMapper;
import com.jfeat.am.uaas.perm.services.persistence.dao.SysPermMapper;
import com.jfeat.am.uaas.perm.services.persistence.dao.SysRoleMapper;
import com.jfeat.am.uaas.perm.services.persistence.model.SysPerm;
import com.jfeat.am.uaas.perm.services.persistence.model.SysPermGroup;
import com.jfeat.am.uaas.perm.services.persistence.model.SysRole;
import com.jfeat.am.uaas.system.services.transfer.RoleWrapper;
import com.jfeat.crud.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来初始化 非平台组织的sql数据用
 * */
@Service
public class CinemaInitSQLServiceImpl implements CinemaInitSQLService {

    protected final static Logger log = LoggerFactory.getLogger(CinemaInitSQLServiceImpl.class);


    @Resource
    QueryCinemaPermDao queryCinemaPermDao;
    @Resource
    SysPermGroupMapper sysPermGroupMapper;
    @Resource
    SysPermMapper sysPermMapper;
    @Resource
    SysRoleService sysRoleService;
    @Resource
    SysRoleMapper sysRoleMapper;

    //角色生成方法
    @Override
    public Integer initRole(List<String> permList, Long orgId, RoleWrapper roleWrapper){
        Integer i = 0;
        //权限
        i += initPerm(permList,orgId);
        //角色
        List<Long> permIdsByPermIdentifier = getPermIdsByPermIdentifier(permList, orgId);
        roleWrapper.setPermIds(permIdsByPermIdentifier);
        SysRole sysRole = new SysRole();
        BeanKit.copyProperties(roleWrapper, sysRole);
        sysRole.setSortOrder(1);
        sysRole.setOrgId(orgId);
        sysRoleService.saveRole(sysRole, roleWrapper.getPermIds());
        return i;
    }

    //配置没权限角色
    @Override
    public void initNoPermRole(Long orgId, RoleWrapper roleWrapper){
        SysRole sysRole = new SysRole();
        BeanKit.copyProperties(roleWrapper, sysRole);
        sysRole.setSortOrder(1);
        sysRole.setOrgId(orgId);
        sysRoleService.saveRole(sysRole, null);
    }

    //设置对应角色的权限
    @Override
    public void setRolePerm(List<String> permList,Long orgId){

        //权限
        initPerm(permList,orgId);
        //角色
        List<Long> permIdsByPermIdentifier = getPermIdsByPermIdentifier(permList, orgId);
        List<SysRole> roleList = sysRoleMapper.selectList(new QueryWrapper<SysRole>().eq("org_id", orgId));
        for(SysRole sysRole:roleList){
            sysRoleService.updateRole(sysRole,permIdsByPermIdentifier);
        }

    }



    //获取权限列表 根据identifier列表
    @Override
    public List<SysPerm>  getPermByPermIdentifier(List<String> permList){
        List<SysPerm> permByList = queryCinemaPermDao.getPermByList(permList);
        return permByList;
    }

    //获取权限列表 根据identifier列表
    @Override
    public List<Long>  getPermIdsByPermIdentifier(List<String> permList, Long orgId){
        List<Long> ids = queryCinemaPermDao.getPermIdsByList(permList,orgId);

        return ids;
    }

    //获取权限组 根据identifier
    @Override
    public List<SysPermGroup> getPermGroup(List<String> permList){
        List<SysPermGroup> permGroupByPermIdList = queryCinemaPermDao.getPermGroupByPermIdList(permList);
        List<Long> permGroupIdList = getPermGroupIdList(permGroupByPermIdList);
        while(permGroupIdList != null){
            List<SysPermGroup> permGroupByList = queryCinemaPermDao.getPermGroupByList(permGroupIdList);
            permGroupIdList = getPermGroupIdList(permGroupByList);
            permGroupByPermIdList.addAll(permGroupByList);
        }
        return permGroupByPermIdList;
    }

    //初始化权限
    //权限生成主要入口
    public Integer initPerm(List<String> permLists,Long orgId){
        Integer i= 0;

        List<SysPerm> permList = getPermByPermIdentifier(permLists);
        List<SysPermGroup> permGroupList = getPermGroup(permLists);
        //获取identifier 与 权限组的映射
        Map<String, SysPermGroup> sysPermGroupMap = ListToMapIdentifier(permGroupList);
        //获取旧的id 权限映射 用于检查
        final Map<Long, SysPermGroup> oldSysPermGroupMap = ListToMapId(permGroupList);
        Map<String, SysPerm> sysPermMap = ListToMapIdentifier(permList);

        for (SysPermGroup permGroup:permGroupList){
            //插入权限组数据
            insertPermGroupCore(permGroup, orgId, oldSysPermGroupMap, sysPermGroupMap);
        }

        //插入权限数据
        for(SysPerm sysPerm:permList){
            SysPermGroup sysPermGroup = oldSysPermGroupMap.get(sysPerm.getGroupId());
            SysPermGroup newSysPermGroup = sysPermGroupMap.get(sysPermGroup.getIdentifier());
            sysPerm.setId(null);
            sysPerm.setGroupId(newSysPermGroup.getId());
            i  += sysPermMapper.insert(sysPerm);
        }
        return i;
    }




    //获取权限组的id
    public List<Long> getPermGroupIdList(List<SysPermGroup> sysPermGroups){
        List<Long> permGroupIds = new ArrayList<>();
        for (SysPermGroup sysPermGroup:sysPermGroups){
            Long pid = sysPermGroup.getPid();
            if(pid!=null){
                permGroupIds.add(pid);
            }
        }

        if(permGroupIds.size()>0){
            return permGroupIds;
        }else {
            return null;
        }

    }


    //转换成map 方便读取
    public  <T> Map<String,T> ListToMapIdentifier(List<T> tList){
        Field idField = null;
        Map<String,T> map = new HashMap<>();


        for (T t:tList){
            try {
                idField = t.getClass().getDeclaredField("identifier");
                idField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new BusinessException(4032,"identifier字段不存在 NoSuchFieldException");
            }

            try {
                String id = idField.get(t).toString();
                map.put(id,t);
            } catch (IllegalAccessException e) {
                throw new BusinessException(4032,"identifier字段不存在 IllegalAccessException"+ e);
            }

        }
        return map;
    }

    public  <T> Map<Long,T> ListToMapId(List<T> tList){
        Field idField = null;
        Map<Long,T> map = new HashMap<>();
        for (T t:tList){
            try {
                idField = t.getClass().getDeclaredField("id");
                idField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new BusinessException(4032,"id字段不存在 NoSuchFieldException");
            }

            try {
                Long id = (Long)idField.get(t);
                map.put(id,t);
            } catch (IllegalAccessException e) {
                throw new BusinessException(4032,"id字段不存在 IllegalAccessException");
            }

        }
        return map;
    }

   SysPermGroup insertPermGroup(SysPermGroup permGroup, Long orgId){
         permGroup.setId(null);
         permGroup.setOrgId(orgId);
         sysPermGroupMapper.insert(permGroup);
         SysPermGroup sysPermGroup = sysPermGroupMapper.selectOne(new QueryWrapper<SysPermGroup>()
               .eq("identifier", permGroup.getIdentifier())
               .eq("org_id", permGroup.getOrgId()));

        return sysPermGroup;
    }




  public void  insertPermGroupCore(SysPermGroup permGroup,Long orgId
          ,Map<Long, SysPermGroup> oldSysPermGroupMap,Map<String, SysPermGroup> sysPermGroupMap){

        if(permGroup.getPid()==null){
            SysPermGroup oldSysPermGroup = oldSysPermGroupMap.get(permGroup.getId());
            SysPermGroup newSysPerm = sysPermGroupMap.get(permGroup.getIdentifier());
            //新旧id相同 未更新 尝试更新
            if(oldSysPermGroup!=null && oldSysPermGroup.getId().equals(newSysPerm.getId())){
                //新增第一层权限组
                SysPermGroup sysPermGroup = insertPermGroup(permGroup, orgId);
                //更新
                sysPermGroupMap.put(sysPermGroup.getIdentifier(),sysPermGroup);
            }
        }
        else {
            //检查自身新旧id
            SysPermGroup oldSysPermGroup = oldSysPermGroupMap.get(permGroup.getId());
            SysPermGroup newSysPerm = sysPermGroupMap.get(permGroup.getIdentifier());

            //新旧id相同 未更新 尝试更新
            if (oldSysPermGroup!=null && oldSysPermGroup.getId().equals(newSysPerm.getId())) {

                permGroup.setId(null);
                permGroup.setOrgId(orgId);
                SysPermGroup sysPermGroup = oldSysPermGroupMap.get(permGroup.getPid());
                SysPermGroup newSysPermGroup = sysPermGroupMap.get(sysPermGroup.getIdentifier());
                //新旧id相同 未更新 尝试更新父类
                if (sysPermGroup!=null && newSysPermGroup.getId().equals(sysPermGroup.getId())) {
                    //更新父类 调用本身
                    insertPermGroupCore(sysPermGroup, orgId, oldSysPermGroupMap, sysPermGroupMap);
                }
                //更新自身 插入数据
                permGroup.setPid(sysPermGroupMap.get(sysPermGroup.getIdentifier()).getId());
                SysPermGroup afterInsertPermGroup = insertPermGroup(permGroup, orgId);
                sysPermGroupMap.put(afterInsertPermGroup.getIdentifier(),afterInsertPermGroup);
            }
        }

    }


}
