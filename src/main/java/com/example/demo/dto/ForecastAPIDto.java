package com.example.demo.dto;

public class ForecastAPIDto {
	private String dataTime;
	private String informCode;
	private String informOverall;
	private String informCause;
	private String informGrade;
	private String imageUrl1;
	private String imageUrl2;
	private String imageUrl3;
	private String imageUrl4;
	private String imageUrl5;
	private String imageUrl6;
	private String informData;
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	public String getInformCode() {
		return informCode;
	}
	public void setInformCode(String informCode) {
		this.informCode = informCode;
	}
	public String getInformOverall() {
		return informOverall;
	}
	public void setInformOverall(String informOverall) {
		this.informOverall = informOverall;
	}
	public String getInformCause() {
		return informCause;
	}
	public void setInformCause(String informCause) {
		this.informCause = informCause;
	}
	public String getInformGrade() {
		return informGrade;
	}
	public void setInformGrade(String informGrade) {
		this.informGrade = informGrade;
	}
	public String getImageUrl1() {
		return imageUrl1;
	}
	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}
	public String getImageUrl2() {
		return imageUrl2;
	}
	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}
	public String getImageUrl3() {
		return imageUrl3;
	}
	public void setImageUrl3(String imageUrl3) {
		this.imageUrl3 = imageUrl3;
	}
	public String getImageUrl4() {
		return imageUrl4;
	}
	public void setImageUrl4(String imageUrl4) {
		this.imageUrl4 = imageUrl4;
	}
	public String getImageUrl5() {
		return imageUrl5;
	}
	public void setImageUrl5(String imageUrl5) {
		this.imageUrl5 = imageUrl5;
	}
	public String getImageUrl6() {
		return imageUrl6;
	}
	public void setImageUrl6(String imageUrl6) {
		this.imageUrl6 = imageUrl6;
	}
	public String getInformData() {
		return informData;
	}
	public void setInformData(String informData) {
		this.informData = informData;
	}
	@Override
	public String toString() {
		return "ForecastAPIDto [dataTime=" + dataTime + ", informCode=" + informCode + ", informOverall="
				+ informOverall + ", informCause=" + informCause + ", informGrade=" + informGrade + ", imageUrl1="
				+ imageUrl1 + ", imageUrl2=" + imageUrl2 + ", imageUrl3=" + imageUrl3 + ", imageUrl4=" + imageUrl4
				+ ", imageUrl5=" + imageUrl5 + ", imageUrl6=" + imageUrl6 + ", informData=" + informData + "]";
	}
	
	
	
	/*
	 * <dataTime>2020-01-01 23시 발표</dataTime>
	 * 
	 * <informCode>PM10</informCode>
	 * 
	 * <informOverall>○ [미세먼지] 수도권·강원영서·충청권·광주·전북·대구는 '나쁨', 그 밖의 권역은 '보통'으로
	 * 예상됨.</informOverall>
	 * 
	 * <informCause>○ [미세먼지] 대기 정체로 전일부터 축적된 미세먼지에 국외 미세먼지가 유입되어 대부분 서쪽지역과 일부
	 * 영남내륙지역에서 농도가 높을 것으로 예상됨.</informCause>
	 * 
	 * <informGrade>서울 : 보통,제주 : 보통,전남 : 보통,전북 : 보통,광주 : 보통,경남 : 보통,경북 : 보통,울산 :
	 * 보통,대구 : 보통,부산 : 보통,충남 : 보통,충북 : 보통,세종 : 보통,대전 : 보통,영동 : 보통,영서 : 보통,경기남부 :
	 * 보통,경기북부 : 보통,인천 : 보통</informGrade>
	 * 
	 * <actionKnack/>
	 * 
	 * <imageUrl1>http://www.airkorea.or.kr/file/viewImage/?atch_id=133056</
	 * imageUrl1>
	 * 
	 * <imageUrl2>http://www.airkorea.or.kr/file/viewImage/?atch_id=133057</
	 * imageUrl2>
	 * 
	 * <imageUrl3>http://www.airkorea.or.kr/file/viewImage/?atch_id=133058</
	 * imageUrl3>
	 * 
	 * <imageUrl4>http://www.airkorea.or.kr/file/viewImage/?atch_id=133059</
	 * imageUrl4>
	 * 
	 * <imageUrl5>http://www.airkorea.or.kr/file/viewImage/?atch_id=133060</
	 * imageUrl5>
	 * 
	 * <imageUrl6>http://www.airkorea.or.kr/file/viewImage/?atch_id=133061</
	 * imageUrl6>
	 * 
	 * <informData>2020-01-02</informData>
	 */
}
