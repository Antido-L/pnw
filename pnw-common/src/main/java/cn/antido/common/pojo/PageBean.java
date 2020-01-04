package cn.antido.common.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * @Description 通用分页对象
 * @author Antido
 * @date 2017年12月17日 下午2:11:10
 */
public class PageBean<T> implements Serializable {
	
	private Integer currentPage;
	private Long totalCount;
	private Integer totalPages;
	private Integer pageSize;
	private List<T> dataList;
	
	public PageBean() {
		super();
	}

	public PageBean(Integer currentPage, Long totalCount, Integer totalPages, Integer pageSize, List<T> dataList) {
		super();
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.dataList = dataList;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
	
	
}
