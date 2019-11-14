package com.zj.caoshangfei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by Jerry on 2017/5/26.
 */
@SpringBootApplication
@ServletComponentScan
public class Application {

    public static final String RELEASE = "v1.0.0_M1";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
