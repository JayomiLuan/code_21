package cn.itcast.day21.service.impl;

import java.sql.Connection;
import java.util.List;

import cn.itcast.day21.dao.ProductDao;
import cn.itcast.day21.dao.impl.ProductDaoImpl;
import cn.itcast.day21.domain.PageBean;
import cn.itcast.day21.domain.Product;
import cn.itcast.day21.service.ProductService;
import cn.itcast.day21.utils.JdbcUtils;

public class ProductServiceImpl implements ProductService {

	//持有Dao的实现类的对象，使于调用
	private ProductDao productDao = new ProductDaoImpl();
	
	@Override
	public void add(Product product) throws Exception {
		Connection conn = JdbcUtils.getConnection();
		try{
			conn.setAutoCommit(false);
			productDao.insert(product);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			JdbcUtils.releaseResource(conn);
		}
	}

	@Override
	public void delete(String id) throws Exception {
		Connection conn = JdbcUtils.getConnection();
		try{
			conn.setAutoCommit(false);
			productDao.delete(id);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			JdbcUtils.releaseResource(conn);
		}
	}

	@Override
	public void modify(Product product) throws Exception {
		Connection conn = JdbcUtils.getConnection();
		try{
			conn.setAutoCommit(false);
			productDao.update(product);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			JdbcUtils.releaseResource(conn);
		}
	}

	@Override
	public Product get(String id) throws Exception {
		Connection conn = JdbcUtils.getConnection();
		Product product = null;
		try{
			conn.setAutoCommit(false);
			product = productDao.get(id);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			JdbcUtils.releaseResource(conn);
		}
		return product;
	}

	@Override
	public List<Product> getAll() throws Exception {
		List<Product> list = null;
		Connection conn = null;
		try{
			//用当前线程取得对应的Connection对象
			conn = JdbcUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			//调用Dao
			list = productDao.getAll();
			//提交
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			//回滚
			conn.rollback();
		}finally{
			//释放资源
			JdbcUtils.releaseResource(conn);
		}
		
		return list;
	}

	@Override
	public PageBean<Product> getByPage(int pageNumber, int pageSize) throws Exception {
		PageBean<Product> pageBean = new PageBean<Product>();
		
		Connection conn = null;
		List<Product> pageData = null;
		int totalPage = 0;
		long totalCount = 0L;
		
		try{
			//取得连接
			conn = JdbcUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			
			//取得记录的总条数
			totalCount = productDao.getCount();
			//取得当前页的数据
			pageData = productDao.getByPage(pageNumber, pageSize);

			//计算相关的分页信息(总记录数除以页大小，如果有余数，则页数加1，否则不加)
			totalPage = (int)(totalCount / pageSize + ( totalCount%pageSize==0?0:1 ));

			//提交事务
			conn.commit();
		}catch(Exception e){
			//回滚
			conn.rollback();
			e.printStackTrace();
		}finally{
			JdbcUtils.releaseResource(conn);
		}
		
		//封装成PageBean
		pageBean.setPageNumber(pageNumber);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		pageBean.setData(pageData);
		
		return pageBean;
	}

	@Override
	public List<Product> getByCondition(Product product) throws Exception {
		List<Product> list = null;
		Connection conn = null;
		
		try{
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			
			//应该用Product对象中的分类和名称拼成一个SQL查询语句的where条件
			StringBuilder sb = new StringBuilder();
			//-》and category_id=? and name like ?
			//select * from tb_product where 1=1 
			if( product.getCategory_id() != null && !product.getCategory_id().equals("") ){
				//判断分类ID是否相等
				sb.append( " and category_id='"+product.getCategory_id()+"' " );
			}
			if( product.getName() != null && !product.getName().equals("") ){
				//对于商品名称使用的是模糊查询
				sb.append( " and name like '%"+product.getName()+"%'" );
			}
			
			//传入条件字符串，执行查询
			list = productDao.getByCondition(sb.toString());
			
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			JdbcUtils.releaseResource(conn);
		}
		
		return list;
	}

}
