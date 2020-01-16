package com.example.demo.scheduler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.service.ForecastAPIServiceImpl;

@Component
public class ForecastScheduler {

	@Autowired
	private ForecastAPIServiceImpl forecastAPIService;
	
	@Scheduled(cron = "0 10 5,11,17,23 * * *")	//매일 5,11,17,23시 1분에 동작 (5,11,17,23시에 예보 발표됨)
	public void testSch() throws IOException {
		forecastAPIService.insertForecast();
		System.out.println("스케쥴러 작동! 수집 시간 : " + getDate());
	}
	//[cron 사용 법] 각 자리는 ( 초, 분, 시, 일, 월, 요일 ) 
	//"0 0 * * * *" = the top of every hour of every day.
	//"*/10 * * * * *" = 매 10초마다 실행한다.(=1분마다 실행)
	//"0 0 8-10 * * *" = 매일 8, 9, 10시에 실행한다
	//"0 0 6,19 * * *" = 매일 오전 6시, 오후 7시에 실행한다.
	//"0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
	//"0 0 9-17 * * MON-FRI" = 오전 9시부터 오후 5시까지 주중(월~금)에 실행한다.
	//"0 0 0 25 12 ?" = every Christmas Day at midnight
	
	public String getDate() {
		SimpleDateFormat dateForm = new SimpleDateFormat ( "yyyy-MM-dd HH시 mm분 ss초");
		Date time = new Date();
		String timeStr = dateForm.format(time);
		return timeStr;
	}
}
