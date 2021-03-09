package app.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

//GenericFilterBean 을 확장하여야 함
public class AuditingFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        long start = new Date().getTime();
        // 체인에 필터가 존재하면 추가 필터를 호출 할 수 있도록 함
        // doFilter 를 호출하지 않으면 클라이언트에 응답을 보낼 수 없다. 호출했지만 필터를 등록하지 않았다면 마찬가지로 응답을 보낼 수 없다.
        // 또한 doFilter 를 호출한 이후 추가 작업을 진행할 수 있다.
        chain.doFilter(req, res);
        long elapsed = new Date().getTime() - start;

        HttpServletRequest request = (HttpServletRequest) req;
        logger.debug("Request[url=" + request.getRequestURI() +
                ", method=" + ((HttpServletRequest) req).getMethod() +
                " completed in " + elapsed + " ms]");
    }
}
