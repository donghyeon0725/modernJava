package app.messages;

import app.filter.AuditingFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
* @Configurable
* @ComponentScan("app.messages") 삭제
* */
@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean<AuditingFilter> auditingFilterFilterRegistrationBean() {
        // AuditingFilter 타입을 제네릭으로 하는 FilterRegistrationBean을 생성한다.
        FilterRegistrationBean<AuditingFilter> registration = new FilterRegistrationBean<>();

        // 만든 필터를 생성 후 필터 등록 객체에 등록한다.
        AuditingFilter filter = new AuditingFilter();
        registration.setFilter(filter);
        registration.setOrder(Integer.MAX_VALUE);
        registration.setUrlPatterns(Arrays.asList("/messages/*"));
        return registration;
    }

}
