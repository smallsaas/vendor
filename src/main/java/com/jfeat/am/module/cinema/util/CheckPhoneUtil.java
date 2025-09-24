package com.jfeat.am.module.cinema.util;

import java.util.regex.Pattern;

public class CheckPhoneUtil {

    public static boolean checkPhone(String phone){
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(phone).matches();
    }

    public static void main(String[] args) {
        String phone = "";
        boolean b = checkPhone(phone);
        System.out.println(phone+" : "+b);
    }
}
