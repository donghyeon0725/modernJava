구조
-

1. 컨트롤러
2. 데이터
3. VO 객체
4. Repository
5. Service
6. AppConfig
7. Application


컨트롤러
-


1. Controller 요청 받음 (Service 를 주입 받음)
2. @RequestBody MessageData data 데이터를 json으로 받음
3. 받은 데이터를 Service에 넘겨주면서 save
4. save 하면서 받은 데이터를 ResponseEntity 객체에 담아 return 


데이터
-
* 단순 데이터를 받는 객체


VO 객체
-
*. 데이터를 받아 DB에 저장될 객체 형태로 @Table(name="messages") 어노테이션 등등으로 관리되고 있음

Repository
-
* 세션을 가져와서 받은 vo 객체를 DB에 insert 해줌

Service
-
* Repository 객체에서 원하는 메소드 들을 불러서 비지니스 로직을 구현하는 곳

AppConfig
-
* 설정들이 있을 곳 

Application
-
* 스프링 부트가 시작할 곳