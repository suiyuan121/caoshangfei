package com.zj.caoshangfei.service;

import com.zj.caoshangfei.common.bean.PageList;
import com.zj.caoshangfei.model.VideoEntity;

/**
 * @author zhangjin
 * @date 2019-10-31
 */
public interface VideoService {

    PageList<VideoEntity> findAll(String keyword, int page, int size);

}
