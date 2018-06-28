package org.totem.iot;

//import com.btmatthews.springboot.memcached.EnableMemcached;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.boot.CommandLineRunner;

//@EnableMemcached
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 7200)
@SpringBootApplication(scanBasePackages={"org.totem"})
@MapperScan(basePackages={"org.totem.*.dao"})
public class MainApplication  implements CommandLineRunner{
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
    }
    @Override
    public void run(String... strings) throws Exception {
        //todo
        }

}