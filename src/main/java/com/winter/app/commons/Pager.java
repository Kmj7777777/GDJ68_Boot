package com.winter.app.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pager {
	private Long startRow;
	private Long lastRow;
	private String kind;
	private String search;
	
	public Long getStartRow() {
		if(this.startRow == null || this.startRow < 0) {
			return 0L;
		}
		
		return this.startRow;
	}
	
	public Long getLastRow() {
		if(this.lastRow == null || this.lastRow < 0) {
			return 10L;
		}
		
		return this.getLastRow();
	}
	
	public String getSearch() {
		if(this.search == null) {
			return "";
		}
		
		return this.search;
	}
}