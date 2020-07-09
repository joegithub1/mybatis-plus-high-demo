package com.example;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.example.beans.User;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusHighDemoApplicationTests {

    public static String dir = "E:\\IdeaProjects\\mybatis-plus-high-demo";
    @Autowired
    public UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    /**
     * @return void
     * @Title: testDeleted
     * @Description: 逻辑删除数据
     * @date 2020-07-09
     * @author HuangJian
     */
    @Test
    void testDeleted() {
        int rows = userMapper.deleteById(1094592041087729666L);
        System.out.println("影响的行数：" + rows);
    }
    /**
    * @Title: selectUserList 
    * @Description:  1、不加任何条件 已删除的条件是不查询出来的
     * 2、自定义的SQL不会过滤掉是否删除的标识
     *
    * @return void
    * @date 2020-07-09 
    * @author HuangJian
    */
    @Test
    void selectUserList() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
    /**
    * @Title: updateById
    * @Description:  修改也是未删除状态的数据进行修改
     * UPDATE t_user SET age=? WHERE id=? AND deleted=0
    * @return void
    * @date 2020-07-09
    * @author HuangJian
    */
    @Test
    void updateById(){
        User user = new User();
        user.setId(1281119058405625857L);
        user.setAge(26);
        int rows = userMapper.updateById(user);
        System.out.println("影响行数："+rows);
    }
    @Test
    void insertUser(){
        User user = new User();
        user.setAge(22);
        user.setName("张三");
        user.setEmail("zs@126.com");
        user.setManagerId(1088248166370832385L);
        int rows = userMapper.insert(user);
        System.out.println("影响行数："+rows);
    }
    @Test
    void testGenerator() {
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)
                .setAuthor("HuangJian")//作者
                //生成的路径
                .setOutputDir(dir + "\\src\\main\\java")
                .setFileOverride(true)//是否文件覆盖，如果多次
                .setOpen(false) //是否打开本地目录
                .setServiceName("%sService") //设置生成的service接口名首字母是否为I
                .setIdType(IdType.AUTO) //主键策略
                .setBaseResultMap(true)
                //.setDateType()
                .setBaseColumnList(true);

        //2数据源
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://127.0.0.1:3306/imooc2?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC")
                //.setDriverName("com.mysql.jdbc.Driver")
                .setDriverName("com.alibaba.druid.pool.DruidDataSource")
                .setUsername("root")
                .setPassword("root")
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {

                        //tinyint转换成Boolean
                        if (fieldType.toLowerCase().contains("tinyint")) {
                            System.out.println("转换类型：tinyint转boolean " + fieldType);
                            return DbColumnType.BOOLEAN;
                        }
                        //将数据库中datetime转换成date
                        if (fieldType.toLowerCase().contains("datetime")) {
                            System.out.println("转换类型：datetime转date  " + fieldType);
                            return DbColumnType.DATE;
                        }
                        return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                    }
                });

        //3.策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) // 全局大写命名
                //.setDbColumnUnderline(true) //表名 字段名 是否使用下滑线命名
                .setColumnNaming(NamingStrategy.underline_to_camel)// 配置数据表的字段与实体类的属性名之间映射的策略
                .setNaming(NamingStrategy.underline_to_camel) //数据库表映射到实体的命名策略
                .setEntityLombokModel(true)//配置 lombok 模式
                .setRestControllerStyle(false)//配置 rest 风格的控制器 true（@RestController）  false @Controller
                .setInclude("t_user") //生成的表
                .setTablePrefix("t_"); // 表前缀


        //4.包名策略
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.example")//父包名
                .setController("controller")
                .setEntity("beans")
                .setService("service")
                .setMapper("mapper")
                .setXml("mapper");


        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        //调整xxxMapper.xml的位置
        focList.add(new FileOutConfig("/templates/mapper/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //返回xxxMapper.xml绝对路径位置
                return dir + "\\src\\main\\resources\\mybatis\\mapper\\" + tableInfo.getXmlName() + ".xml";
            }
        });
        cfg.setFileOutConfigList(focList);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);//不使用原来的位置 使用新位置
        // tc.setController(null);
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。

        //5.整合配置
        AutoGenerator ag = new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig)
                .setTemplate(tc)
                .setCfg(cfg);
        ag.execute();

    }
}
