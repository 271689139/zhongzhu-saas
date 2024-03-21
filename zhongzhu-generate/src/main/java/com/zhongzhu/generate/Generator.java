package com.zhongzhu.generate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * 生成代码
 */
public class Generator {

    public static void main(String[] args) {
        //数据库连接 //数据库url
        String url = "jdbc:mysql://43.136.65.155:3306/lsh-dev?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false";
        //账号
        String username = "liushihao";
        //密码
        String password = "Liushihao1.";
        //全局配置参数
        //作者
        String author = "yuxiang.dai";
        //指定输出目录
        String outputDir = "/Users/admin/Downloads/test/generator";
        //包配置参数 //父包名
        String parent = "com";
        String moduleName = "zhongzhu.system";
        //Entity 实体类包名
        String entity = "domain";
        //Mapper 包名
        String mapper = "mapper";
        //Mapper XML 包名
        String mapperXml = "mapper";
        //Service 包名
        String service = "service";
        //Service Impl 包名
        String serviceImpl = "service.impl";
        /*String controller = "controller";//Controller 包名*/
        //要生成的数据库表
        List<String> tables = new ArrayList<>();
        tables.add("system_version");

        FastAutoGenerator.create(url, username, password)
                //全局配置
                .globalConfig(builder -> {
                    builder.author(author).outputDir(outputDir)
                           .commentDate("yyyy-MM-dd");//注释日期
                })
                //包配置
                .packageConfig(builder -> {
                    builder.parent(parent).moduleName(moduleName).entity(entity).mapper(mapper).xml(mapperXml).service(service).serviceImpl(serviceImpl);
//                    .controller(controller);
                })
                //策略配置
                .strategyConfig(builder -> builder.addInclude(tables)
                        //开启生成实体类
                        .entityBuilder().enableLombok()//开启 lombok 模型
                        .enableTableFieldAnnotation()//开启生成实体时生成字段注解
                        //开启生成mapper
                        .mapperBuilder().enableBaseResultMap()//启用 BaseResultMap 生成
                        .superClass(BaseMapper.class)//设置父类
                        .enableMapperAnnotation()//开启 @Mapper 注解
                        .formatMapperFileName("%sMapper")//格式化 mapper 文件名称
                        .formatXmlFileName("%sMapper")//格式化 xml 实现类文件名称
                        //开启生成service及impl
                        .serviceBuilder().formatServiceFileName("%sService")//格式化 service 接口文件名称
                        .formatServiceImplFileName("%sServiceImpl")//格式化 service 实现类文件名称
                        //开启生成controller
                        .controllerBuilder()
                        // 映射路径使用连字符格式，而不是驼峰
                        .enableHyphenStyle().formatFileName("%sController")//格式化文件名称
                        .enableRestStyle()).templateEngine(new VelocityTemplateEngine())
                .templateConfig(builder -> builder.controller(""))
                .execute();
    }

}
