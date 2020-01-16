package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddAPIDto;
import com.example.demo.dto.ForecastAPIDto;
import com.example.demo.mapper.ForecastAPIMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class ForecastAPIServiceImpl {

	@Autowired
	private ForecastAPIMapper forecastApiMapper;

	public List<ForecastAPIDto> selectForecast() {
		List<ForecastAPIDto> dtoList = new ArrayList<>();
		dtoList = forecastApiMapper.selectForecast();
		return dtoList;
	}

	/**
	 * 스케쥴링에 의해 실행되는 메소드 매일 5시, 11시, 17시, 23시에 호출됨 1. 예보 open api 호출 2. response
	 * json data -> ForecastAPIDto Mapping 3. Call Mapper
	 * 
	 * @throws IOException
	 */
	public void insertForecast() throws IOException {
		System.out.println("insertForecast() 호출!!!");
		String xmlStr = callForecastAPI();
		List<ForecastAPIDto> dtoList = getDtoListFromXml(xmlStr);
		System.out.println(dtoList);
		forecastApiMapper.insertForecast(dtoList);
	}

	/**
	 * Call Forecast API return: xml Response Data formed String
	 * 
	 * @throws IOException
	 */
	public String callForecastAPI() throws IOException {	
		StringBuilder urlBuilder = new StringBuilder("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMinuDustFrcstDspth"); /*URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=mWOUP6hFibrsdKm56wULHkl93YWqbqfALbjYOD9XH%2F1ASgmGqBlXVo5YZIpfA5P5DgSlFTaggM2zrYBUWiHQug%3D%3D"); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수 (조회 날짜로 검색 시 사용 안함)*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호(조회 날짜로 검색 시 사용 안함)*/
        urlBuilder.append("&" + URLEncoder.encode("searchDate","UTF-8") + "=" + URLEncoder.encode(getDateForm2(), "UTF-8")); /*통보시간 검색 (조회 날짜 입력 없을 경우 한달동안 예보통보 발령 날짜의 리스트 정보를 확인)*/
        urlBuilder.append("&" + URLEncoder.encode("InformCode","UTF-8") + "=" + URLEncoder.encode("PM10", "UTF-8")); /*통보코드검색 (PM10 : 미세먼지 PM25 : 초미세먼지 O3 : 오존)*/

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String result = sb.toString().replaceAll("&#039;", ""); 	//홑따옴표 치환 
		System.out.println(result);

		return result;
	}

	/**
	 * parsing xml String to JsonArr return: address List Formed JsonArray
	 */
	public List<ForecastAPIDto> getDtoListFromXml(String xmlStr) {
		JsonParser parser = new JsonParser();
		JSONObject jsonObj = new JSONObject();
		jsonObj = XML.toJSONObject(xmlStr.toString());

		JsonElement element = parser.parse(jsonObj.toString());
		element = element.getAsJsonObject().get("response").getAsJsonObject().get("body").getAsJsonObject().get("items")
				.getAsJsonObject().get("item");
		// element = element.getAsJsonObject().get("NewAddressListResponse");

		return getDtoListFromJsonElement(element);
	}

	/**
	 * Get ForecastAPIDto List from JsonElement return: ForecastAPIDto Array
	 */
	public List<ForecastAPIDto> getDtoListFromJsonElement(JsonElement element) {
		JsonArray jsonArr = new JsonArray();
		Gson gson = new Gson();
		List<ForecastAPIDto> dtoList = new ArrayList<ForecastAPIDto>();

		
		 if (element.isJsonArray()) {
			 //jsonArray로 변환 가능(json list 가 2개 이상) 
			 jsonArr = element.getAsJsonArray(); 
			 //mapping jsonArr to Dto 
			 for (JsonElement item : jsonArr) {
				 String curDataTime = item.getAsJsonObject().get("dataTime").getAsString();
				 String curTime = getDateForm1(); //"2020-01-15 시 발표"; //임시데이터 	//getDateForm1();
				 if (curDataTime.equals(curTime)) //현재 일시에 발표된 예보만 가지고 옴 
					 dtoList.add(gson.fromJson(item, ForecastAPIDto.class)); 
			 } 
		 } else {
			 //jsonArray로 변환 불가(json list 가 1개 ) 
			 JsonElement castjson = (JsonElement) element ;
			 dtoList.add(gson.fromJson(castjson, ForecastAPIDto.class)); 
		 }
		 
		 return dtoList;
	}
	
	
	/**
	 * 현재 날짜 및 시간 가져옴 
	 */
	// Form : 2020-01-14 17시 발표
	public String getDateForm1() {
		SimpleDateFormat dateForm = new SimpleDateFormat ( "yyyy-MM-dd HH시 발표");
		Date time = new Date();
		String timeStr = dateForm.format(time);
		return timeStr;
	}
	// Form : 2020-01-14
	public String getDateForm2() {
		SimpleDateFormat dateForm = new SimpleDateFormat ( "yyyy-MM-dd");
		Date time = new Date();
		String timeStr = dateForm.format(time);
		return timeStr;
	}
}
