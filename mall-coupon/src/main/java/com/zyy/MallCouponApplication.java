package com.zyy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *  如何实现nacos配置中心
 *      1.引入config依赖
 *         <dependency>
 *             <groupId>com.alibaba.cloud</groupId>
 *             <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 *         </dependency>
 *      2.创建bootstrap.properties (springboot默认会先于application.properties执行)
 *          spring.application.name=mall-coupon
 *          spring.cloud.nacos.config.server-addr=127.0.0.1:8848
 *      3.在配置中心添加[当前应用名.properties]的数据集（data Id）   如命名为 mall-coupon.properties
 *      4.在xxx.properties中自定义任何配置 [不能yml]
 *      5.获取配置信息 @Value正常获取； @RefreshScope加在Controller实现动态刷新 以观察出动态变化
 *      6.相同属性值，nacos配置中心为优先
 *
 *  细节
 *      1.命名空间 默认public,prod,dev,.....  类似于使用不同的配置文件，达到生产、测试隔离
 *        在bootstrap.properties指定命名空间  一定要是对应的Id
 *        spring.cloud.nacos.config.namespace=a746b4ec-5475-49c8-897d-acb68224a8e7
 *        可以基于环境隔离，也可以基于微服务之间隔离-将命名空间设定为服务名称
 *      2.配置分组
 *          默认分组都在[DEFAULT_GROUP]; 在新建配置的时候可以指定，跟隔离效果是一个道理，在bootstrap.properties可以指定组
 *          spring.cloud.nacos.config.group=分组名称
 *
 *      3. 同时加载多个配置集
 *          配置中心没有默认的分组 会读取本地配置文件
 *          将 application.yml 拆分为具体功能的配置文件配置在nacos,
 *          然后bootstrap.properties使用
 *              spring.cloud.nacos.config.extension-configs[0].data-id=datasource.yml
 *              spring.cloud.nacos.config.extension-configs[0].group=dev
 *              spring.cloud.nacos.config.extension-configs[0].refresh=true
 *
 *         其实最后只需要保留bootstrap.properties，其他的都写到nacos里,配置就行
 */

@EnableDiscoveryClient // 开启服务的注册与发现
@SpringBootApplication
public class MallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallCouponApplication.class, args);
    }

}
