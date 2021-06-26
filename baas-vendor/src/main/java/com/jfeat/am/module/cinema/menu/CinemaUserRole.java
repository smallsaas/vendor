package com.jfeat.am.module.cinema.menu;

import java.util.ArrayList;
import java.util.List;

public class CinemaUserRole {
    //广告主和影院的角色应自动生成 自动分配
/*    public static List<Long> ADVERTISER_ROLE_IDS = List.of(7L);
    public static List<Long> CINEMA_ROLE_IDS = List.of(6L);*/
    public static List<Long> ADMIN_SHOPKEEPER_ROLE_IDS = List.of(5L);
    public static List<Long> ADMIN_CINEMA_ROLE_IDS = List.of(4L);
    public static List<Long> ADMIN_SHOPKEEPER_MANAGER_ROLE_IDS = List.of(3L);
    public static List<Long> ADMIN_CINEMA_MANAGER_ROLE_IDS = List.of(2L);
    public static List<Long> ADMIN_CINEMA_MANAGER_MASTER_ROLE_IDS = List.of(1L);

    public static Long ADMIN_SHOPKEEPER_ROLE = 5L;
    public static Long ADMIN_CINEMA_ROLE = 4L;
    public static Long ADMIN_SHOPKEEPER_MANAGER_ROLE = 3L;
    public static Long ADMIN_CINEMA_MANAGER_ROLE = 2L;
    public static Long ADMIN_CINEMA_MANAGER_MASTER_ROLE = 1L;
    /**
     * 1		总主管
     * 2		商务主管
     * 3		店小二主管
     * 4		商务
     * 5		店小二

     * */

}
