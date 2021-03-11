ResponseEntity
-
* ResponseEntity 는 응답 상태의 본문(body) 해더를 설정할 수 있게 허용한다.
* status(500).build(); 를 통해서 응답 코드를 설정할 수 있고 
* 정상일 때는 ok를 통해서 반환할 수 있다. 이때 몸체는 인자로 전달해서 넘기면 된다.
* 이 응답은 @ResponseBody 어노테이션을 통해 HTTP 형식으로 변환해서 응답한다.
