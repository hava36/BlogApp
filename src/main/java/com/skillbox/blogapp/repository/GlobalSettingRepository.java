package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.GlobalSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GlobalSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GlobalSettingRepository extends JpaRepository<GlobalSetting, Integer> {

}
