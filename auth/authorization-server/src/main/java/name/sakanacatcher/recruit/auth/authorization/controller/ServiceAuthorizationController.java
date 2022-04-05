package name.sakanacatcher.recruit.auth.authorization.controller;

import name.sakanacatcher.recruit.auth.authorization.entity.ServiceToken;
import name.sakanacatcher.recruit.auth.authorization.repository.ServiceTokenRepository;
import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/authorization/service/")
public class ServiceAuthorizationController {
    @Autowired
    ServiceTokenRepository serviceTokenRepository;

    @Transactional
    @GetMapping(value = "generate")
    public Result<String> generateToken(@RequestParam("serviceName") String serviceName) {
        ServiceToken serviceToken = serviceTokenRepository.findByName(serviceName);
        if (serviceToken != null) {
            serviceToken.setToken(serviceName);
        } else {
            serviceToken = new ServiceToken(serviceName);
        }
        serviceTokenRepository.save(serviceToken);
        return Result.success(serviceToken.getToken());
    }

    @GetMapping(value = "get")
    public Result<String> getToken(@RequestParam("serviceName") String serviceName) {
        ServiceToken serviceToken = serviceTokenRepository.findByName(serviceName);
        return Result.success(serviceToken.getToken());
    }
}
