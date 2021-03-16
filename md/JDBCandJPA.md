스프링 JDBC와 JPA
-

### JDBC

* JDBC(Java DataBase Connectivity의 줄임말) 드라이버는 특정 데이터 베이스에 대한 JBDC API 구현체이다. 예를 들어 com.mysql.jdbc.Driver 는 MYSQL 데이터베이스에 대한 드라이버 클래스의 이름이고 jdbcDriver는 순수 자바로 작성된 관계형 DB인 HSQLDB에 대한 클래스 이름
* 스프링 JDBC는 JDBC API 위에서 데이터베이스와 더 쉽게 사용작용할 수 있게함 

### JPA
* JPA(Java Persistence API의 줄임말). 
* JPA는 자바 객체의 영속성(사라지지 않음)을 위한 자바의 표준화된 접근 방식을 정의
* 객체 지향 모델과 관계형 데이터베이스에 저장된 데이터 사이의 간격을 메우기 위해 **객체 관계형 매핑(ORM, Object-Relational Mapping)** 메커니즘을 사용
* 하이버네이트 ORM은 가장 흔히 사용하는 JPA 표준을 구현한 구현체

### JDBC와 JPA
* JDBC와 JPA는 서로 다른 문제를 해결하는 두가지 API 세트
* JDBC API는 DB와의 상호작용을 해결하지만 JPA는 객체지향방식으로 DB에 객체를 저장하고 가져오는 방법을 담당. 즉, JPA 구현체는 JDBC 드라이버에 의존한다.

![JVM과 관계형 DB의 매핑](./src/main/resources/image/JPA.jpg)

---

시작하기
-
* mysql 설치
<https://goddaehee.tistory.com/277/>

* DB 생성
```sql
CREATE DATABASE app_messages default CHARACTER SET UTF8;
SHOW databases;
USE app_messages;
```


pom.xml 에 아래와 같은 내용 추가
```html
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<!-- datasource의 인스턴스를 기동해서 스프링컨테이너에서 사용할 수 있도록 해주고, 커넥션 풀을 설정한다. -->

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
<!-- jdbc 드라이버, 버전이 없는데 이는 부트 내부적으로 자동 처리하기 때문이다. -->
```


```java
PreparedStatement ps = c.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
```
* Statement.RETURN_GENERATED_KEYS 코드를 통해서 insert 한 뒤 키 값이 필요하다는 명시를 해줄 수 있다.
* 그 키는 ps.getGeneratedKeys(); 를 통해 받아온다.

* c.close(); 를 통해 커넥션을 닫아야 커넥션 풀로 연결이 반환되고 그렇지 않으면 다른 연결에 의해 재사용 된다.
* 최종적으로 DataSourceUtils.releaseConnection(c, dataSource); 를 통해 연결을 반환했다.

스프링 JDBC
-
* JDBC는 JDBC 위의 추상화 계층을 제공함. jdbcTemplate 클래스가 핵심. 따라서 쿼리문을 준비하고 쿼리 결과를 처리할 방법만 지정하면 됨  
* 한편, NamedParameterJdbcTemplate 클래스는 JdbcTemplate 객체를 래핑한 클래스로 JDBC의 "?" 플레이스홀더 대신에 지정한 이름을 지정한 매개변수를 사용할 수 있도록 한다.
* 또한 스프링 JDBC는 DB에서 제공하는 메타데이터로, JDBC 작업을 단순화하기 위해 SimpleJdbcInsert와 SimpleJdbcCall을 제공한다.
* 메타 데이터(컬럼타입)는 DatabaseMetaData 인스턴스를 반환하는 connection.getMetaData() 메소드를 호출해 가져온다.
* 그러면 메타데이터에 대해 걱정할 필요가 없다.

* 스프링 JDBC 는 작업을 자바 객체로 표현하는 방법도 제공
* MappingSqlQuery 객체를 생성해서 DB 쿼리를 실행
* SqlUpdate 객체를 생성해 삽입/업데이트 작업 수행 가능
* 그리고 StoreProcedure 객체를 새성해 DB에서 저장 프로시저를 호출할 수 있음
* 이 객체들은 재사용이 가능하며 스레드 세이프(멀티 스레드가 접근해도 문제가 없음)이다

스프링 JDBC를 이용해서 DB에 접근하면 아래와 같다.
```java
// NamedParameterJdbcTemplate 객체 사용. 외부에서 주입해줄 것을 요청 => class 위에 @Component 을 사용해서
private NamedParameterJdbcTemplate jdbcTemplate;

public MessageRepository(DataSource dataSource) {
    this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
}

// 스프링 JDBC를 사용한 DB 접근의 구현은 아주 쉽다.
public Message saveMessage(Message message) {
    // 새로운 메시지에서 생성될 id를 보관할 키홀더이다.
    GeneratedKeyHolder holder = new GeneratedKeyHolder();
    // 파라미터를 관리할 객체이다.
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("text", message.getText());
    // insert 구문에서 사용한 :createdDate 의 이름부분과 동일해야 한다.
    params.addValue("createdDate", message.getCreatedDate()); 
    String insertSQL = "INSERT INTO messages (`id`, `text`, `created_date`) VALUE (null, :text, :createdDate)";
    
    try {
        this.jdbcTemplate.update(insertSQL, params, holder);
    } catch (DataAccessException e) {
        logger.error("Failed to save message", e);
        return null;
    }
    return new Message(holder.getKey().intValue(), message.getText(), message.getCreatedDate());
}
```
보면 
1. 키홀더 생성
2. 파라미터 생성
3. sql 쿼리 준비
4. connection 요청  

