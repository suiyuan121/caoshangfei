package com.zj.caoshangfei.controller.rest;

import com.zj.caoshangfei.service.CaoliuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/rest/task")
public class TaskRestController {

    @Autowired
    private CaoliuService caoliuService;


    @GetMapping("/list")
    public String triggerListTask() {

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                for (int i = 1; i <= 600; i++) {
                    log.info("更新列表 i={}", i);
                    caoliuService.getList(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "success";
        });
        return "SUCCESS";
    }


}
