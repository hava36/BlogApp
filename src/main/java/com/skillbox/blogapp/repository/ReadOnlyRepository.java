package com.skillbox.blogapp.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ReadOnlyRepository<T, I> extends Repository<T, I> {

    Optional<T> findById(I id);

    List<T> findAll();

    Page<T> findAll(Pageable page);

}
