필터
-
* 필터는 자바 EE 의 또 다른 좋은 기술
* 이 필터는 디자인 패턴인 **책임 연쇄 패턴(Chain of Responsibility)을 구현한 것.
* 서블릿에 도달하기 전에 작동한다.
* 필터를 만드려면 javax.serlvet.Filter 인터페이스를 구현하여야 한다.

```java
public class AuditingFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
            throws IOException, ServletException {
        long start = new Date().getTime();
        chain.doFilter(req, res);
        long elapsed = new Date().getTime() - start;
        
        HttpServletRequest request = (HttpServletRequest) req;
        logger.debug("Request[url=" + request.getRequestURI() + 
                ", method=" + ((HttpServletRequest) req).getMethod() + 
                " completed in " + elapsed + " ms");
    }
}
```
* GenericFilterBean 을 상속받아야 함
* chain.doFilter(req, res)
    - 체인에 필터가 존재하면 추가 필터를 호출 할 수 있도록 함. 이 후 서블릿을 호출한다.
    - doFilter 를 호출하지 않으면 클라이언트에 응답을 보낼 수 없다. 호출했지만 필터를 등록하지 않았다면 마찬가지로 응답을 보낼 수 없다.
    - 또한 doFilter 를 호출한 이후 추가 작업을 진행할 수 있다.
    

필터를 등록하는 두가지 방법
-
1. web.xml 파일에 <filter-mapping>을 추가해서 등록하는 방법 => 이는 웹 애플리케이션이 실행될 때 사용하는 방법이다.(war로 묶어서 사용할 때)
2. FilterRegistrationBean을 만들어 설정파일인 AppConfig 에 등록하는 방법 (톰캣을 임베디드로 실행할 때 적합)

필터 등록하기(AppConfig)
-
```java
@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean<AuditingFilter> auditingFilterFilterRegistrationBean() {
        FilterRegistrationBean<AuditingFilter> registration = new FilterRegistrationBean<>();

        AuditingFilter filter = new AuditingFilter();
        registration.setFilter(filter);
        registration.setOrder(Integer.MAX_VALUE);
        registration.setUrlPatterns(Arrays.asList("/messages/*"));
        return registration;
    }

}
```
* AuditingFilter 타입을 제네릭으로 하는 FilterRegistrationBean을 생성한다.
* 만든 필터를 생성 후 필터 등록 객체에 등록한다.
* @Configurable 과 @ComponentScan("app.messages") 어노테이션 대신 @Configuration 사용


필터의 디버그 결과를 로그로 관리하기
-
1. resources 아래 application.properties 파일 생성
2. logging.level.app.filter.AuditingFilter=DEBUG 추가 이 때 app.filter.AuditingFilter 는 경로와 필터의 이름이다.

* 여기서 GenericFilterBean 을 구현했으므로 자동으로 logger의 사용이 가능해진다.
---

필터의 구동 과정
-
    ① 서블릿 컨테이너는 웹 어플리케이션을 시작할 때 DD파일(web.xml)에 등록된 필터의 인스턴스를 생성하고 init()을 호출한다.  
    ② 클라이언트 요청이 들어오면 해당하는 필터의 doFilter()를 호출한다.  
    ③ doFilter()에서 작업을 실행하고 다음 필터의 doFilter()를 호출한다.  
    ④ 마지막 필터까지 ③을 반복한다.  
    ⑤ 마지막 필터는 서블릿의 service()를 호출한다.  
    ⑥ 서블릿의 service()가 끝나면 service()를 호출했던 이전 필터로 돌아간다.  
    ⑦ 반복해서 제일 처음 호출됐던 필터까지 돌아간다.  
    ⑧ 마지막으로 클라이언트에게 응답 결과를 보낸다.  

