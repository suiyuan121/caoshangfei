package com.zj.caoshangfei;

/**
 * Created by jin.zhang@fuwo.com on 2017/8/18.
 */
public class GlobalConstant {

    //oss 文件key
    public static final String OSS_KEY_PATTEN = "liuliang/caoshangfei/essay_%s.txt";

    public static final String DEAFULT_CHARSET = "UTF-8";

    //oss 目录，根目录 .../admin/{filepath}
    public static final String OSS_STORE_ROOT_PATH = "xiaoguotu";

    //管理后台访问根目录  localhost/admin/....
    public static final String ADMIN_MAPPING = "/admin";

    //前端接根目录
    public static final String FRONTED_MAPPING = "/caoshangfei";

    public static final int REST_MAX_REQUEST_PAGE = 20;

    public static final int MVC_MAX_REQUEST_PAGE = 50;

    //移动端默认分页大小
    public static final int MOBILE_DEFAULT_REQUEST_PAGE = 16;

}
