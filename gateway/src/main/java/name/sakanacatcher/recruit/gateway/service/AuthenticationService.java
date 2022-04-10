package name.sakanacatcher.recruit.gateway.service;

import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Component
@FeignClient(value = "authentication-server")
public interface AuthenticationService {
    @GetMapping("/auth/authentication/resource/ignore/check/url")
    Result<Boolean> isIgnore(@RequestParam("url") String url);

    @PostMapping("/auth/authentication/resource/register")
    Result<String> registerResource(@RequestBody Map<String,Object> formData);

    @PostMapping("/auth/authentication/resource/register/before")
    Result<String> beforeRegisterService(@RequestBody Map<String,Object> formData);
}
