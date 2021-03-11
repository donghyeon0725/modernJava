package app.messages;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

// db에 들어갈 각종 타입이나 속성 등등 메타데이터를 어노테이션을 통해서 설정해주었다.
// 이 클래스를 Entity 클래스로 표시한다.
@Entity
// 매핑되는 테이블 이름을 지정하기 위해 사용했다.
@Table(name="messages")
public class Message {

    // 이 필드를 키로 사용하기 위해서 지정한다.
    @Id
    // 그리고 이 값을 생성하는 전략은 GenerationType.IDENTITY 이다. 이것은 DB의 AUTO_INCREMENT 설정과 완정 동일하다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Integer id;
    // 기본적으로 @Column 어노테이션은 어노테이션의 nullable = false 과 같은 속성 기반으로 데이터 검증을 수행하지 않는다.
    // hbm(Hibernate mapping)으로 부터 DDL을 생성하는 hbm2ddl 에서 이 메타데이터를 사용한다.
    // 데이터 검증을 위해서는 Bean Validation 2.0을 사용하면 된다. => java에서 객체를 생성할 때가 아닌, sql을 매핑하고 생성할 때 작동된다는 의미
    // 이 어노테이션을 사용하지 않는다면 기본적으로 사용된 것으로 간주된다.
    @Column(name = "text", nullable = false, length = 128)
    private String text;
    @Column(name = "created_date", nullable = false)
    // java.util.Date 또는 java.util.Calendar 타입에는 필수적으로 들어가야 한다.
    // JDBC 가 이해할 수 있는 sql의 java.sql.Timestamp 타입과 매핑하기 위함이다.
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    // 하이버네이트는 기본생성자가 꼭 필요하다. DB에서 메시지 레코드를 가져올 때 Message 객체를 재구성하는데, 이때 기본생성자를 호출하기 때문이다!
    public Message() {}

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