package com.skilkihodin.jhauzzer.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/backdoor")
public final class ServerDropApiController {

    private final ApplicationContext app;

    @Autowired
    public ServerDropApiController(ApplicationContext app) {
        this.app = app;
    }

    @PostMapping("/kill-in-{timeoutMs}")
    public String killServer(@PathVariable("timeoutMs") Long timeoutMs) {
        new Thread(
                () -> {
                    try {
                        Thread.sleep(timeoutMs);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("Oops! The server was shut down by a request from the side");
                    SpringApplication.exit(app);
                }
        ).start();

        return "Thank you for your request!\nServer will die in " + timeoutMs + " ms";
    }

}
