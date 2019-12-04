package xmu.oomall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @author liznsalt
 */
@SpringBootApplication
@EnableRedisRepositories
@MapperScan("xmu.oomall.mapper")
public class GoodsInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsInfoApplication.class, args);
    }
}
