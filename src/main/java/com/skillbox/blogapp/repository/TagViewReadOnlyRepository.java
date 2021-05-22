package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.TagView;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tag View entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagViewReadOnlyRepository extends ReadOnlyRepository<TagView, Integer> {

    List<TagView> findByNameContainingIgnoreCase(@Param("name") String name);

}
