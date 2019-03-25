package cn.giit.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import cn.giit.platform.interceptor.UserInterceptor;

@Configuration
public class RegisterInterceptor implements WebMvcConfigurer {

    private final UserInterceptor userInterceptor;

    @Autowired
    public RegisterInterceptor(UserInterceptor userInterceptor) {
        this.userInterceptor = userInterceptor;
    }

    /**
     * 添加拦截器
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/**", // 为了方便测试某些Api，可以暂时写到这下面
                        "/api/authorize",
                        "/api/wx-sign-in",
                        "/api/sign-in",
                        "/api/sign-out",
                        "/api/sign-up",
                        "/401",
                        "/403",
                        "/404",
                        "/");
    }


}
