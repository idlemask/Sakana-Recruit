package name.sakanacatcher.recruit.gateway.service;

import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Component
//@FeignClient(value = "authorization-server")
//@RequestMapping("/auth/authorization/service/")
public interface ServiceRegister {
    @GetMapping(value = "generate")
    Result<String> generateToken(@RequestParam("username") String serviceName);

    @GetMapping(value = "get")
    Result<String> getToken(@RequestParam("username") String serviceName);
}
