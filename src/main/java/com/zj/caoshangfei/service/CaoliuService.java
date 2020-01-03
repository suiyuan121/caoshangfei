package com.zj.caoshangfei.service;

import com.zj.caoshangfei.config.CaoshangfeiProperties;
import com.zj.caoshangfei.model.VideoEntity;
import com.zj.caoshangfei.service.repository.VideoEntityRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CaoliuService {

    @Autowired
    private CaoshangfeiProperties caoshangfeiProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private VideoEntityRepository videoEntityRepository;

    public void getList(Integer page) {

        String LIST_API_URL = "thread0806.php?fid=22&search=&";
        String listUrl = String.format("%s%spage=%s", caoshangfeiProperties.getHost(), LIST_API_URL, page);

        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("text/html; charset=GBK");
        requestHeaders.setContentType(type);

        requestHeaders.add("cookie","__cfduid=d6b5d9f4aeda86d42e3a3b839f16da4641578032795; UM_distinctid=16f6a1595d931-0ad4872cea0917-1d376b5c-fa000-16f6a1595db305; CNZZDATA950900=cnzz_eid%3D751062132-1578030469-%26ntime%3D1578030469");
        requestHeaders.add("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");

        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> listResponse = restTemplate.exchange(listUrl, HttpMethod.GET, requestEntity, String.class);

        Document listDoc = Jsoup.parse(listResponse.getBody());
        Elements hrefs = listDoc.getElementsByAttributeValueContaining("href", "htm_data");
        Set<String> urls = new HashSet<>();

        if (!CollectionUtils.isEmpty(hrefs)) {
            hrefs.forEach(item -> {

                urls.add(item.attr("href"));

            });
        }

        if (!CollectionUtils.isEmpty(urls)) {

            urls.forEach(item -> {

                String detailUrl = String.format("%s%s", caoshangfeiProperties.getHost(), item);

                ResponseEntity<String> detailResp = null;
                try {
                    detailResp = restTemplate.exchange(detailUrl, HttpMethod.GET, requestEntity, String.class);
                    Document detailDoc = Jsoup.parse(detailResp.getBody());

                    String url = "";
                    Elements video = detailDoc.getElementsByAttributeValueContaining("onclick", "getElementById");
                    if (!CollectionUtils.isEmpty(video)) {
                        String onclick = video.get(0).attr("onclick");

                        if (StringUtils.hasText(onclick)) {
                            url = onclick.substring(onclick.indexOf("=") + 2).split("#")[0];
                        }
                    }
                    if (StringUtils.hasText(url)) {
                        VideoEntity videoEntity = videoEntityRepository.findFirstByUrl(url);
                        if (videoEntity == null) {
                            videoEntity = new VideoEntity();
                            String title = detailDoc.getElementsByTag("title").get(0).html().split("-")[0];

                            videoEntity.setUrl(url);
                            videoEntity.setTitle(title);
                            videoEntity.setCreatedAt(new Date());
                            videoEntity.setType((byte) 1);

                            videoEntityRepository.save(videoEntity);
                        }
                    }


                } catch (RestClientException e) {

                }

            });
        }
    }

}


