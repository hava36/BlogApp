package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CaptchaCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaptchaCodeRepository extends JpaRepository<CaptchaCode, Integer> {

}
