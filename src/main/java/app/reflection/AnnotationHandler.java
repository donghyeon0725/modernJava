package app.reflection;

import app.annotation.NotNull;
import app.annotation.Repeat;
import app.annotation.Validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AnnotationHandler {
    /**
     * 이 메소드를 실행하면 아래와 같은 결과를 얻을 수 있습니다.
     * */
    public static void main(String[] args) throws IllegalAccessException {
        // 유저 '김' 생성
        UserVO userKim = new UserVO("kim", "김");

        // 유효성 검사, 반복 호출 진행
        AnnotationHandler handler = new AnnotationHandler();
        handler.validationCheck(userKim);
        handler.repeat(userKim);

        System.out.print("{아이디 : " + userKim.getId());
        System.out.print(", 이름 : " + userKim.getName());
        System.out.println(", 가진책 : " + userKim.getBook() + "권}");

        // 유저 '민' 생성
        UserVO userMin = new UserVO();
        userMin.setId("min");

        // 값 name이 비어 있음
        handler.validationCheck(userMin); // 에러

        System.out.print("{아이디 : " + userMin.getId());
        System.out.print(", 이름 : " + userMin.getName());
        System.out.println(", 가진책 : " + userMin.getBook() + "권}");

        /*
        = 실행결과
        ===================================

        {아이디 : kim, 이름 : 김, 가진책 : 2권}
        Exception in thread "main" java.lang.RuntimeException: 객체에 빈 값이 있습니다. 클래스 : app.reflection.UserVO, 필드 : name
	        at app.reflection.AnnotationHandler.validationCheck(AnnotationHandler.java:77)
	        at app.reflection.AnnotationHandler.main(AnnotationHandler.java:32)

        ===================================*/

    }

    /**
    * 값이 비어 있는지 확인
    * */
    public boolean isEmpty(Object obj) {
        if (obj instanceof String) return obj == null || "".equals(obj.toString().trim());
        else if (obj instanceof List) return obj == null || ((List<?>) obj).isEmpty();
        else if (obj instanceof Map) return obj == null || ((Map<?, ?>) obj).isEmpty();
        else if (obj instanceof Object[]) return obj == null || Array.getLength(obj) == 0;
        else return obj == null;
    }

    /**
    * 객체에 @NotNull 어노테이션이 붙은 객체에 빈 값이 있는지 확인
    * */
    public boolean validationCheck(Object instance) throws IllegalAccessException {
        Class <? extends Object> cls = instance.getClass();
        Annotation[] annotations = cls.getDeclaredAnnotations();

        // Validation 어노테이션이 없으면
        if (!cls.isAnnotationPresent(Validation.class)) throw new IllegalAccessException("유효성 검사를 진행 할 수 없습니다.");

        // getFields            인스턴스에서 public 필드만 가져옵니다.
        // getDeclaredFields    인스턴스의 모든 필드를 가져옵니다.
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            //접근 제어자 속성을 무시하고 접근이 가능
            field.setAccessible(true);

            // NotNull 필드
            if (field.isAnnotationPresent(NotNull.class)) {

                try {
                    // 인스턴스의 해당 필드값을 가져옴
                    Object value = field.get(instance);

                    if (isEmpty(value)) {
                        throw new RuntimeException("객체에 빈 값이 있습니다. 클래스 : " + cls.getName() + ", 필드 : " + field.getName());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * 객체에 @Repeat 어노테이션 count 속성 수 만큼 메소드 반복 호출
     * */
    public void repeat(Object instance) throws RuntimeException {
        Class <? extends Object> cls = instance.getClass();

        Arrays.stream(cls.getDeclaredMethods()).forEach(method -> {
            if (method.isAnnotationPresent(Repeat.class)) {
                method.setAccessible(true);

                // 메소드에 붙은 Repeat 어노테이션 객체를 가져온다.
                Repeat annotation = method.getAnnotation(Repeat.class);

                // 받은 반복회수 만큼 메소드 호출
                for (int i=0; i<annotation.count(); i++) {
                    try {
                        method.invoke(instance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException();
                    }
                }
            }
        });
    }

}
