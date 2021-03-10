package app.messages;

import java.util.Date;
import java.util.Objects;

public class Message {

    // For unsaved message, the id will be null
    private Integer id;
    private String text;
    private Date createdDate;

    public Message(String text) {
        this.text = text;
        this.createdDate = new Date();
    }

    public Message(int id, String text, Date createdDate) {
        this.id = id;
        this.text = text;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        // 주소가 같으면
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        // 아이디가 같으면 같은 주소
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        // 객체 고유값을 생성한다. 같은 값을 넣으면 같은 해시값이 나오므로 키값을 인자로 전달해주어야 equals를 제대로 비교할 수 있다.
        // 키값이 2개이면 인자를 2개를 준다.
        return Objects.hash(id);
    }
}