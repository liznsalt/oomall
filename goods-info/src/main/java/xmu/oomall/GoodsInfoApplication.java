package xmu.oomall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author liznsalt
 */
@SpringBootApplication
@EnableCaching
@MapperScan("xmu.oomall.mapper")
public class GoodsInfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsInfoApplication.class, args);
    }
}
