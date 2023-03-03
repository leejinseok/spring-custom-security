package spring.custom.security.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.custom.security.filter.BasicAuthFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<BasicAuthFilter> authFilterBean() {
        FilterRegistrationBean<BasicAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new BasicAuthFilter());
        registration.setOrder(1);
        registration.addUrlPatterns("/api/*");
        return registration;
    }

}
