package com.zj.caoshangfei.controller;

import com.zj.caoshangfei.common.bean.PageList;
import com.zj.caoshangfei.model.VideoEntity;
import com.zj.caoshangfei.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private VideoService videoService;

    private static final int PAGE_SIZE = 50;

    @GetMapping({"/index.html", "/"})
    public String index(Model model) {

        return list(model, 1, "");
    }


    /**
     * @param page 从1开始
     */
    @GetMapping("/list-{page}")
    public String list(Model model, @PathVariable("page") int page, String keyword) {
        if (page <= 0) {
            throw new RuntimeException();
        }
        PageList<VideoEntity> pageList = videoService.findAll(keyword, page, PAGE_SIZE);
        model.addAttribute("pageList", pageList);

        List<VideoEntity> topList = videoService.findAll((byte) 2);

        model.addAttribute("topList", topList);

        return "list";
    }

    @GetMapping("/list")
    public String list(Model model, String keyword) {
        return list(model, 1, keyword);
    }


}
