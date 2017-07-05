package com.yzwy.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/to-login").setViewName("login");
//        registry.addViewController("/logout").setViewName("login");
//        registry.addViewController("/error").setViewName("error");
//        registry.addViewController("/403").setViewName("error/403");
//        registry.addViewController("/401").setViewName("error/401");
//        registry.addViewController("/404").setViewName("error/404");
        registry.addViewController("/monitor").setViewName("util/MqttMonitor");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //todo 导航栏？
//        registry.addInterceptor(new NavMenuActiveInterceptor());
    }

//    /**
//     * 消息转换
//     */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//
//
//        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
//
//        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        messageConverter.setObjectMapper(objectMapper);
//        converters.add(messageConverter);
//    }
    /**
     * 消息转换
     */
    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> additional = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        messageConverter.setObjectMapper(objectMapper);
        HttpMessageConverter<?> another = messageConverter;
        return new HttpMessageConverters(additional, another);
    }

    /**
     * tomcat编码
     */
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.setUriEncoding(Charset.forName("UTF-8"));
        return tomcat;
    }

//静态资源映射 可直接访问下载文件
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:E:/uploads/");
        super.addResourceHandlers(registry);
    }
}
