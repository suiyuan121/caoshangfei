package com.zj.caoshangfei.controller;

import com.zj.caoshangfei.model.VideoEntity;
import com.zj.caoshangfei.service.repository.VideoEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DetailController {


    @Autowired
    private VideoEntityRepository videoEntityRepository;

    @GetMapping("/{id}.html")
    public String index(Model model, @PathVariable Long id) {
        Assert.notNull(id, "id 不能为空");

        VideoEntity videoEntity = videoEntityRepository.getOne(id);
        Assert.notNull(videoEntity, "视频未找到");

        model.addAttribute("video", videoEntity);

        return "detail";

    }


}
