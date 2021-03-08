package app.messages;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

/**
 * 스프링 컨테이너를 인스턴스화 하는데 사용할 설정 메타데이터
 *
 * */

@Configurable // 스프링에게 Bean으로 구성된 클래스임을 알리는 것. Bean을 관리하기 위해 꼭 사용해줘야 한다.
@ComponentScan("app.messages") // 경로에 맞춰 ComponentScan 어노테이션을 써주면, Bean을 static 객체로 만들어준다.
public class AppConfig {
// 설정을 하나의 클래스에서 관리할 수 있게 되었다.
// MessageService에 @Component 어노테이션이 붙음에 따라 더이상 객체를 생성해주는 설정 클래스를 사용할 필요가 없게 되었다.

    /*public MessageRepository messageRepository() {
        return new MessageRepository();
    }*/

    /*@Bean //("messageService12") // 자동으로 messageService 이라는 이름이 붙는다. 이 이름은 메소드의 이름이다.
    MessageService messageService() {
        return new MessageService(messageRepository());
    }*/

/*    @Bean("messageService12")
    MessageService messageService1() {
        return new MessageService(messageRepository());
    }*/

}
