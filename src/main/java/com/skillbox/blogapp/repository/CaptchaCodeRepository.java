package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.CaptchaCode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CaptchaCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaptchaCodeRepository extends JpaRepository<CaptchaCode, Integer> {

    @Query("from CaptchaCode where time <= :time")
    List<CaptchaCode> findCaptchaCodeByLessThenTime(@Param("time") Instant time);

    Optional<CaptchaCode> findOneBySecretCode(@Param("code") String code);

}
