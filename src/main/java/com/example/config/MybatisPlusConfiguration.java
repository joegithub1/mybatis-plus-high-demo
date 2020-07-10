package com.example.config;/**
 * @title: MybatisPlusConfiguration
 * @projectName mybatis-plus-high-demo
 * @description:
 * @author HuangJian
 * @date 2020-07-09
 */

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *@description:配置类
 *@author: huangJian
 *@create: 2020-07-09
 */
@Configuration
public class MybatisPlusConfiguration {

    /**
    * @Title: optimisticLockerInterceptor 
    * @Description:  乐观锁  bean
    * @return com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor
    * @date 2020-07-09 
    * @author HuangJian
    */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

    /**
    * @Title: performanceMonitorInterceptor 
    * @Description:  性能分析 bean 一般生产环境不开启
    * @return org.springframework.aop.interceptor.PerformanceMonitorInterceptor
    * @date 2020-07-09 
    * @author HuangJian
    */
    @Bean
    @Profile({"dev","test"})
    public PerformanceMonitorInterceptor performanceMonitorInterceptor(){
        System.out.println("性能分析~~");
        PerformanceMonitorInterceptor pmi = new PerformanceMonitorInterceptor();
        return pmi;
    }
}
