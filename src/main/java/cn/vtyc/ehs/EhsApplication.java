package cn.vtyc.ehs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.vtyc.ehs.dao")
@EnableScheduling
public class EhsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EhsApplication.class, args);
	}
}
