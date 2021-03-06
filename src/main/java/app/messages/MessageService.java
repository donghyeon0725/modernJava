package app.messages;

public class MessageService {
    private MessageRepository repository;

    //생성시 레포지터리를 주입받음 => 의존성을 주입받음. 제어의 역전
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }
    // 데이터를 받으면 메세지 객체를 만들어 전달한다.
    public void save(String text) {
        this.repository.saveMessage(new Message(text));
    }

}
