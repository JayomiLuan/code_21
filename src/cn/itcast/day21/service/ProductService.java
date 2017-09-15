package cn.itcast.day21.service;

import java.util.List;

import cn.itcast.day21.domain.PageBean;
import cn.itcast.day21.domain.Product;

public interface ProductService {
	void add( Product product ) throws Exception;
	void delete( String id ) throws Exception;
	void modify( Product product ) throws Exception;
	Product get( String id ) throws Exception;
	List<Product> getAll() throws Exception;
	PageBean<Product> getByPage( int pageNumber , int pageSize ) throws Exception;

	//真实开发中，需要定义一个专门封装查询条件的Bean，当前可以使用Product对象本身代替
	List<Product> getByCondition( Product product ) throws Exception;
}
