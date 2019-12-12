package xmu.oomall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author liznsalt
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("xmu.oomall.mapper")
public class UserGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserGatewayApplication.class, args);
    }
}
