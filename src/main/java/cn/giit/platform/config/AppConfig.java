package cn.giit.platform.config;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class AppConfig {
    /**
     * 配置项目WebSocket支持
     * @return server endpoint exporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 设置文件上传位置
     * @return config
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String path = System.getProperty("user.home") +
                File.separator + "Documents" +
                File.separator + "tour" +
                File.separator + "upload";
        factory.setLocation(path);
        return factory.createMultipartConfig();
    }

    /**
     * 配置Http REST请求模板
     * @return rest Template
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    /**
     * 设置跨域
     * @return cors filters
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        return new CorsFilter(configSource);
    }

    /**
     * 设置RequestContext监听器
     * @return request context listener
     */
    @Bean
    public RequestContextListener requestContextListener() {
        log.info("RequestContextListener initial successful...");
        return new RequestContextListener();
    }

    /**
     * 配置全局Gson对象
     * @return gson
     */
    @Bean
    public Gson gson() {
        return new Gson();
    }
}
