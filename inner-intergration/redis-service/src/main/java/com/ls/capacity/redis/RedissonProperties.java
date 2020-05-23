package com.ls.capacity.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis.redission")
public class RedissonProperties {
    private String config;
    private String enable;

    public String getConfig(){
        return  config;
    }
    public  void setConfig(String config){
        this.config=config;

    }
    public String getEnable(){
        return enable;
    }
    public  void  setEnable(String enable){
        this.enable=enable;
    }

}
