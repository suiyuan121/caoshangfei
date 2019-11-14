package com.zj.caoshangfei.integration;

import com.zj.caoshangfei.common.RestResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Map;

/**
 * Created by jin.zhang@fuwo.com on 2017/11/27.
 */
@Service
public class WwwCient {

    //爱福窝装修
    public static final String GET_ACCESS_TOKEN = "http://www.fuwo.com/weixin/access_token/";

    @Autowired
    private RestOperations restOperations;

    private final Logger logger = LoggerFactory.getLogger(WwwCient.class);


    public String getAccessToken() {
        RestResult response = restOperations.getForEntity(GET_ACCESS_TOKEN, RestResult.class).getBody();
        if (StringUtils.equals(response.getCode(), RestResult.ResultCodeEnum.SUCC.getCode())) {
            Map<String, String> data = (Map<String, String>) response.getData();
            String access_token = data.get("access_token");
            logger.debug("获取【爱福窝装修】微信access_token={}", access_token);
            return access_token;
        }
        throw new RuntimeException("获取【爱福窝装修】微信access_token异常 result=" + response);
    }


}
