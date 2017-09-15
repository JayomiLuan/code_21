package cn.itcast.day21.dao.impl;

import cn.itcast.day21.dao.CategoryDao;
import cn.itcast.day21.domain.Category;
import cn.itcast.day21.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

	private QueryRunner queryRunner = new QueryRunner();
	
	@Override
	public List<Category> getAll() throws Exception {
		List<Category> list = null;
		Connection conn = JdbcUtils.getConnection();
		String sql = "select id,name from tb_category";
		list = (List<Category>) queryRunner.query(conn,sql, new BeanListHandler(Category.class) );
		
		return list;
	}

}
