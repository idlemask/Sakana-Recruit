package name.sakanacatcher.recruit.common.core.config;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import name.sakanacatcher.recruit.common.core.util.ServiceTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class ConsumerFeignConfig implements RequestInterceptor {

    @Value("${sign:123}")
    String sign;

    @Value("${spring.application.name:456}")
    String appName;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("AUTHORIZATION",ServiceTokenUtil.generateToken(sign, appName));
    }
}

