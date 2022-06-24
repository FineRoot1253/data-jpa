package com.jungeunhong.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	/**
	 * auditor를 제공하는 빈, 보통 유저의 ID를 제공해야한다.
	 * SpringDataJpa의 entity 어노테이션중 CreateBy, UpdatedBy등을 채워야하는 경우가 올때,
	 * 이 프로바이더에서 가져와 채워 넣는다.
	 * 예시 1) SecurityContext의 세션에 있는 유저의 ID
	 * 예시 2) HttpSession에 있는 유저의 ID
	 * @return
	 */
	@Bean
	public AuditorAware<String> auditorAwareProvider(){
		return ()-> Optional.of(UUID.randomUUID().toString());
	}

}
