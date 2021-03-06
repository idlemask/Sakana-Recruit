package name.sakanacatcher.recruit.auth.authentication.server;

import name.sakanacatcher.recruit.auth.authentication.server.controller.ResourceController;
import name.sakanacatcher.recruit.common.core.entity.vo.Result;
import name.sakanacatcher.recruit.common.core.util.RegisterUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AuthenticationApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(AuthenticationApplication.class, args);
        RegisterUtil registerUtil = new RegisterUtil(context);
        registerUtil.run(args);
        ResourceController serviceRegisterService = context.getBean(ResourceController.class);
        Result<Boolean> result = serviceRegisterService.registerResource(registerUtil.form);
    }
}
