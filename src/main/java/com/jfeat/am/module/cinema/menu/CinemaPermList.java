package com.jfeat.am.module.cinema.menu;

import java.util.List;

//权限配置类 根据权限列表分配租户权限
public class CinemaPermList {
    public static List<String> ADVERTISER_PERM_GROUP = List.of(
            "sysUser.view","sysUser.edit","sysUser.del","sysRole.view","sysRole.edit","sysRole.del"
            ,"Org.view","Org.edit","Org.del"
            ,"OperationLog.view"
            ,"advertingplan.new","advertingplan.delete",  "advertingplan.view", "advertingplan.edit", "advertingplan.order"
            ,"order.view","order.confirm"
            ,"ordertask.view"
            ,"advertingmaterial.new","advertingmaterial.delete","advertingmaterial.view","advertingmaterial.edit"
            ,"filmschedule.view"

    );

    public static List<String> CINEMA_PERM_GROUP = List.of(
            "sysUser.view","sysUser.edit","sysUser.del","sysRole.view","sysRole.edit","sysRole.del"
            ,"Org.view","Org.edit","Org.del"
            ,"OperationLog.view"
            ,"ordertask.view","ordertask.edit","ordertask.provide"
            ,"filmschedule.view"
    );

    public static List<String> KEEPER_MANAGER_PERM_GROUP = List.of(
            "sysRole.del","sysUser.edit"
    );

    public static List<String> CINEMA_MANAGER_PERM_GROUP = List.of(
            "sysRole.del","sysUser.edit"
    );






}
