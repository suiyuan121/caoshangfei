package com.zj.caoshangfei.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangjin
 * @date 2019-12-25
 */
@Service
public class DsService {


    @Autowired
    private RestTemplate restTemplate;

    public final String matchDetailPatten = "https://www.dszuqiu.com/race/%s";

    public Integer getScore(Long matchId) {


        String matchDetail = String.format(matchDetailPatten, matchId);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("cookie", "ds_session=aekjab8a3s0c4mpvgp6n7c0h83; _ga=GA1.2.438046285.1576914178; Hm_lvt_a68414d98536efc52eeb879f984d8923=1577265715,1577265720,1577266138,1577266154; Hm_lpvt_a68414d98536efc52eeb879f984d8923=1577267978");
        requestHeaders.add("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");

        MediaType type = MediaType.parseMediaType("text/html; charset=GBK");
        requestHeaders.setContentType(type);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);

        String resp = restTemplate.exchange(matchDetail, HttpMethod.GET, requestEntity, String.class).getBody();

        Document document = Jsoup.parse(resp);


        Element element = document.getElementById("race_part");

        Elements right = element.getElementsByClass("text-right");
        Elements rightTag = right.get(0).getElementsByTag("h3");

        Integer rightScore = Integer.valueOf(rightTag.html());

        Elements left = element.getElementsByClass("text-left");
        Elements leftTag = left.get(0).getElementsByTag("h3");

        Integer leftScore = Integer.valueOf(leftTag.html());

        return rightScore + leftScore;
    }
}
