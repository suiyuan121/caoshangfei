package com.zj.caoshangfei.enums;

import com.zj.caoshangfei.common.exceptions.NotFoundException;
import org.apache.commons.lang.StringUtils;

public enum AreaTypeEnum {

    UNKNOWN("0", "未知"),
    ONE("1", "60㎡以下"),
    TWO("2", "60-80㎡"),
    THREE("3", "80-100㎡"),
    FOUR("4", "100-120㎡"),
    FIVE("5", "120-150㎡"),
    SIX("6", "150㎡以上"),;
    //;
    private String desc;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    private AreaTypeEnum(String code, String desc) {
        this.desc = desc;
        this.code = code;
    }


    public static AreaTypeEnum getByName(String name) {

        for (AreaTypeEnum item : AreaTypeEnum.values()) {
            if (StringUtils.equals(item.toString(), name)) {
                return item;
            }
        }
        return null;
    }

    public static AreaTypeEnum getByCode(String code) {

        for (AreaTypeEnum item : AreaTypeEnum.values()) {
            if (StringUtils.equals(code, AreaTypeEnum.UNKNOWN.code)) {
                return null;
            }
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }
        //未找到的直接404，就是不存在的值
        throw new NotFoundException();
    }

    public static AreaTypeEnum valueOfCode(String code) {

        for (AreaTypeEnum item : AreaTypeEnum.values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }
        return AreaTypeEnum.UNKNOWN;
    }

}
