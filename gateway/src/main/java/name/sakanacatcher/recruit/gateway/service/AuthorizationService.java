package name.sakanacatcher.recruit.gateway.service;

import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "authorization-server")
public interface AuthorizationService {
    @GetMapping(value = "/auth/authorization/generate")
    Result<String> generateToken(@RequestParam("username") String username);

    @GetMapping(value = "/auth/authorization/clear")
    Result clearToken(@RequestParam("username") String username, @RequestParam("device") String device);

    @GetMapping(value = "/auth/authorization/refresh")
    Result<String> refreshToken(@RequestParam("tokenStr") String tokenStr, @RequestParam("device") String device);

    @GetMapping(value = "/auth/authorization/service/generate")
    Result<String> generateServiceToken(@RequestParam("serviceName") String serviceName);

    @GetMapping(value = "/auth/authorization/service/get")
    Result<String> getServiceToken(@RequestParam("serviceName") String serviceName);
}
