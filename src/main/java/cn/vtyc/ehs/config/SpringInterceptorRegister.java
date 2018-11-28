package cn.vtyc.ehs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringInterceptorRegister extends WebMvcConfigurerAdapter {

    /**
     * TODO 添加spring中的拦截器.
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有路径
        registry.addInterceptor(new XbqInterceptor()).addPathPatterns("/**");
        // 拦截/freemarker后路径
        registry.addInterceptor(new JoeInterceptor()).addPathPatterns("/freemarker/**");
        super.addInterceptors(registry);
    }

    /**
     * TODO  注册静态文件的自定义映射路径
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/image/**").addResourceLocations("classpath:/image/");
        registry.addResourceHandler("/picture/**").addResourceLocations("file:E:/pictures/");
        super.addResourceHandlers(registry);
    }
}
