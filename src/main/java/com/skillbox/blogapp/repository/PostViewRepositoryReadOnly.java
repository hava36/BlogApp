package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.PostView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostViewRepositoryReadOnly extends ReadOnlyRepository<PostView, Integer> {

    Page<PostView> findByIsActive(Pageable pageable, @Param("is_active") Integer isActive);

}
