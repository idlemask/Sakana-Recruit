package name.sakanacatcher.recruit.collector.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CollectorProvider {
    public static void main(String[] args) {
        SpringApplication.run(CollectorProvider.class, args);
    }
}