으로 아주 단순하게 구현되어 있다.  
이는 POJO(Plain Old Java Object) JDBC 로 구현된 것보다 훨씬 간편하다.  
허나 이는 sql 의 param 들을 일일이 관리해줘야 한다는 단점이 있다.  
ORM으로 JDBC를 관리하는 하이버네이트를 살펴보자


ORM이란?
-
ORM이란 객체와 DB의 테이블이 매핑을 이루는 것을 의미 (Java 진영에 국한된 기술은 아니라고함)
즉, 객체가 테이블이 되도록 매핑 시켜주는 것을 의미
ORM을 이용하면 SQL Query가 아닌 직관적인 코드(메서드)로서 데이터를 조작할 수 있음
다만, 오버헤드 문제를 일으켜 성능 저하를 일으킬 수 있어서 ORM의 한 종류인 JPA와 Mybatis를 같이 사용하기도 함


스프링부트 하이버네이트 시작하기
-
1. 스프링 orm 의존성과 하이버네이트 의존성 추가하기
2. VO 객체 (또는 DTO)를 어노테이션을 이용해서 Entity 클래스로 만들고, 테이블과 매핑 시키기
3. 스프링 빈에서 datasource를 주입받아 LocalSessionFactoryBean 를 사용하겠다고 명시하기. 이 때 VO 객체를 스캔할 수 있도록 패키지 명시하기
4. LocalSessionFactoryBean 에서 세션을 의존성으로 주입받아 사용하기
---

### 1. 스프링 orm 의존성과 하이버네이트 의존성 추가하기
```html
<!-- 하이버네트 사용을 위한 의존성 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-orm</artifactId>
</dependency>
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
</dependency>
```

### 2 VO 객체 (또는 DTO)를 어노테이션을 이용해서 Entity 클래스로 만들고, 테이블과 매핑 시키기
```java
@Entity
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Integer id;
    @Column(name = "text", nullable = false, length = 128)
    private String text;
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Message() {}
    ...
}
```
* @Entity: 클래스를 Entity 클래스로 표시한다.
* @Table(name="messages"): messages 테이블과 매핑한다.
* @Id: 필드를 키로 사용하기 위해서 지정한다.
* @GeneratedValue(strategy = GenerationType.IDENTITY): 그리고 이 값을 생성하는 전략을 GenerationType.IDENTITY 으로 생성함으로써 이 컬럼은 DB의 AUTO_INCREMENT 설정과 완정 동일해진다.
* @Column 어노테이션으로 필드를 컬럼이라고 명시하고, 속성을 이용해 필드를 컬럼과 매핑시킨다. 이 때 @Column을 사용하지 않은 경우 자동으로 매핑된다.
   - 한편 @Column 어노테이션은 자바 객체가 생성될 때는 값을 검증하지 않지만, sql을 생성하는 hbm2ddl 에서 이 메타테이터를 생성하는데 그 때 값이 맞지 않으면 에러를 일으킨다.
* @Temporal(TemporalType.TIMESTAMP): java.util.Date 또는 java.util.Calendar 타입에는 필수적으로 들어가야 한다. JDBC 가 이해할 수 있는 sql의 java.sql.Timestamp 타입과 매핑하기 위함이다.
* 기본생성자: 하이버네이트는 기본생성자가 꼭 필요하다. DB에서 메시지 레코드를 가져올 때 Message 객체를 재구성하는데, 이때 기본생성자를 호출하기 때문이다!


### 3. 스프링 빈에서 datasource를 주입받아 LocalSessionFactoryBean 를 사용하겠다고 명시하기. 이 때 VO 객체를 스캔할 수 있도록 패키지 명시하기
```java
@Configuration
@ComponentScan("app.messages")
public class AppConfig {
    private DataSource dataSource;

    public AppConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    ...

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // Entity 클래스가 있는 패키지를 검색할 수 있도록 경로를 지정해주어야 한다.
        sessionFactoryBean.setPackagesToScan("app.messages");
        return sessionFactoryBean;
    }

}

```
* @Configuration: 이 있는 설정 파일이다. 여기서 해줘야하는 설정은 다음과 같다.
    1. 스프링부트가 관리하고 있는 datasource 주입받기(생성자로)
    2. pom.xml에서 추가해준 의존성(spring-orm, hibernate-core)에서 세션을 생성할 수 있는 LocalSessionFactoryBean() 객체를 생성하여 리턴해준다.
* setDataSource(dataSource): 커넥션 풀을 관리하는 dataSource를 팩토리에 set 해준다.
* sessionFactoryBean.setPackagesToScan("app.messages"): 엔티티 클래스가 존재하는 패키지 위치를 명시한다.

### 4. LocalSessionFactoryBean 에서 세션을 의존성으로 주입받아 사용하기
```java
@Component
public class MessageRepository {
    private final static Log logger = LogFactory.getLog(MessageRepository.class);

    private SessionFactory sessionFactory;

    public MessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Message saveMessage(Message message) {
        Session session = sessionFactory.openSession();
        session.save(message);
        return message;
    }

}
```
* @Component: 의존성을 주입받을 수 있도록 컴포넌트를 스캔해준다.
* 마찬가지로 세션 관련한 의존성을 주입받을 수 있도록 private SessionFactory sessionFactory; 필드에 대한 생성자를 만들어준다. 또는 생성자 대신 @Autowired 같은 속성을 사용해도 된다.
   - 이 때 SessionFactory와 NamedParameterJdbcTemplate 했을 때의 차이를 비교해보기 바란다.
* saveMessage 메소드: AppConfig 에서 생성된 LocalSessionFactoryBean 객체로 부터 session을 주입받는다.
   - LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
   - sessionFactory = sessionFactoryBean.getObject().openSession(); 코드를 스프링부트 단에서 해결해준 것이다.



