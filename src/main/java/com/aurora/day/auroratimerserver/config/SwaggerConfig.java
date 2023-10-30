package com.aurora.day.auroratimerserver.config;

import cn.hutool.core.util.RandomUtil;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//@EnableSwagger2 swagger2 旧版本的启动方式
@EnableKnife4j
@Configuration
public class SwaggerConfig {

    public static final String SwaggerTitle = "AuroraTimerServer";
    public static final String SwaggerDescription = "极光工作室计时器后端";
    public static final String Version = "1.0";

    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags()!=null){
                openApi.getTags().forEach(tag -> {
                    Map<String,Object> map=new HashMap<>();
                    map.put("x-order", RandomUtil.randomInt(0,100));
                    tag.setExtensions(map);
                });
            }
            if(openApi.getPaths()!=null){
                openApi.addExtension("x-test123","333");
                openApi.getPaths().addExtension("x-abb",RandomUtil.randomInt(1,100));
            }
        };
    }

    @Bean
    public GroupedOpenApi userApi(){
        String[] paths = { "/**" };
        String[] packagedToMatch = { "com.aurora.day.auroratimerserver" };
        return GroupedOpenApi.builder().group("swagger-config")
                .pathsToMatch(paths)
                .addOperationCustomizer((operation, handlerMethod) -> operation.addParametersItem(new HeaderParameter().name("groupCode").example("极光工作室").description("极光工作室·API").schema(new StringSchema()._default("AuroraTimer").name("groupCode").description("AuroraTimer"))))
                .packagesToScan(packagedToMatch).build();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo());
    }
    /**
     * 添加摘要信息
     * @return 返回Info对象
     */
    private Info apiInfo() {
        return new Info()
                // 设置标题
                .title(SwaggerTitle)
                // 服务条款
                .termsOfService("No terms of service")
                // 描述
                .description(SwaggerDescription)
                // 作者信息
                .contact(new Contact().name("DAYGood_Time").url("https://github.com/DAYGoodTime").email("osutimemail@gmail.com"))
                // 版本
                .version(Version)
                //协议
                .license(new License().name("The Apache License")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));// 协议url
    }

}