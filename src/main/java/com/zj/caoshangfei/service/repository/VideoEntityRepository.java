package com.zj.caoshangfei.service.repository;

import com.zj.caoshangfei.model.VideoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

import javax.validation.constraints.Size;

@RepositoryRestResource
public interface VideoEntityRepository extends JpaRepository<VideoEntity, Long> {

    VideoEntity findFirstByUrl(String url);

    List<VideoEntity> findByType(byte type);

}

