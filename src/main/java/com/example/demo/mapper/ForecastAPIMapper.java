package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.ForecastAPIDto;

@Mapper
public interface ForecastAPIMapper {

	List<ForecastAPIDto> selectForecast();
	void insertForecast(List<ForecastAPIDto> dtoList);
}
