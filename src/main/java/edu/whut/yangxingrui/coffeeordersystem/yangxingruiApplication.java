package edu.whut.yangxingrui.coffeeordersystem;

import edu.whut.yangxingrui.coffeeordersystem.mapper.CoffeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("edu.whut.yangxingrui.coffeeordersystem.mapper")
public class yangxingruiApplication implements ApplicationRunner {


	@Autowired
	private CoffeeMapper coffeeMapper;


	public static void main(String[] args) {
		SpringApplication.run(yangxingruiApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

	}



}

