package com.zj.caoshangfei;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jin.zhang@fuwo.com on 2017/10/14.
 */
//@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

    public static final String CACHE_NAME_PREFIX = "com.fuwo.caoshangfei:";
    //
    public static final String CACHE_NAME_FOR_MODELS = CACHE_NAME_PREFIX + "zset:models";

    public static final String CACHE_NAME_FOR_IDS = CACHE_NAME_PREFIX + "zset:ids";

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        Map<String, Long> expires = new HashMap<String, Long>();
        expires.put(CACHE_NAME_FOR_MODELS, ExpireEnum.TWO_DAYS.time);
        expires.put(CACHE_NAME_FOR_IDS, ExpireEnum.TWO_DAYS.time);

        cacheManager.setExpires(expires);
        cacheManager.setDefaultExpiration(ExpireEnum.TWO_DAYS.time);
        return cacheManager;
    }


    public enum ExpireEnum {
        ONE_YEAR(365 * 24 * 60 * 60L, "一年"),

        THIRTY_DAYS(30 * 24 * 60 * 60L, "30天"),

        ONE_DAY(24 * 60 * 60L, "1天"),

        ONE_WEEK(7 * 24 * 60 * 60L, "一周"),

        TWO_DAYS(2 * 24 * 60 * 60L, "2天"),

        ONE_HOUR(60 * 60L, "1小时"),

        SIX_HOURS(6 * 60 * 60L, "6小时"),


        //
        ;
        private String desc;

        //以毫秒为单位
        private Long time;

        public String getDesc() {
            return desc;
        }

        public long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private ExpireEnum(Long time, String desc) {
            this.desc = desc;
            this.time = time;

        }
    }

}
