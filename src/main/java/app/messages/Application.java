package app.messages;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 스프링 컨테이너의 역할을 하는 것 같다.
public class Application {
    public static void main(String[] args) {
        // AppConfig 설정을 가져온다. => 설정파일을 읽어옴
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        // 설정파일에서 필요한 객체를 생성함
        MessageService messageService = context.getBean(MessageService.class); // 설정에서 사용에 필요한 값을 가져다가 쓰는 방식. 자동으로 맞는 타입의 빈을 불러와준다. 만약 같은 타입을 리턴하는 빈이 2개 이상이면 에러가 난다. 그럴 때는 이름으로 가져올 수 있다.
        messageService.save("Hello, Spring"); //클래스에 해당하는 의존성을 주입해준다.


        //이름으로 가져오는 방식
        //MessageService messageService = (MessageService)context.getBean("messageService");
        //messageService.save("Hello, Spring");

    }
}
