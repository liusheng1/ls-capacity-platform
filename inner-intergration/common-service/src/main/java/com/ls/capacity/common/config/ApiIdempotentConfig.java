package com.ls.capacity.common.config;

import com.ls.capacity.common.interceptor.AccessLimitInterceptor;
import com.ls.capacity.common.interceptor.ApiIdempotentInterceptor;
import com.ls.capacity.redis.util.RedisUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author liusheng
 */
@Configuration
@ConditionalOnClass(WebMvcConfigurer.class)
public class ApiIdempotentConfig implements WebMvcConfigurer{
    @Resource
    private RedisTemplate<String ,Object> redisTemplate;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessLimitInterceptor(redisUtil));
        registry.addInterceptor(new ApiIdempotentInterceptor(redisTemplate)).addPathPatterns("/**");

    }
}
