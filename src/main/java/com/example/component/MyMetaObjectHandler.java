package com.example.component;/**
 * @title: MyMetdObjectHandler
 * @projectName mybatis-plus-high-demo
 * @description:
 * @author HuangJian
 * @date 2020-07-09
 */

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *@description:
 *@author: huangJian
 *@create: 2020-07-09
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("insertFill ~~");
        //setInsertFieldValByName(); 这个方法已过时
        setFieldValByName("createTime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("updateFill ~~");
        //setUpdateFieldValByName() 这个方法已过时
        setFieldValByName("updateTime",new Date(),metaObject);
    }
}
