package com.sample.test;

public class KeywordPOJO {

	private Integer id;
	private String localPrice = "";
	private String cropName = "";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getLocalPrice() {
		return localPrice;
	}

	public void setLocalPrice(String localPrice) {
		this.localPrice = localPrice;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	@Override
	public String toString() {
		return "KeywordPOJO [id=" + id + ", localPrice=" + localPrice + ", cropName=" + cropName + "]";
	}

}
