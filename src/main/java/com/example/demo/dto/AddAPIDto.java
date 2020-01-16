package com.example.demo.dto;

public class AddAPIDto {
	private String zipNo;
	private String lnmAdres;
	private String rnAdres;
	
	public String getZipNo() {
		return zipNo;
	}
	public void setZipNo(String zipNo) {
		this.zipNo = zipNo;
	}
	public String getLnmAdres() {
		return lnmAdres;
	}
	public void setLnmAdres(String lnmAdres) {
		this.lnmAdres = lnmAdres;
	}
	public String getRnAdres() {
		return rnAdres;
	}
	public void setRnAdres(String rnAdres) {
		this.rnAdres = rnAdres;
	}
	
	@Override
	public String toString() {
		return "AddAPIDto [zipNo=" + zipNo + ", lnmAdres=" + lnmAdres + ", rnAdres=" + rnAdres + "]";
	}
	
	/* API response list
	<zipNo>61725</zipNo>
	<lnmAdres>광주광역시 남구 서문대로749번길 3 (주월동, 클럽메드엄지통닭)</lnmAdres>
	<rnAdres>광주광역시 남구 주월동 408-1 클럽메드엄지통닭</rnAdres>
	*/
}
