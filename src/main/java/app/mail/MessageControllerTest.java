package app.mail;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

class MessageControllerTest {
    public static void main(String[] args) {
        try {
            MessageControllerTest controller = new MessageControllerTest();
            /* 파일을 불러오는 경우 */
            controller.FileLoader();
            /* 문자열을 랜더링 하는 경우 */
            controller.String();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // https://github.com/samskivert/jmustache
    void String() {
        String html = "<html><body><p>{{message}}</p><body></html>";

        Template template = Mustache.compiler().compile(html);
        Map<String, String> model = new HashMap<>();
        model.put("message", "담겨진 모델입니다");
        System.out.println(template.execute(model));
    }

    void FileLoader() throws Exception {
        MustacheResourceTemplateLoader mustacheResourceTemplateLoader = new MustacheResourceTemplateLoader();
        Reader reader = mustacheResourceTemplateLoader.getTemplate("templates/mail.mustache");
        Template template = Mustache.compiler().compile(reader);

        Map<String, String> model = new HashMap<>();
        model.put("message", "메시지입니다");
        System.out.println(template.execute(model));
    }
}
