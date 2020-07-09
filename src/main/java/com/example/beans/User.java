package com.example.beans;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author HuangJian
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    //IdType.AUTO 根据数据库自定策略自增
    //@TableId(value = "id", type = IdType.AUTO)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 直属上级id
     */
    private Long managerId;

    /**
     * 创建时间
     */
    //新增时填充
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    //修改时填充
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 逻辑删除标识(0.未删除,1.已删除)
     */
    @TableLogic(delval = "1",value = "0")
    @TableField(select = false)//查询的时候不返回这个字段
    private Integer deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
