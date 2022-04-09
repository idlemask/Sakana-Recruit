package name.sakanacatcher.recruit.auth.authorization;

import name.sakanacatcher.recruit.auth.authorization.service.ServiceRegisterService;
import name.sakanacatcher.recruit.common.core.CoreApplication;
import name.sakanacatcher.recruit.common.core.util.RegisterUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AuthorizationServerApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(AuthorizationServerApplication.class, args);
        RegisterUtil registerUtil = new RegisterUtil(context);
        registerUtil.run(args);
        ServiceRegisterService serviceRegisterService = context.getBean(ServiceRegisterService.class);
        serviceRegisterService.registerResource(registerUtil.form);

    }
}
