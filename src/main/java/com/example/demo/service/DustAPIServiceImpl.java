package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DustAPIDto;
import com.example.demo.mapper.DustAPIMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
@Service
public class DustAPIServiceImpl{

	@Autowired
	private DustAPIMapper dustApiMapper;
	
	public void testA() {
		System.out.println("Test Query 결과");
		System.out.println(dustApiMapper.selectTest());
	}
	
	
	/** 사용자가 요청한 경우
	 * 
	 * @param schAddr
	 * @param isAuto
	 * @return
	 * @throws IOException
	 */
	public DustAPIDto selectAll(String schAddr) throws IOException{
		String[] mappingAddr = mappingAddr(schAddr);
		
		DustAPIDto dto = new DustAPIDto();
		System.out.println("DUST Service Area");
		
		String responseData = getDustAPIData(mappingAddr[0]);
		JsonArray jsonArr = getJsonArr(responseData);
		dto = getDtoFromJsonArr(jsonArr, mappingAddr[1]);
		dto.setIsAuto("0");
		dustApiMapper.insertDust(dto);
        return dto;
	}
	
	
	/**
	 * 스케줄러가 요청한 경우
	 */
	public void selectAllScheduler() throws IOException {
		List<DustAPIDto> dtoList = new ArrayList<>();
		System.out.println("DUST Service Area(스케줄러)");
		
		String responseData = getDustAPIData("서울");
		JsonArray jsonArr = getJsonArr(responseData);
		dtoList = getDtoListFromJsonArr(jsonArr);
		dustApiMapper.insertDustScheduler(dtoList);
	}
	
	
	/** Call Dust API 
	 * return: 
	 * @throws IOException */ 
	public String getDustAPIData(String inputSido) throws IOException {
		// Call API
		StringBuilder urlBuilder = new StringBuilder("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=mWOUP6hFibrsdKm56wULHkl93YWqbqfALbjYOD9XH%2F1ASgmGqBlXVo5YZIpfA5P5DgSlFTaggM2zrYBUWiHQug%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode(inputSido, "UTF-8")); /*시도 이름 (서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.3", "UTF-8")); /*버전별 상세 결과 참고문서 참조*/
        urlBuilder.append("&" + URLEncoder.encode("_returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
        
	    URL url = new URL(urlBuilder.toString());
	    System.out.println("url 주소: " + url.toString() );
	    
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json");
	    System.out.println("Response code: " + conn.getResponseCode());	//ex: 200
	    
	    BufferedReader rd;
	    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
		return sb.toString();
	}
	
	
	/** String to JsonArray Data (Json Parsing)
	 * return: JsonArray 
	 * JSON Parse using GSON Library
       [GSON JsonElement Exemple] ref) http://www.gmsgondr.net/lecture/1/61 
       int total = element.getAsJsonObject().get("total").getAsInt();
       String date = element.getAsJsonObject().get("lastBuildDate").getAsString();
       JsonArray items = element.getAsJsonObject().get("items").getAsJsonArray(); 
     */
	 public JsonArray getJsonArr(String jsonStr) {
		 JsonParser parser = new JsonParser();
		 JsonElement element = parser.parse(jsonStr.toString());
		 JsonArray jsonArr = element.getAsJsonObject().get("list").getAsJsonArray();
		 System.out.println("JSON Response: " + jsonArr.toString());
		 return jsonArr;
	 }

	 
	 /** Get Dto from JsonArray
	  *	 return: Dto (from Json item) corrented input StationName */
	 public DustAPIDto getDtoFromJsonArr(JsonArray jsonArr, String inputStr) {
		 Gson gson = new Gson();
		 DustAPIDto dto = new DustAPIDto();
		 
		 for (JsonElement item : jsonArr) {
			 String stationName = item.getAsJsonObject().get("stationName").getAsString();
			 if (inputStr.equals(stationName)) 
				 dto = gson.fromJson(item, DustAPIDto.class);	//json -> Dto Mapping
		 }
		 return dto;
	 }
	 
	 
	 /** 전체 조회 함수
	  */
	 public List<DustAPIDto> selectAllDust() {
		 List<DustAPIDto> dtoList = new ArrayList<>();
		 dtoList = dustApiMapper.selectAllDust();
		 return dtoList;
	 }
	 
	 
	 /** 지번, 도로명 주소 -> 시도 & 측정소명으로 맵핑 
	  * 측정소명 맵핑 값 없는 경우: 시도 첫번째 값으로 대체
	  * 시도 맵핑 값 없는 경우: "조회하신 주소의 미세먼지 측정 값이 존재하지 않습니다"
	  */
	 public String[] mappingAddr(String schAddr) {
		 /*시도 이름 (서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
		 String[] inputValue = schAddr.split("&");
		 //String inputStation = "종로구"; // 임시 구 데이터
		
		 String inputSido = inputValue[0];
		 String inputStation = inputValue[1];
		 
		 String[] mappingValue = new String[2];
		 
		 switch(inputSido) {
		 	case "서울특별시": 	mappingValue[0] = "서울"; break;
		 	case "부산광역시":	mappingValue[0] = "부산"; break;
		 	case "대구광역시":	mappingValue[0] = "대구"; break;
		 	case "인천광역시":	mappingValue[0] = "인천"; break;
		 	case "광주광역시":	mappingValue[0] = "광주"; break;
		 	case "대전광역시":	mappingValue[0] = "대전"; break;
		 	case "울산광역시":	mappingValue[0] = "울산"; break;
		 	case "경기도":	mappingValue[0] = "경기"; break;
		 }
		 
		 mappingValue[1] = inputStation;
		 	
		 return mappingValue;
	 }
	 
	 
	 /** Chart Data Select
	  * Datatime, pm10value, pm25value 가지고 옴 
	  */
	 public List<DustAPIDto> selectChartData(String stationName) {
		 List<DustAPIDto> dtoList = new ArrayList<>();
		 dtoList = dustApiMapper.selectChartData(stationName);
		 return dtoList;
	 }
	 
	 
	 /** 스케줄러로 요청하는 경우 모든 구 단위 데이터 저장 
	  */
	 public List<DustAPIDto> getDtoListFromJsonArr(JsonArray jsonArr) {
		 Gson gson = new Gson();
		 List<DustAPIDto> dtoList = new ArrayList<>();
		 
		 for (JsonElement item : jsonArr) {
			 String stationName = item.getAsJsonObject().get("stationName").getAsString();
			 if (stationName != "" && stationName.contains("구")) {
				 if (isNumber(item)) {
					 DustAPIDto dto = new DustAPIDto();
					 dto = gson.fromJson(item, DustAPIDto.class);
					 dto.setIsAuto("1");	//스케줄러로 저장된 데이터 여부
					 dtoList.add(dto);		//json -> Dto List Mapping
				 }
			 }
		 }
		 return dtoList;
	 }
	 
	 
	 public boolean isNumber(JsonElement item) {
		 List<String> chkArr = new ArrayList<String>();
		 chkArr.add(item.getAsJsonObject().get("pm10Value").getAsString());
		 chkArr.add(item.getAsJsonObject().get("pm10Value24").getAsString());
		 chkArr.add(item.getAsJsonObject().get("pm25Value").getAsString());
		 chkArr.add(item.getAsJsonObject().get("pm25Value24").getAsString());
		 chkArr.add(item.getAsJsonObject().get("pm10Grade").getAsString());
		 chkArr.add(item.getAsJsonObject().get("pm10Grade1h").getAsString());
		 chkArr.add(item.getAsJsonObject().get("pm25Grade").getAsString());
		 chkArr.add(item.getAsJsonObject().get("pm10Grade1h").getAsString());
		 
		 boolean result = false;
		 try {
			 for (String value:chkArr) {
				 Double.parseDouble(value);
			 }
			 result = true;
		 } catch(Exception e) {}
		 return result;
	 }
}
