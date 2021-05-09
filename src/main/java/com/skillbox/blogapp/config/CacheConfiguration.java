package com.skillbox.blogapp.config;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(
        JHipsterProperties properties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        var redisUri = URI.create(properties.getCache().getRedis().getServer()[0]);

        var config = new Config();
        if (properties.getCache().getRedis().isCluster()) {
            var clusterServersConfig = config
                .useClusterServers()
                .setMasterConnectionPoolSize(
                    properties.getCache().getRedis().getConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(
                    properties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(
                    properties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .addNodeAddress(properties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(
                    redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            var singleServerConfig = config
                .useSingleServer()
                .setConnectionPoolSize(properties.getCache().getRedis().getConnectionPoolSize())
                .setConnectionMinimumIdleSize(
                    properties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(
                    properties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .setAddress(properties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(
                    redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
            CreatedExpiryPolicy.factoryOf(
                new Duration(TimeUnit.SECONDS, properties.getCache().getRedis().getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(
        javax.cache.CacheManager cm) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cm);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, com.skillbox.blogapp.repository.UserRepository.USERS_BY_LOGIN_CACHE,
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.repository.UserRepository.USERS_BY_EMAIL_CACHE,
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.User.class.getName(),
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.User.class.getName() + ".authorities",
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.User.class.getName(),
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.Post.class.getName(),
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.Post.class.getName() + ".tags",
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.PostVote.class.getName(),
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.PostComment.class.getName(),
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.Tag.class.getName(),
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.Tag.class.getName() + ".posts",
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.CaptchaCode.class.getName(),
                jcacheConfiguration);
            createCache(cm, com.skillbox.blogapp.model.entity.GlobalSetting.class.getName(),
                jcacheConfiguration);
        };
    }

    private void createCache(
        javax.cache.CacheManager cm,
        String cacheName,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
