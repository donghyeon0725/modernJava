```java
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
```
equals

* equals 는 기본적으로 주소 값을 먼저 비교해서 같으면 true를 리턴한다.
* 클래스가 다르거나, 값이 비어있으면 false를 리턴한다.
* 받은 값의 id를 비교해서 같으면 같은 객체로 리턴한다. 이때 Objects.equals() 메소드를 이용한다.

hashCode

* 해시코드 값은 같은 객체일 경우 무조건 같아야 한다는 규약이 있기 때문에 키 값을 통해서 같은 객체인지 판별하도록 한다.
* Objects.hash(); 를 이용한다. 
* 키 값이 두개인 경우 인자를 두개 주면 된다.