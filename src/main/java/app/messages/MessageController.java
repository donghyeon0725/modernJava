package app.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @SecurityCheck
    @GetMapping("/welcome")
    public String welcome(HttpServletRequest req) {
        req.setAttribute("message", "Hello, Welcome to Spring Boot!");
        logger.debug("welcome");

        if (false) {
            throw new RuntimeException("에러가 발생했습니다.");
        }
        return "welcome";
    }


    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Message> saveMessage(@RequestBody MessageData data) {

        //HTTP 요청 몸체를 자바 객체로 변환
        Message saved = messageService.save(data.getText());
        if (saved == null) {
            return ResponseEntity.status(500).build();
        }
        // 정상인 응답 상태를 반환한다.
        return ResponseEntity.ok(saved);
    }




}
