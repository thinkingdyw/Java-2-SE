package com.think.common.util;

import java.util.ArrayList;
import java.util.List;

public final class Page<T> {

	/**
	 * 总记录数
	 */
	private long totalLine; 
	/**
	 * 当前页码
	 */
	private long currentPageNum = 1;
	/**
	 * 总页数
	 */
	private long totalPageNumber;
	/**
	 * 每页记录数,默认20条
	 */
	private int perPageNumber = 20;
	
	private List<T> recordList = new ArrayList<T>();

	public long getStartLine() {
		return (currentPageNum-1)*this.perPageNumber;
	}

	public long getTotalPageNumber() {
		if(0==totalLine){
			this.totalPageNumber=1;
		}else{
			this.totalPageNumber = ((0 == totalLine % this.perPageNumber) ? totalLine
					/ this.perPageNumber
					: totalLine / this.perPageNumber + 1);
		}
		
		return totalPageNumber;
	}

	public long getTotalLine() {
		return totalLine;
	}




	public void setTotalLine(long totalLine) {
		this.totalLine = totalLine;
	}


	public long getCurrentPageNum() {
		return currentPageNum;
	}




	public void setCurrentPageNum(long currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getPerPageNumber() {
		return perPageNumber;
	}




	public void setPerPageNumber(int perPageNumber) {
		this.perPageNumber = perPageNumber;
	}




	public List<T> getRecordList() {
		return recordList;
	}




	public void setRecordList(List<T> recordList) {
		this.recordList = recordList;
	}




	public void setTotalPageNumber(long totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

}
