package app.messages;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Aspect
@Component
public class SecurityChecker {

    private static final Logger logger = LoggerFactory.getLogger(SecurityChecker.class);

    /**
     * 받은 파라미터를 변경해주는 메소드
     * */
    public Object[] setServletRequestArgs(ProceedingJoinPoint joinPoint, Map<String, Object> map) {
        Object[] result = joinPoint.getArgs();

        for (Object obj : result) {
            if (obj instanceof HttpServletRequest || obj instanceof MultipartHttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) obj;

                Iterator<String> keys = map.keySet().iterator();
                while( keys.hasNext() ){
                    String key = keys.next();
                    Object value = map.get(key);

                    request.setAttribute(key, value);
                }
            }
        }

        return result;
    }

    @Around("execution(* app.messages..*.welcome(..))")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("Around Start");

        Map<String, Object> map = new HashMap<>();
        map.put("aop", "AOP Around Come here");
        map.put("temp", "temp Around Come here");
        Object[] newArgs = setServletRequestArgs(joinPoint, map);


        Object response = null;
        try {
            //response = joinPoint.proceed(newArgs);
            response = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        logger.debug("Around End");
        return response;
    }

    /*
    Around Start
    welcome
    Around End
    welcome After
    welcome AfterReturning

    Around Start
    welcome
    Around End
    welcome After
    welcome AfterThrowing
    */
    @After("execution(* app.messages.MessageController.welcome(..))")
    public void After(JoinPoint joinPoint) {
        logger.debug(joinPoint.getSignature().getName() + " After");
        // ProceedingJoinPoint joinPoint 는 오직 Around 어노테이션에만 적용할 수 있다.
    }

    @AfterReturning("execution(* app.messages.MessageController.welcome(..))")
    public void AfterReturning(JoinPoint joinPoint) {
        logger.debug(joinPoint.getSignature().getName() + " AfterReturning");
        // ProceedingJoinPoint joinPoint 는 오직 Around 어노테이션에만 적용할 수 있다.

    }

    @AfterThrowing(value="execution(* app.messages.MessageController.welcome(..))", throwing = "e")
    public void AfterThrowing(JoinPoint joinPoint, Exception e) {
        logger.debug(joinPoint.getSignature().getName() + " AfterThrowing");
        logger.debug(e.getMessage());
        // ProceedingJoinPoint joinPoint 는 오직 Around 어노테이션에만 적용할 수 있다.

        /*
        if (true) {
            throw new RuntimeException("에러가 발생했습니다.");
        }
        를 사용하니 에러가 발생했다.
        이를 통해서 에러 발생시
        */

    }

    /*@Pointcut("@annotation(SecurityCheck)")
    public void checkMethodSecurity() {}

    @Around("checkMethodSecurity()")
    public Object checkSecurity (ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("Checking method security...");
        // TODO Implement security check logics here
        Object result = joinPoint.proceed();
        return result;
    }*/
}