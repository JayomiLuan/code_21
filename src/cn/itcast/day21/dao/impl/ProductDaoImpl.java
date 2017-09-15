package cn.itcast.day21.dao.impl;

import cn.itcast.day21.dao.ProductDao;
import cn.itcast.day21.domain.Product;
import cn.itcast.day21.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
	
	//使用无参构造创建DBUtils的核心对象，用于执行SQL语句
	private QueryRunner queryRunner = new QueryRunner();
	
	@Override
	public void insert(Product product) throws Exception {
		Connection conn = JdbcUtils.getConnection();
		String sql = "insert into tb_product(id,name,price,img_path,description) values(?,?,?,?,?)";
		queryRunner.update(conn, sql,
				product.getId(),
				product.getName(),
				product.getPrice(),
				product.getImg_path(),
				product.getDescription() );
		
	}

	@Override
	public void delete(String id) throws Exception {
		Connection conn = JdbcUtils.getConnection();
		String sql = "delete from tb_product where id=?";
		queryRunner.update(conn, sql , id);
	}

	@Override
	public void update(Product product) throws Exception {
		Connection conn = JdbcUtils.getConnection();
		String sql = "update tb_product set name=?,price=?,img_path=?,description=? where id=?";
		queryRunner.update(conn, sql,
				product.getName(),
				product.getPrice(),
				product.getImg_path(),
				product.getDescription(), 
				product.getId() );
	}

	//按ID取得单个商品对象
	@Override
	public Product get(String id) throws Exception {
		
		Connection conn = JdbcUtils.getConnection();
		String sql = "select id,name,price,img_path,description,category_id from tb_product where id=?";
		Product product = (Product) queryRunner.query(conn, sql ,id , new BeanHandler(Product.class));
		
		return product;
	}

	@Override
	public List<Product> getAll() throws Exception {
		List<Product> list;
		//用当前线程取得对应的连接对象
		Connection conn = JdbcUtils.getConnection();
		String sql = "select id,name,price,img_path,description,category_id from tb_product";
		list = (List<Product>) queryRunner.query(conn, sql, new BeanListHandler(Product.class) );

		return list;
	}

	/**
	 * 取得总的记录条数
	 */
	@Override
	public long getCount() throws Exception {
		Connection conn = JdbcUtils.getConnection();
		String sql = "select count(*) from tb_product";
		//取得的结果集为单一值，所以使用ScalarHandler()
		long r = (long)queryRunner.query(conn, sql, new ScalarHandler() );
		
		return r;
	}

	/**
	 * 取得指定页的数据
	 */
	@Override
	public List<Product> getByPage(int pageNumber, int pageSize) throws Exception {
		List<Product> list;
		Connection conn = JdbcUtils.getConnection();
		String sql = "select id,name,price,img_path,description,category_id from tb_product limit ?,?";
		
		//计算从第几条记录开始取得数据(计算得到)，取多少条（pageSize）
		list = (List<Product>) queryRunner.query(conn, sql,new BeanListHandler( Product.class ) , (pageNumber-1)*pageSize, pageSize);
		
		return list;
	}

	@Override
	public List<Product> getByCondition(String condition) throws Exception {
		List<Product> list;
		Connection conn = JdbcUtils.getConnection();
		//拼接条件查询SQL语句：and category_id=? and name like ?
		//如果不加where 1=1 则会出现：select * from tb_product and category='xxxx'的错误SQL
		String sql = "select id,name,price,img_path,description,category_id from tb_product where 1=1 " + condition;
		
		list = (List<Product>) queryRunner.query(conn, sql,new BeanListHandler( Product.class ));
		
		return list;
	}

}
