package app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 런타임시에 실행 가능한 어노테이션
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE/*, ElementType.FIELD*/}) // 여러가지 항목을 주고 싶을 때 괄호를 통해 넘길 수 있다.
public @interface Validation {
}
