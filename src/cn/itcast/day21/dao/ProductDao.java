package cn.itcast.day21.dao;

import java.util.List;

import cn.itcast.day21.domain.Product;

public interface ProductDao {
	void insert( Product product ) throws Exception;
	void delete(String id) throws Exception;
	void update( Product product ) throws Exception;
	Product get(String id) throws Exception;
	List<Product> getAll()  throws Exception;
	long getCount() throws Exception;
	List<Product> getByPage( int pageNumber , int pageSize )  throws Exception;	
	
	//按指定条件查询商品信息
	List<Product> getByCondition( String condition ) throws Exception;
	
}
