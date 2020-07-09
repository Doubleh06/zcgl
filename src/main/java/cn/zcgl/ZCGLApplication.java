package cn.zcgl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.zcgl.dao.*")
@EnableScheduling
public class ZCGLApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZCGLApplication.class, args);
	}
}
