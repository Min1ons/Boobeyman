package com.salmon.happyrmmall.mall.config;

/**
 * @date 2022-1-16 - 17:10
 * Created by Salmon
 */
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Swagger2API文档的配置
 */
@Configuration
@EnableOpenApi
public class Swagger3Config {

    /**
     * 配置swagger的docket
     * @return
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .ignoredParameterTypes(HttpServletRequest.class, HttpSession.class, HttpServletResponse.class)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("com.salmon.happyrmmall.mall.controller"))
                //为有@Api注解的Controller生成API文档
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //为有@ApiOperation注解的方法生成API文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置swagger的ApiInfo
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("happyrmmall Api  Document")
                .description("happyrmmall接口文档")
                .contact(new Contact("salmon1802", "https://github.com/salmon1802/happyrmmall_2.0.1", "757635525@qq.com"))
                .version("2.0")
                .build();
    }
}