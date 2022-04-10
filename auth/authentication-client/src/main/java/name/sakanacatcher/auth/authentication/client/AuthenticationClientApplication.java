package name.sakanacatcher.auth.authentication.client;

import name.sakanacatcher.recruit.common.core.service.ServiceRegisterService;
import name.sakanacatcher.recruit.common.core.util.RegisterUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class AuthenticationClientApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(AuthenticationClientApplication.class, args);
        RegisterUtil registerUtil = new RegisterUtil(context);
        registerUtil.run(args);
        ServiceRegisterService serviceRegisterService = context.getBean(ServiceRegisterService.class);
        serviceRegisterService.registerResource(registerUtil.form);
    }
}