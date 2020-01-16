package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.AddAPIDto;
import com.example.demo.dto.DustAPIDto;
import com.example.demo.dto.ForecastAPIDto;
import com.example.demo.service.AddAPIServiceImpl;
import com.example.demo.service.DustAPIServiceImpl;
import com.example.demo.service.ForecastAPIServiceImpl;

@Controller
public class DustAPIController {
	
	@Autowired 
	public DustAPIServiceImpl dustAPIService;
	@Autowired 
	private AddAPIServiceImpl addAPIService;
	@Autowired
	private ForecastAPIServiceImpl forecastAPIService;
	
	@RequestMapping({"/{schAddr}", "/"})
	@ResponseBody
	public ModelAndView selectAll(@PathVariable(required=false) String schAddr) throws IOException, ParseException {
		System.out.println("Dust Controller Area!");
		
		ModelAndView mv = new ModelAndView("/DustAPI/dustMain");
		DustAPIDto dto = new DustAPIDto();
		
		if (schAddr != null) { //case in "/{schAddr}" path
			dto = dustAPIService.selectAll(schAddr);
			mv.addObject("dto", dto);
		}
		System.out.println(dto);
		return mv;
	}
	
	
	/**Main Page 내 주소 검색창 진입 시 Pop up 창
	 * @return View Name (addrSearch.html)
	 */
	@RequestMapping("/popUpAddr/")
	public String openAddr() {
		System.out.println("Addr POPUP Controller Area!!!");
		return "DustAPI/addrSearch";
	}
	
	
	/** 주소 검색 창 내에서 주소 OPEN API 요청 및 응답 받음
	 * @param addrName(사용자 input Value)
	 * @return 주소 DTO LIST
	 * @throws IOException
	 */
	@RequestMapping("/addr/{addrName}/{dongOrRoad}")
	@ResponseBody
	public List<AddAPIDto> selectAddress(@PathVariable String addrName, @PathVariable String dongOrRoad) throws IOException {
		System.out.println("Addr Controller Area!!!");
		List<AddAPIDto> dtoList = addAPIService.selectAddr(addrName, dongOrRoad);
		System.out.println(dtoList);
		
		return dtoList;
	}
	
	
	/**
	 * 조회 이력 리스트 전체 조회
	 * @return ModelAndView
	 */
	@RequestMapping("/dustSelectAll")
	public ModelAndView selectAllDust() {
		System.out.println("Dust Select All Controller Area!!!");
		List<DustAPIDto> dtoList = dustAPIService.selectAllDust();
		ModelAndView mv = new ModelAndView("/DustAPI/dustSelectAll");
		mv.addObject("dtoList", dtoList);
		System.out.println(dtoList);
		return mv;
	}
	
	
	/**
	 * 대기질 예보 통보 API 호출, 조회, 저장 
	 * @return ModelAndView - Model: 예보 조회 리스트, View: 조회 페이지
	 */
	@RequestMapping("/forecast")
	public ModelAndView selectForecast() {
		System.out.println("Forecast Controller Area!!!");
		
		ModelAndView mv = new ModelAndView("/DustAPI/forecastSelect");
		List<ForecastAPIDto> dtoList = forecastAPIService.selectForecast();
		mv.addObject("dtoList", dtoList);
		System.out.println(dtoList);
		return mv;
	}
	
	
	/**
	 * Chart Data Select 
	 * @param 측정소명(ex.중구)
	 * @return DustAPIDto
	 */
	@RequestMapping("/dustChart/{stationName}")
	@ResponseBody
	public List<DustAPIDto> selectchartData(@PathVariable String stationName) {
		List<DustAPIDto> dtoList = dustAPIService.selectChartData(stationName);
		return dtoList;
	}
}
