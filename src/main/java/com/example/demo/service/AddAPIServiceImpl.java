package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddAPIDto;
import com.example.demo.dto.DustAPIDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
@Service
public class AddAPIServiceImpl{
	
	public List<AddAPIDto> selectAddr(String addrName, String dongOrRoad) throws IOException{
		String inputAdd = addrName; 
		System.out.println("Add Service Area~!");
		
		String responseData = getAddAPIData(inputAdd, dongOrRoad);
		return getDtoListFromXml(responseData);
	}
	
	
	/** Call Add API 
	 * return: XML Response Data formed String
	 * @throws IOException */ 
	public String getAddAPIData(String inputAdd, String dongOrRoad) throws IOException {
		// Call API
		StringBuilder urlBuilder = new StringBuilder("http://openapi.epost.go.kr/postal/retrieveNewAdressAreaCdService/retrieveNewAdressAreaCdService/getNewAddressListAreaCd"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=mWOUP6hFibrsdKm56wULHkl93YWqbqfALbjYOD9XH%2F1ASgmGqBlXVo5YZIpfA5P5DgSlFTaggM2zrYBUWiHQug%3D%3D"); /*Service Key*/
		urlBuilder.append("&" + URLEncoder.encode("searchSe","UTF-8") + "=" + URLEncoder.encode(dongOrRoad, "UTF-8")); /*dong : 동(읍/면)명 road :도로명[default] post : 우편번호 */
        urlBuilder.append("&" + URLEncoder.encode("srchwrd","UTF-8") + "=" + URLEncoder.encode(inputAdd, "UTF-8")); /*검색어*/
        urlBuilder.append("&" + URLEncoder.encode("countPerPage","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*페이지당 출력될 개수를 지정*/
        urlBuilder.append("&" + URLEncoder.encode("currentPage","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*출력될 페이지 번호*/
		
	    URL url = new URL(urlBuilder.toString());
	    System.out.println(urlBuilder.toString());
	    
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        
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
        System.out.println(sb.toString());
        return sb.toString();
	}
	
	
	/** parsing xml String to JsonArr
	 * return: address List Formed JsonArray 
     */
	 public List<AddAPIDto> getDtoListFromXml(String xmlStr) {
		 JsonParser parser = new JsonParser();
		 JSONObject jsonObj = new JSONObject();
		 jsonObj = XML.toJSONObject(xmlStr.toString());
		 
		 JsonElement element = parser.parse(jsonObj.toString());
		 element = element.getAsJsonObject().get("NewAddressListResponse");
		 
		 return getDtoListFromJsonElement(element);
	 }
	 
	 
	 /** Get AddAPIDto List from JsonElement 
	  *	 return: AddAPIDto Array */
	 public List<AddAPIDto> getDtoListFromJsonElement(JsonElement element) {
		 JsonArray jsonArr = new JsonArray();
		 Gson gson = new Gson();
		 List<AddAPIDto> dtoList = new ArrayList<AddAPIDto>();
		 
		 if (element.getAsJsonObject().get("newAddressListAreaCd").isJsonArray()) {	
			 //jsonArray로 변환 가능(json list 가 2개 이상)
			 jsonArr = element.getAsJsonObject().get("newAddressListAreaCd").getAsJsonArray();
			 // mapping jsonArr to Dto
			 for (JsonElement item : jsonArr) {
				 dtoList.add(gson.fromJson(item, AddAPIDto.class));
			 }
		 } else {
			//jsonArray로 변환 불가(json list 가 1개 )
			 JsonElement castjson = (JsonElement) element.getAsJsonObject().get("newAddressListAreaCd");
			 dtoList.add(gson.fromJson(castjson, AddAPIDto.class));
		 }
		 return dtoList;
	 }
}
