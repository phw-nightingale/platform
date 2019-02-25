package cn.giit.platform;

import cn.giit.platform.common.JsonResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }

    @GetMapping({"/", "/platform"})
    public JsonResult _home() {
        return JsonResult.success("Welcome to Platform Application.");
    }

    @GetMapping("/404")
    public JsonResult _404() {
        return JsonResult.error(404, "request not found");
    }

    @GetMapping("/403")
    public JsonResult _403() {
        return JsonResult.error(403, "unauthorized");
    }

    @GetMapping("/401")
    public JsonResult _401() {
        return JsonResult.error(401, "token invalid");
    }
}
