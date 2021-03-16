트랜잭션
-
* 스프링 트랜잭션 기능은 전역으로 동작하는 JTA(Java Transaction API) 뿐만 아니라 JDBC API, 하이버네이트 프랜잭션 API, JPA 트랜잭션 API를 포함한 다양한 트랙잭션 API에 대한 추상화를 제공  
* 전역 트랜잭션은 일반적으로 관계형 DB나 JMS(메시지 저장소 역할을 함)와 같은 다양한 트랜잭션 자원과 함께 동작할 수 있다.
* JTA(Java Transaction API)로 전역 트랜잭션을 관리하는 것은 애플리케이션 서버다.  
* 반면 JDBC 와 관련된 트랜잭션인 로컬 트랜잭션은 다양한 자원들과 함께 동작할 수는 없다.

PlatformTransactionManager
-
트랜잭션을 관리해주는 자바 인터페이스 중 하나

* 하이버네이트의 PlatformTransactionManager 구현체는 HibernateTransactionManager
* JDBC의 구현체는 DataSourceTransactionManager
* JPA의 구현체는 JpaTransactionManager
* JTA의 구현체는 JtaTransactionManager다. 
* 그리고 JMS 구현체는 JmsTransactionManager다.
* 다른 API는 PlatformTransactionManager 에 상응하는 구현체를 생성해야한다.

선언적 트랜잭션 관리
-
1. @Configure 클래스에 @EnableTransactionManagement 어노테이션 추가
2. 트랜젝션 매니저를 빈으로 등록
    ```java
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        // PlatformTransactionManager 구현체를 생성해서 sessionFactory에서 생성하는 session을 set 해줬다.
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
    ```
    * 이 때 중요한 것은 sessionFactory() 메소드에서 DB 커넥션을 관리하는 session을 가져와야 한다.
3. @Transactional 어노테이션을 비지니스 로직을 처리하는 Service에 추가
   * 주의할 점은 Transactional 어노테이션이 새로운 세션을 여는 것을 시도한다. 그렇기 때문에 save 할 때 새로운 세션이 아닌 Transactional이 열어놓은 세션으로 주입받아야만 트랜젝션 처리가 가능해진다.
4. 주의할점은! save 하는 Repository 에서 새로운 세션을 오픈하는 것이 아닌, 기존의 세션을 가져와야 한다.
   1. Session session = sessionFactory.openSession(); => 새로운 세션을 열음
   2. Session session = sessionFactory.getCurrentSession(); => 기존의 세션을 가져옴
        * => 이렇게 트랜젝션으로 묶여야할 곳은 트랜잭션 어드바이저와 공유하는 컨텍스트에서 세션을 획득해와야 한다.
5. @Transactional(noRollbackFor = {UnsupportedOperationException.class}) 처럼 특정 에러가 난 경우에만 롤백을 하지 않도록 설정할 수도 있다.