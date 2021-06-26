package com.jfeat.am.module.cinema.menu;

public enum CinemaImportStatus {

    ERROR_CITY ("ERROR_CITY","城市数据错误，请填写正确的县级市或地级市"),
    IS_NULL ("NULL","未发现异常");
    private  String name;
    private  String info;
    CinemaImportStatus(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
