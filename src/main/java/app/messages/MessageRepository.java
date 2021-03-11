package app.messages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MessageRepository {
    private final static Log logger = LogFactory.getLog(MessageRepository.class);

    // NamedParameterJdbcTemplate 대신 사용
    private SessionFactory sessionFactory;

    // sessionFactory 필드에 @Autowired 를 사용해도 된다.
    public MessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Message saveMessage(Message message) {
        /*
        AppConfig 에서 생성된 LocalSessionFactoryBean 객체로 부터 session을 주입받을 수 있다.
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactory = sessionFactoryBean.getObject().openSession();
        */
        Session session = sessionFactory.openSession();
        session.save(message);
        return message;
    }

}
