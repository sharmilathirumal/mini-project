package com.example.spring_thymeleaf;

import com.example.spring_thymeleaf.dto.TeamDTO;
import com.example.spring_thymeleaf.entity.Team;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringThymeleafApplication {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringThymeleafApplication.class, args);
	}

}
