package name.sakanacatcher.recruit.gateway;

import name.sakanacatcher.recruit.common.core.util.RegisterUtil;
import name.sakanacatcher.recruit.gateway.service.ServiceRegisterService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GateWayApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(GateWayApplication.class, args);
        RegisterUtil registerUtil = new RegisterUtil(context);
        registerUtil.run(args);
    }
}
