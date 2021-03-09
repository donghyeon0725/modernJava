package app.messages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/messages")
public class MessageController {
    @GetMapping("/welcome")
    @ResponseBody // 리턴 타입이 경로가 아닌, Stream을 타고 나갈 수 있도록 설정함. 원래는 HttpServletResponse 객체를 통해 얻은 스트림으로 해야하는 동작을 프레임워크단에서 처리해줌
    public String welcome() {
        return "<strong>Hello, Welcome to Spring Boot!</strong>";
    }

}
