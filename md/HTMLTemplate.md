HTML 에서 메일을 보낸다면,
템플릿을 통해서 특정 부분의 변수값만 변경해서 보내는 방법이 간편하다.  
이때 JSP와 같이 HTML의 변수 부분을 변경해주는 것을 템플릿엔진이라고 한다.  
여기서는 템플릿 엔진을 JAVA 단에서 불러와서 처리하는 방법을 다룰 것이다.

이를 사용하는 방법은 아래와 같다.  
MUSTACHE를 사용하는 방법을 소개한다.  
이 때 추가적인 팁으로 @Async 을 사용하면 자동으로 서버는 스레드를 사용함으로써 클라이언트가 응답처리를 바로 받아볼 수 있도록 할 수 있다.



1. pom.xml 에 의존성 추가(템플릿 엔진)
2. 패키지에 템플릿 html 생성
3. 템플릿엔진을 통한 랜더링

* 템플릿 엔진 추가
```html
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mustache</artifactId>
</dependency>
```

* 패키지에 템플릿 html 생성
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
{{message}}
</body>
</html>
```
이 때 파일이름은 확장자를 포함하여 mail.mustache 이렇게 된다.

* 랜더링
```java
//머스타치 파일로더를 통해 머스타치 파일을 불러온다. 기본 경로는 resources 이다.
MustacheResourceTemplateLoader mustacheResourceTemplateLoader = new MustacheResourceTemplateLoader();
Reader html = mustacheResourceTemplateLoader.getTemplate("templates/mail.mustache");

//문자열을 대신 넣어도 된다.
//String html = "<html><body><p>{{message}}</p><body></html>";
Template template = Mustache.compiler().compile(html);
Map<String, String> model = new HashMap<>();
model.put("message", "메시지입니다");
System.out.println(template.execute(model));
```
 

