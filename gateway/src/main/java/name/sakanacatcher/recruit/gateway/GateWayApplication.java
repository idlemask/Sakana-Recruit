package name.sakanacatcher.recruit.gateway;

import name.sakanacatcher.recruit.gateway.service.AuthorizationService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class GateWayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GateWayApplication.class, args);
        AuthorizationService authorizationService = (AuthorizationService) context.getBean(AuthorizationService.class);
        try {
            authorizationService.generateServiceToken("gateway");
        } catch (Exception e) {
            System.out.println("注册失败:" + e);
            SpringApplication.exit(context);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}
