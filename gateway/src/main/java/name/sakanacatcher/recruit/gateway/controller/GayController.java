package name.sakanacatcher.recruit.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "gateway")
public class GayController {
    @GetMapping(value = "/get")
    public String getGate(){
        return "Gate";
    }
}

