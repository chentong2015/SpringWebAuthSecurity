package cross_access;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO. Access Control Allow Origin
// https://spring.io/guides/gs/rest-service-cors/
// Java服务端必须提供跨域认证的支持，否则JavaScript(Ajax)无法向其发送请求
@RestController
public class CrossOriginController {

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/status/test")
    public String test() {
        // return "<div>test</div>";
        return "{\"order_id\": \"test order id\"}";
    }

    // 允许指定的URL发送跨域请求, 接受来自Javascript Ajax的请求
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== get greeting ====");
        return new Greeting(10, "hello " + name);
    }

    class Greeting {
        private final long id;
        private final String content;

        public Greeting(long id, String content) {
            this.id = id;
            this.content = content;
        }
    }
}