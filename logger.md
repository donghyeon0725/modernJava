스프링부트의 logback
-
스프링 부트는 로거가 자동으로 내장되어 있다.

```html
logging.level.app.filter.AuditingFilter=DEBUG
```

그래서 resources 경로에 application.properties 를 생성한 뒤에 위와 같은 코드 한줄만 추가해주면 사용이 가능하다.

```java
private final static Log logger = LogFactory.getLog(MessageRepository.class);
```

위와 같은 방법으로 추가해서 사용하면 된다.  
아래 링크를 확인하자

[갓대희의 작은 공간: 로거](https://goddaehee.tistory.com/206 "갓대희의 작은 공간: 로거")