package app.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageService {
    @Autowired
    private MessageRepository repository;

    //생성시 레포지터리를 주입받음 => 의존성을 주입받음. 제어의 역전
    /*@Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }*/
    // 데이터를 받으면 메세지 객체를 만들어 전달한다.
    public void save(String text) {
        this.repository.saveMessage(new Message(text));
    }

    // 생성자 대신 setter와 함께 사용이 가능하다. => 권장하지는 않는 방법이다.
    /*@Required
    public void setRepository(MessageRepository repository) {
        this.repository = repository;
    }*/
}

