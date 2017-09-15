package cn.itcast.day21.domain;

import java.util.List;
/**
 * 用于封装实际要显示的数据和分页信息
 * @author Administrator
 *
 * @param <T>
 */
public class PageBean<T> {
	private int pageNumber;	//当前页号
	private int pageSize;	//页大小
	private long totalCount;	//总记录数
	private int totalPage;	//总页数
	private int startIndex;	//起始索引
	private List<T> data;	//实际当前页的数据
	
	
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
}
