package com.winter.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
	@SpringBootApplication 애너테이션이 적용된 클래스에 있는 main 메서드가 자동으로 실행됩니다.
	이 main 메서드는 스프링 부트 애플리케이션을 실행하는 역할을 하며, 웹 애플리케이션을 시작하는데 사용됩니다.
*/
// @SpringBootApplication(exclude = SecurityAutoConfiguration.class) // Spring Security 비활성화(properties 파일 대신 애너테이션 사용 방법)
@SpringBootApplication
@EnableScheduling // Scheduler 활성화
public class Gdj68BootApplication {
	/*
 		main 메서드는 Spring Boot 애플리케이션을 시작하는 진입점이며,
 		이 main 메서드를 정의하는 클래스는 기본적으로 최상위 패키지(Base 패키지)에 위치해야 합니다.(그래야 Spring Boot가 정상적으로 스캔할 수 있음)
 		Spring Boot 애플리케이션을 생성할 때 Spring Initializr 또는 IDE (통합 개발 환경)를 사용하는 경우,
 		보통 다음과 같이 main 메서드를 가진 클래스가 자동으로 생성됩니다.
 		※클래스 이름은 무작위로 지어도 상관없음
	*/
	public static void main(String[] args) {
		/*
			SpringApplication.run() 메서드는 스프링 부트 애플리케이션을 초기화하고 실행하는 역할을 합니다.
			main 메서드를 실행하면 내장 웹 서버(예: Tomcat 또는 Jetty)가 시작되고, 스프링 애플리케이션 컨텍스트가 로드되며, 스프링 빈(Bean)들이 초기화됩니다.
			이렇게 함으로써 웹 애플리케이션을 실행하게 됩니다.
			따라서 @SpringBootApplication 애너테이션이 적용된 클래스의 main 메서드는 스프링 부트 애플리케이션을 시작하는 진입점(entry point)이며,
			개발자는 이 메서드를 실행하여 웹 애플리케이션을 실행시킬 수 있습니다.
		*/
		SpringApplication.run(Gdj68BootApplication.class, args);
	}
}