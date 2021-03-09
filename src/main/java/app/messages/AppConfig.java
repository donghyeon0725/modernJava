package app.messages;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 스프링 컨테이너를 인스턴스화 하는데 사용할 설정 메타데이터
 * 설정을 하나의 클래스에서 관리할 수 있게 되었다.
 * MessageService에 @Component 어노테이션이 붙이고 @Autowired 를 사용하면
 * 더이상 객체를 생성해주는 설정 클래스를 사용할 필요가 없게 된다.
 * 스프링이 자동으로 의존성을 관리해주기 때문이다.
 * 따라서 아래에서 객체를 생성해주는 모든 메소드를, 주석 처리해도 된다.
 * */

// 스프링에게 Bean으로 구성된 클래스임을 알리는 것. Bean을 관리하기 위해 꼭 사용해줘야 한다.
@Configurable
// 경로에 맞춰 ComponentScan 어노테이션을 써주면, Bean을 static 객체로 만들어준다.
@ComponentScan("app.messages")
public class AppConfig {
    /*
    public MessageRepository messageRepository() {
        return new MessageRepository();
    }
    */

    /*
    @Bean // 자동으로 messageService 이라는 이름이 붙는다. 이 이름은 메소드의 이름이다.
    MessageService messageService() {
        return new MessageService(messageRepository());
    }

    @Bean("messageService12") // messageService12 이라는 이름으로 getBean 을 통해 사용이 가능해다.
    MessageService messageService1() {
        return new MessageService(messageRepository());
    }

    */

}
