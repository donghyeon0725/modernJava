package app.messages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageRepository {
    private final static Log log = LogFactory.getLog(MessageRepository.class);

    public void saveMessage(Message message) {
        //데이터베이스에 메시지를 저장한다.
        //log4j 는 자동으로 resources 에서 해당 이름을 가진 파일을 찾는다.
        log.info("Saved message: " + message.getText());
    }

}
