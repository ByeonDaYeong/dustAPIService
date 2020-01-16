package com.example.demo.dto;

public class DustAPIDto {
	private String stationName;		// 측정소명 (ex:중구)
	private String dataTime;		// 측정 날짜
	
	//Value Data
	private int pm10Value;			// 미세먼지 측정농도
	private int pm10Value24;		// 미세먼지 24시간 예측 이동농도
	private int pm25Value;			// 초미세먼지 측정농도
	private int pm25Value24;		// 초미세먼지 24시간 예측 이동농도
	
	//Grade data (1.좋음 2.보통 3.나쁨 4.매우나쁨)
	private int pm10Grade;			// 미세먼지 24시간 등급 자료
	private int pm10Grade1h;		// 미세먼지 1시간 등급 자료
	private int pm25Grade;			// 초미세먼지 24시간 등급 자료
	private int pm25Grade1h;		// 초미세먼지 1시간 등급 자료
	
	//DB Data
	private String schTime;			// 조회 날짜 
	private String isAuto;			// 스케줄링에 의한 데이터 여부
	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	public int getPm10Value() {
		return pm10Value;
	}
	public void setPm10Value(int pm10Value) {
		this.pm10Value = pm10Value;
	}
	public int getPm10Value24() {
		return pm10Value24;
	}
	public void setPm10Value24(int pm10Value24) {
		this.pm10Value24 = pm10Value24;
	}
	public int getPm25Value() {
		return pm25Value;
	}
	public void setPm25Value(int pm25Value) {
		this.pm25Value = pm25Value;
	}
	public int getPm25Value24() {
		return pm25Value24;
	}
	public void setPm25Value24(int pm25Value24) {
		this.pm25Value24 = pm25Value24;
	}
	public int getPm10Grade() {
		return pm10Grade;
	}
	public void setPm10Grade(int pm10Grade) {
		this.pm10Grade = pm10Grade;
	}
	public int getPm10Grade1h() {
		return pm10Grade1h;
	}
	public void setPm10Grade1h(int pm10Grade1h) {
		this.pm10Grade1h = pm10Grade1h;
	}
	public int getPm25Grade() {
		return pm25Grade;
	}
	public void setPm25Grade(int pm25Grade) {
		this.pm25Grade = pm25Grade;
	}
	public int getPm25Grade1h() {
		return pm25Grade1h;
	}
	public void setPm25Grade1h(int pm25Grade1h) {
		this.pm25Grade1h = pm25Grade1h;
	}
	public String getSchTime() {
		return schTime;
	}
	public void setSchTime(String schTime) {
		this.schTime = schTime;
	}
	public String getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}
	@Override
	public String toString() {
		return "DustAPIDto [stationName=" + stationName + ", dataTime=" + dataTime + ", pm10Value=" + pm10Value
				+ ", pm10Value24=" + pm10Value24 + ", pm25Value=" + pm25Value + ", pm25Value24=" + pm25Value24
				+ ", pm10Grade=" + pm10Grade + ", pm10Grade1h=" + pm10Grade1h + ", pm25Grade=" + pm25Grade
				+ ", pm25Grade1h=" + pm25Grade1h+ ", schTime=" + schTime + "isAuto=" + isAuto +"]";
	}
	
	
	
	/* API response list
	_returnType: "json",
	coGrade: "1",
	coValue: "0.5",
	dataTerm: "",
	dataTime: "2020-01-09 15:00",
	khaiGrade: "3",
	khaiValue: "135",
	mangName: "도시대기",
	no2Grade: "1",
	no2Value: "0.015",
	numOfRows: "10",
	o3Grade: "2",
	o3Value: "0.031",
	pageNo: "1",
	pm10Grade: "2",
	pm10Grade1h: "2",
	pm10Value: "46",
	pm10Value24: "50",
	pm25Grade: "3",
	pm25Grade1h: "3",
	pm25Value: "43",
	pm25Value24: "45",
	resultCode: "",
	resultMsg: "",
	rnum: 0,
	serviceKey: "",
	sidoName: "",
	so2Grade: "1",
	so2Value: "0.004",
	stationCode: "",
	stationName: "중구",
	totalCount: "",
	ver: "",
	*/
}
