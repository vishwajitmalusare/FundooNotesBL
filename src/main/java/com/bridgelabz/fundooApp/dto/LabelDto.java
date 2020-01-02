package com.bridgelabz.fundooApp.dto;

public class LabelDto {

	private String labelName;
	
	public LabelDto() {}

	/**
	 * @param labelName
	 */
	public LabelDto(String labelName) {
		this.labelName = labelName;
	}

	public String getLabelName() {
		return labelName;
	}


	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	@Override
	public String toString() {
		return "LabelDto [labelName=" + labelName + "]";
	}

	
}
