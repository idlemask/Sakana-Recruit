package name.sakanacatcher.recruit.collector.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CollectorConsumer {
    public static void main(String[] args) {
        SpringApplication.run(CollectorConsumer.class, args);
    }
}
