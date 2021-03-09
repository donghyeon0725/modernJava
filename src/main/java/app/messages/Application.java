package app.messages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 스프링이 자동설정을 할 수 있도록 하는 태그
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // SpringApplication을 구동한다.
        SpringApplication.run(Application.class, args);
    }
}
