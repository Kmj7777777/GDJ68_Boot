package com.winter.app.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestScheduler {
	
	// @Scheduled(fixedRateString = "2000", initialDelay = 5000) // == fixedRate = 2000, initialDelay : 서버 초기화 완료 후 5초 후 실행
	// @Scheduled(fixedDelay = 1000)
	public void useFixedRate() throws Exception {
		
		log.info("==================== Fixed Rate ====================");
	}
	
	// @Scheduled(fixedDelay = 1000) // 1초마다
	public void useFixedDelay() {
		log.info("==================== Fixed Delay ====================");
	}
	
	// https://www.notion.so/Schedule-72201ad58c1e4196ad063f2428d69930 참고
	// @Scheduled(cron = "0 50 * * *  *") // 50분이 될 때마다
	// @Scheduled(cron = "*/10 * * * *  *") // 10초마다
	public void useCron() { // Cron : 가장 많이 사용됨
		log.info("==================== Cron ====================");
	}
}