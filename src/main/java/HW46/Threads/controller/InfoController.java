package HW46.Threads.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/port")
    public int getServerPort() {
        return serverPort;
    }
}
