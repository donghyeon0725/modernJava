package app.messages;

import app.filter.AuditingFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Arrays;

/**
* @Configurable
* @ComponentScan("app.messages") 삭제
* */
@Configuration
@ComponentScan("app.messages")
public class AppConfig {

    private DataSource dataSource;

    // 스프링 부트가 관리해주는 dataSource를 주입받는다.
    public AppConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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

    // 스프링 ORM 기반의 sessionFactory를 사용하려면 FactoryBean인 LocalSessionFactoryBean 을 생성하면 된다.
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // Entity 클래스가 있는 패키지를 검색할 수 있도록 경로를 지정해주어야 한다.
        sessionFactoryBean.setPackagesToScan("app.messages");
        return sessionFactoryBean;
    }

}
