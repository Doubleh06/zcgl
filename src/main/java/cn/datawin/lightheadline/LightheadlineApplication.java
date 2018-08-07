package cn.datawin.lightheadline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "cn.datawin.lightheadline.dao")
public class LightheadlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(LightheadlineApplication.class, args);
	}
}
