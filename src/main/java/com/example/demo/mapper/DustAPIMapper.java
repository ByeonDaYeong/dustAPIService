package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.DustAPIDto;

@Mapper
public interface DustAPIMapper {
	int selectTest();
	
	void insertDust(DustAPIDto dto);					//사용자가 조회한 구의 데이터 저장
	
	List<DustAPIDto> selectAllDust();
	List<DustAPIDto> selectChartData(String stationName);

	void insertDustScheduler(List<DustAPIDto> dtoList);	//스케줄러로 요청 및 저장
}
