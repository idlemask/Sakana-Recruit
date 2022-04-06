package name.sakanacatcher.recruit.gateway;

import name.sakanacatcher.recruit.gateway.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Register {
    @Value("${spring.application.name}")
    public String appName;

    @Resource
    AuthorizationService authorizationService;

    public void registerPersistent() throws Exception{
        authorizationService.generateServiceToken(appName);
    }
}
