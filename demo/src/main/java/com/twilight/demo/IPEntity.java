package com.twilight.demo;

/**
 * Created by twilight on 12/4/15.
 */
public class IPEntity {



    private int ret;
    private int start;
    private int end;
    private String country;
    private String province;
    private String city;
    private String district;
    private String isp;
    private String type;
    private String desc;

    public void setRet(int ret) {
        this.ret = ret;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRet() {
        return ret;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getIsp() {
        return isp;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "country="+country+",province="+province+",city="+city;
    }
}
