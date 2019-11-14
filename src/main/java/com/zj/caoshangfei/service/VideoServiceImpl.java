package com.zj.caoshangfei.service;

import com.zj.caoshangfei.common.bean.PageList;
import com.zj.caoshangfei.model.VideoEntity;
import com.zj.caoshangfei.service.repository.VideoEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author zhangjin
 * @date 2019-10-31
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoEntityRepository videoEntityRepository;

    @Override
    public PageList<VideoEntity> findAll(String keyword, int page, int size) {
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains());

        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle(keyword);
        Example<VideoEntity> example = Example.of(videoEntity, matcher);


        long count = videoEntityRepository.count(example);

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page - 1, size, sort);


        Page<VideoEntity> videos = videoEntityRepository.findAll(example, pageable);

        return new PageList<>(pageable, count, videos.getContent());
    }
}
