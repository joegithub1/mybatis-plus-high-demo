package com.example.config;/**
 * @title: MybatisPlusConfiguration
 * @projectName mybatis-plus-high-demo
 * @description:
 * @author HuangJian
 * @date 2020-07-09
 */

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

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
    /**
    * @Title: paginationInterceptor 
    * @Description:  分页bean
    * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
    * @date 2020-07-10 
    * @author HuangJian
    */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor p = new PaginationInterceptor();
        //start---------------------------多租户信息表级别过滤  (查询当前登录人信息)
        /*ArrayList<ISqlParser> iSqlParsers = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            //
            @Override
            public Expression getTenantId(boolean select) {
                //这个值应该从session中取出来
                return new LongValue(1088248166370832385L);
            }

            //多租户表中的字段名
            @Override
            public String getTenantIdColumn() {
                return "manager_id";
            }
            //过滤掉不需要多租户的表  true过滤，false不过滤
            @Override
            public boolean doTableFilter(String tableName) {
                //查询t_role表不会查询当前用户ID条件查询
                if("t_role".equalsIgnoreCase(tableName)){
                    return true;
                }
                return false;
            }
        });
        iSqlParsers.add(tenantSqlParser);
        p.setSqlParserList(iSqlParsers);*/

        //start---------------------------多租户信息方法级别过滤  (查询当前登录人信息)
        p.setSqlParserFilter(new ISqlParserFilter() {
            @Override
            public boolean doFilter(MetaObject metaObject) {
                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
                if(!StringUtils.isEmpty(ms.getId()) && ms.getId().contains("getById")){
                    //不增加多租户信息查询
                    return true;
                }
                return false;
            }
        });

        //END---------------------------------------
        return p;
    }
}
