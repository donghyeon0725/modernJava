package app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityChecker {
    @Around("execution(* app.messages..*.welcome(..))")
    public Object checkSecurity(ProceedingJoinPoint joinPoint) {
        System.out.println("checkSecurity");
        return null;
    }
}
