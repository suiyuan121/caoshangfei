package com.zj.caoshangfei.integration.solr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.caoshangfei.common.bean.PageList;
import com.zj.caoshangfei.utils.RegexpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.util.*;

import static com.zj.caoshangfei.CacheConfiguration.CACHE_NAME_FOR_IDS;

/**
 * Created by jin.zhang@fuwo.com on 2017/11/27.
 */
@Service
public class SolrClient {

    //tag搜索接口地址
    public static final String SOLR_SEARCH_TAG_URL = "http://search.fuwo.com/api/caoshangfei/tags/search?q=%s&page=%s&size=%s";

    //图册搜索接口地址====
    public static final String SOLR_SEARCH_CASE_URL = "http://search.fuwo.com/api/caoshangfei/cases/search?q=%s&page=%s&size=%s";

    //单图搜索接口地址====
    public static final String SOLR_SEARCH_PHOTO_URL = "http://search.fuwo.com/api/caoshangfei/photos/search?q=%s&page=%s&size=%s";

    @Autowired
    private RestOperations restOperations;

    private final Logger logger = LoggerFactory.getLogger(SolrClient.class);


    //根据搜索solr查询
    @Cacheable(cacheNames = CACHE_NAME_FOR_IDS,
            key = "T(com.zj.caoshangfei.CacheConfiguration).CACHE_NAME_PREFIX+'case:solr:search:size:'+#pageable.pageSize+':page:'+#pageable.pageNumber+':q:'+T(com.zj.caoshangfei.utils.MD5Utils).getMD5Hash(#q)+ ':ids'")
    public PageList<Integer> searchCase(Pageable pageable, String q) {
        logger.debug("多图搜索************");
        if (StringUtils.isEmpty(q)) {
            return new PageList<>();
        }
        q = org.apache.commons.lang.StringUtils.substring(RegexpUtils.removeNonUnicodeCharacter(q), 0, 20);
        String url = String.format(SOLR_SEARCH_CASE_URL, q, pageable.getPageNumber(), pageable.getPageSize());
        String response = "";
        try {
            long start = System.currentTimeMillis();
            response = restOperations.getForEntity(url, String.class).getBody();
            long end = System.currentTimeMillis();
            logger.debug("多图请求接口耗时************" + (end - start));

            return resolve(pageable, response);
        } catch (IOException e) {
            logger.error("搜索article解析数据异常 response=" + response + "---error=" + e);
        }
        return new PageList<>();
    }

    //根据搜索solr查询
    @Cacheable(cacheNames = CACHE_NAME_FOR_IDS,
            key = "T(com.zj.caoshangfei.CacheConfiguration).CACHE_NAME_PREFIX+'tag:solr:search:size:'+#pageable.pageSize+':page:'+#pageable.pageNumber+':q:'+T(com.zj.caoshangfei.utils.MD5Utils).getMD5Hash(#q)+ ':ids'")
    public PageList<Object> searchTag(Pageable pageable, String q) {
        logger.debug("tag搜索************");

        if (StringUtils.isEmpty(q)) {
            return new PageList<>();
        }
        q = org.apache.commons.lang.StringUtils.substring(RegexpUtils.removeNonUnicodeCharacter(q), 0, 20);
        String url = String.format(SOLR_SEARCH_TAG_URL, q, pageable.getPageNumber(), pageable.getPageSize());
        String response = "";
        try {
            long start = System.currentTimeMillis();
            response = restOperations.getForEntity(url, String.class).getBody();
            long end = System.currentTimeMillis();
            logger.debug("tag请求接口耗时************" + (end - start));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);
            JsonNode content = jsonNode.get("content");
            JsonNode totalElements = jsonNode.get("totalElements");

            Iterator<JsonNode> iterator = content.elements();

            List<Object> tags = new ArrayList<>();
            while (iterator.hasNext()) {
                JsonNode node = iterator.next();
                String id = node.get("id").asText();
                String name = node.get("name").asText();

                Map<String, Object> object = new HashMap<>();
                object.put("id", id);
                object.put("name", name);
                tags.add(object);
            }
            return new PageList(pageable, Long.valueOf(totalElements.toString()), tags);
        } catch (IOException e) {
            logger.error("搜索tag解析数据异常 response=" + response + "---error=" + e);
        }
        return new PageList<>();
    }


    //根据搜索solr查询
    @Cacheable(cacheNames = CACHE_NAME_FOR_IDS,
            key = "T(com.zj.caoshangfei.CacheConfiguration).CACHE_NAME_PREFIX+'photo:solr:search:size:'+#pageable.pageSize+':page:'+#pageable.pageNumber+':q:'+T(com.zj.caoshangfei.utils.MD5Utils).getMD5Hash(#q)+ ':ids'")
    public PageList<Integer> searchPhoto(Pageable pageable, String q) {
        logger.debug("photo搜索************");

        if (StringUtils.isEmpty(q)) {
            return new PageList<>();
        }
        q = org.apache.commons.lang.StringUtils.substring(RegexpUtils.removeNonUnicodeCharacter(q), 0, 20);
        String url = String.format(SOLR_SEARCH_PHOTO_URL, q, pageable.getPageNumber(), pageable.getPageSize());
        String response = "";
        try {
            long start = System.currentTimeMillis();
            response = restOperations.getForEntity(url, String.class).getBody();
            long end = System.currentTimeMillis();
            logger.debug("单图请求接口耗时************" + (end - start));

            return resolve(pageable, response);
        } catch (IOException e) {
            logger.error("搜索photo解析数据异常 response=" + response + "---error=" + e);
        }
        return new PageList<>();
    }


    private PageList<Integer> resolve(Pageable pageable, String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);
        JsonNode content = jsonNode.get("content");
        Iterator<JsonNode> iterator = content.elements();
        JsonNode totalElements = jsonNode.get("totalElements");

        List<Integer> ids = new ArrayList<>();
        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
            ids.add(node.get("id").asInt());
        }
        return new PageList(pageable, Long.valueOf(totalElements.toString()), ids);
    }


}
