package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query(value =
        "select t.*, count(t2p.post_id) / (select count(t2p.post_id) as post_count from tag2post t2p group by t2p.tag_id order by post_count desc limit 1) weight "
            + "from tag t left join tag2post t2p on t.id = t2p.tag_id where t.name like %:name% group by t.id;", nativeQuery = true)
    List<Tag> findAllWithWeight(@Param("name") String name);

}
