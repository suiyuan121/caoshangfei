package com.zj.caoshangfei.integration.aliyunoss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.zj.caoshangfei.config.OSSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jin.zhang@fuwo.com on 2017/8/18.
 */
@Service
public class AliyunOssClient {

    private final Logger logger = LoggerFactory.getLogger(AliyunOssClient.class);

    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    public String uploadByStream(OSSProperties.Bucket bucket, String filePath, InputStream inputStream) {

        OSSClient ossClient = null;
        String key = "";
        try {
            // 创建OSSClient实例
            ossClient = new OSSClient(ossProperties.getBucketEndpoint(), bucket.getBucketAccessKey(), bucket.getBucketAccessSecret());
            // 上传文件流
            key = filePath;
            PutObjectResult result = ossClient.putObject(bucket.getBucketName(), key, inputStream);

        } catch (OSSException oe) {
            logger.error(("OSSException Error Message: " + oe.getStackTrace()));
            return null;
        } catch (ClientException ce) {
            logger.error("ClientException Error Message: " + ce.getStackTrace());
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return ossProperties.getDomain() + key;

    }

    /**
     * get 使用文件存储的bucket   file
     **/
    public String get(String key) {
        return get(ossProperties.getFile(), key);
    }

    /**
     * get
     **/
    //读取文件,只适合小文件，大文件不要这样读取
    // https://help.aliyun.com/document_detail/32014.html?spm=5176.doc31947.6.665.rWGljf
    public String get(OSSProperties.Bucket bucket, String key) {

        OSSClient ossClient = null;
        try {
            // 创建OSSClient实例
            ossClient = new OSSClient(ossProperties.getBucketEndpoint(), ossProperties.getFile().getBucketAccessKey(),
                    ossProperties.getFile().getBucketAccessSecret());
            OSSObject ossObject = ossClient.getObject(bucket.getBucketName(), key);
            // 读Object内容,ossObject.getObjectContent() 已经在方法里面关闭
            String result = IOUtils.readStreamAsString(ossObject.getObjectContent(), "UTF-8");
            return result;
        } catch (Exception oe) {
            logger.error(("OSS NotFoundException Error Message: " + oe.getMessage()));
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return null;
    }


    public Map<String, String> getForBatch(String bucketName, String... keys) {
        Map<String, String> map = new ConcurrentHashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(keys.length);

        //long time1 = System.currentTimeMillis();
        try {
            // 创建OSSClient实例
            OSSClient ossClient = new OSSClient(ossProperties.getBucketEndpoint(), ossProperties.getFile().getBucketAccessKey(),
                    ossProperties.getFile().getBucketAccessSecret());
            // long time2 = System.currentTimeMillis();
            //logger.info("创建client 耗时=" + (time2 - time1));
            for (String item : keys) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        OSSObject ossObject = ossClient.getObject(bucketName, item);
                        // 读Object内容
                        String result = null;
                        try {
                            result = IOUtils.readStreamAsString(ossObject.getObjectContent(), "UTF-8");
                        } catch (IOException e) {
                            logger.error(("OSS inputString 转 String 异常: " + e.getMessage()));
                        }
                        map.put(item, result);
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();//知道countDownLatch为0
            // long time3 = System.currentTimeMillis();
            // logger.info("批量读取object 耗时=" + (time3 - time2));
            if (ossClient != null) {
                ossClient.shutdown();
            }

        } catch (Exception oe) {
            logger.error(("OSS NotFoundException Error Message: " + oe.getMessage()));
        }

        return map;
    }

}
