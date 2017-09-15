package cn.itcast.day21.service.impl;

import java.sql.Connection;
import java.util.List;

import cn.itcast.day21.dao.CategoryDao;
import cn.itcast.day21.dao.impl.CategoryDaoImpl;
import cn.itcast.day21.domain.Category;
import cn.itcast.day21.service.CategoryService;
import cn.itcast.day21.utils.JdbcUtils;

public class CategoryServiceImpl implements CategoryService {

	private CategoryDao categoryDao = new CategoryDaoImpl();
	
	@Override
	public List<Category> getAll() throws Exception {
		Connection conn = null;
		List<Category> list = null;
		
		try{
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			
			list = categoryDao.getAll();
			
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
