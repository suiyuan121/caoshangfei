package com.zj.caoshangfei;

import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SimpleThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

/**
 * Created by Jerry on 2017/5/31.
 */
@Configuration
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
public class WebAppConfiguration {


    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer(EntityManagerFactory entityManagerFactory) {
        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.setDefaultMediaType(MediaType.APPLICATION_JSON);

                //Expose all IDs when using Spring Data Rest
                Metamodel metamodel = entityManagerFactory.getMetamodel();
                for (ManagedType<?> managedType : metamodel.getManagedTypes()) {
                    Class<?> javaType = managedType.getJavaType();
                    if (javaType.isAnnotationPresent(Entity.class)) {
                        config.exposeIdsFor(managedType.getJavaType());
                    }
                }
            }
        };
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(64);
        executor.setMaxPoolSize(128);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("caoshangfei-tasks-");
        executor.initialize();
        return executor;
    }

    @Bean
    public AsyncListenableTaskExecutor asyncTaskExecutor() {
        SimpleThreadPoolTaskExecutor executor = new SimpleThreadPoolTaskExecutor();
        executor.setThreadCount(64);
        executor.setThreadNamePrefix("caoshangfei-async-tasks-");
        return executor;
    }


    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(512);
        cm.setDefaultMaxPerRoute(512);
        return cm;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(HttpClientConnectionManager httpClientConnectionManager) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(10000).build())
                .disableCookieManagement().setConnectionManager(httpClientConnectionManager).build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);
        factory.setConnectionRequestTimeout(5000);
        factory.setConnectTimeout(3600);
        factory.setReadTimeout(2000);
        return factory;
    }

    @Bean
    public RestTemplate restOperations(ClientHttpRequestFactory clienthttpRequestFactory) {

        RestTemplate restTemplate = new RestTemplate(clienthttpRequestFactory);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList();
        messageConverters.addAll(restTemplate.getMessageConverters());
        for (HttpMessageConverter<?> converter : messageConverters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("gbk"));
            }
        }
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }


}
