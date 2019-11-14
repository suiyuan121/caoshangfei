package com.zj.caoshangfei.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.caoshangfei.common.RestResult;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jin.zhang@fuwo.com on 2018/4/13.
 */
@Service
public class YsjClient {

    public static final String URl = "https://yz.chsi.com.cn/sytj/stu/sytjqexxcx.action";


    @Autowired
    private RestOperations restOperations;

    private final Logger logger = LoggerFactory.getLogger(YsjClient.class);

    @Autowired
    private JavaMailSender mailSender;

    private String cookie = "userName=17693355227; userId=46675ea556c1af9a11def0b684a38036; JSESSIONID=8CEAA72C46CE6DFC384FE2EA9BC18F76; __utmz=65168252.1513235301.2.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utma=65168252.1004702446.1509274948.1513235301.1521691505.3; _ga=GA1.3.1004702446.1509274948; acw_tc=AQAAAJgh0wE8ygAAJpjOjJZdo5OfrNXf; __utmc=229973332; __utmz=229973332.1524015605.26.16.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; JSESSIONID=9E7DE3C0CFDDA2AD8FB00BA63A37FE40; __utma=229973332.1906756184.1508723999.1524021323.1524456745.28";

    @Value("${spring.mail.username}")
    private String from;

    public void sendAdminMail(String subject, String content, String from, String... recipients) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(recipients);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    public RestResult get() {
        System.out.println("*************************************" + new Date());
        String response = "";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie", cookie);
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36");
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            //params.set("dwmc", "(10315)南京中医药大学");
            //params.set("dwmc", "(10063)天津中医药大学");
            params.set("zymc", "中西医结合临床");

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity(params, headers);

            ObjectMapper mapper = new ObjectMapper();
            response = restOperations.postForEntity(URl, entity, String.class).getBody();

            org.json.JSONObject jsonresponse = new org.json.JSONObject(response);
            String data = String.valueOf(jsonresponse.get("data"));
            String vo_list = new org.json.JSONObject(data).get("vo_list").toString();
            String vos = new org.json.JSONObject(vo_list).get("vos").toString();

            JSONArray jsonArray = new org.json.JSONArray(vos);

            Set<String> schhos = new HashSet<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                String items = jsonArray.get(i).toString();
                org.json.JSONObject jsonObject = new org.json.JSONObject(items);
                String zydm = jsonObject.get("zydm").toString();
                String dwmc = jsonObject.get("dwmc").toString();
               /* if ("105709".equals(zydm)) {
                }*/
                schhos.add(zydm + "-" + dwmc);
            }
            schhos.remove("100602-新疆医科大学");
            schhos.remove("100602-黑龙江省中医药科学院");
            schhos.remove("100602-滨州医学院");
            schhos.remove("100602-大连医科大学");
            schhos.remove("100602-山西医科大学");
            schhos.remove("100602-天津医科大学");
            schhos.remove("100602-广州医科大学");
            schhos.remove("105709-天津中医药大学");
            if (schhos.size() > 0) {
                sendAdminMail("有名额啦", schhos.toString(), "system@fuwo.com", new String[]{"2246488438@qq.com", "1972975537@qq.com"});
            }

            System.out.println(new RestResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
