package com.Application;

import com.Application.Command.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * configures the Application
 */
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    private final RequestInterceptor requestInterceptor;

    @Autowired
    public ApplicationConfig(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    /**
     * adds the request interceptor to the application
     * enables the interceptor for the path /api
     * @param registry the registry of the application
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns("/api");
    }
}
