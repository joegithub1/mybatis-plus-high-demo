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
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *@description: 默认填充字段类
 *@author: huangJian
 *@create: 2020-07-09
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /*
    如果要修改创建人，修改人， 可以从request.Context.XXX中获取登录人

     */


    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("insertFill ~~");
        //setInsertFieldValByName(); 这个方法已过时

        //判断属性是否有set createTime 才执行
        if(metaObject.hasSetter("createTime")){
            setFieldValByName("createTime",new Date(),metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //setUpdateFieldValByName() 这个方法已过时
        //判断属性是否有set updateTime 才执行
        if(metaObject.hasSetter("updateTime")){
            //如果属性上没有值再默认填充  有值就不再填充默认值
            Object val = getFieldValByName("updateTime", metaObject);
            if(StringUtils.isEmpty(val)){
                System.out.println("updateFill ~~");
                setFieldValByName("updateTime",new Date(),metaObject);
            }

        }

    }
}
