package app.messages;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/welcome")
    public ModelAndView welcome() {
        ModelAndView mv = new ModelAndView("welcome");
        mv.addObject("message", "Hello, Welcome to Spring Boot!");
        return mv;
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
