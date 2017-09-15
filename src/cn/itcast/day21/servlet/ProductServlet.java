package cn.itcast.day21.servlet;

import cn.itcast.day21.domain.Category;
import cn.itcast.day21.domain.PageBean;
import cn.itcast.day21.domain.Product;
import cn.itcast.day21.service.CategoryService;
import cn.itcast.day21.service.ProductService;
import cn.itcast.day21.service.impl.CategoryServiceImpl;
import cn.itcast.day21.service.impl.ProductServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品信息的Servlet
 * @author Administrator
 *
 */
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//得到Service实现类的对象，便于调用
	private ProductService productService = new ProductServiceImpl();
	//持有商品分类的业务逻辑对象，便于调用
	private CategoryService categoryService = new CategoryServiceImpl();
	
	//重写构造器，用于在Servlet初始化时，把所有分类信息从数据库中读取出来，保存到ServletContext范围
	@Override
	public void init(){
		List<Category> list;
		//保存分类信息的ID和name的键值对
		Map<String,String> map = new HashMap<String,String>();
		
		try {
			//把取得的所有商品信息的List转换成Map
			list = categoryService.getAll();
			for( Category c : list ){
				map.put( c.getId() , c.getName() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//把分类信息保存在ServletContext中，可以在服务器运行其间使用，此数据保存在内存中，所以访问效率很高
		this.getServletContext().setAttribute("categoryMap", map);
	}
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置请求的字符集，解决表单提交的中文乱码问题
		req.setCharacterEncoding("UTF-8");
		
		//规定method参数，传递当前请求所要执行的操作（查询、添加、转跳添加页面等）
		String method = req.getParameter("method");
		
		switch( method ){
			case "findAll":
				//查询所有商品信息，转跳到商品信息列表页面
				findAll( req , resp );
				break;
			case "addUI":
				//转跳添加商品信息的页面
				addUI( req , resp );
				break;
			case "add":
				//把提交的商品信息表单写入数据库
				add( req , resp );
				break;
			case "modifyUI":
				//转跳到商品信息修改页面
				modifyUI( req ,resp );
				break;
			case "modify":
				//把提交的商品信息更新到数据库
				modify( req , resp );
				break;
			case "delete":
				//删除指定的商品信息
				delete( req , resp );
				break;
			case "findByCondition":
				//根据条件查询商品信息
				findByCondition( req , resp );
				break;
		}
		
		
	}
	
	
	/**
	 * 调用业务逻辑，取得所有商品信息，转到商品列表页面进行显示
	 * @param req
	 * @param resp
	 */
	private void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//取得所有商品信息
		PageBean<Product> all = null;
		//接收提交的页号
		String t = req.getParameter("pageNumber");
		if( t == null || "".equals(t) ){//容错处理，当没有指定页号时，默认为1
			t="1";
		}
		int pageNumber = Integer.parseInt( t );
		
		//接收页大小
		t = req.getParameter("pageSize");
		if( t == null || "".equals(t) ){//容错处理，当没有指定页大小时，默认为5
			t="5";
		}
		int pageSize = Integer.parseInt( t );
		

		try {
			//调用Service，按页取得数据
			all = productService.getByPage(pageNumber, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//保存到request域 
		req.setAttribute("pageBean", all);
	
		//内部转发到显示页面
		req.getRequestDispatcher("/admin/product/list.jsp").forward(req, resp);
		
	}
	
	/**
	 * 转跳到添加商品页面的方法
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//转跳到添加商品的页面， 可以进行数据准备
		req.getRequestDispatcher("/admin/product/add.jsp").forward(req, resp);
	}
	
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//接收请求参数（要添加的商品信息）
		Product product = new Product();
		try {
			BeanUtils.populate( product, req.getParameterMap() );

			//调用业务逻辑，写入数据库
			productService.add(product);
			
			/*转跳到所有商品列表的页面
			基于两个原因，不能使用内部转发：
				一、当前的Request对象中没有所有商品信息，内部转发到list.jsp不能显示信息的
				二、使用内部转发时，浏览器依然使的是提交添加表单的URL，此时用户按f5刷新页面，则会出现添加商品表单重复提交的问题
			所以只能使用重定向
			*/
			//也需要动态取得当前项目的名称
			resp.sendRedirect( getServletContext().getContextPath() + "/productServlet?method=findAll");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 转跳到修改商品信息的页面
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void modifyUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//取得提交的要删除商品的ID
		String id = req.getParameter("id");
		
		//调用业务逻辑，取得被修改商品的信息
		Product product = null;
		try {
			product = productService.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//把当前要被修改的商品信息保存到reuest对象
		req.setAttribute("product", product);
		
		//内部转发到修改页面
		req.getRequestDispatcher("/admin/product/modify.jsp").forward(req, resp);
	}
	
	private void modify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//接收请求参数（要添加的商品信息）
		Product product = new Product();
		try {
			BeanUtils.populate( product, req.getParameterMap() );

			//调用业务逻辑，写入数据库
			productService.modify(product);
			
			//也需要动态取得当前项目的名称
			resp.sendRedirect( getServletContext().getContextPath() + "/productServlet?method=findAll");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	/**
	 * 删除商品的方法
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//取得提交的要删除商品的ID
		String id = req.getParameter("id");
		
		//调用业务逻辑删除
		try {
			productService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//转跳页面
		resp.sendRedirect( getServletContext().getContextPath() + "/productServlet?method=findAll");

	}

	//根据提交的查询条件取得相应的商品信息
	private void findByCondition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//得到查询条件
		Product product = new Product();
		List<Product> list = null;
		
		try {
			BeanUtils.populate(product, req.getParameterMap() );
			//调业务逻辑，按条件得到相应的商品信息
			list = productService.getByCondition(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//把数据保存到Request，
		req.setAttribute("allProduct", list);
		//为了实现查询表单的回填，查询条件的Bean也存入request，带回页面
		req.setAttribute("condition", product);
		
		//转跳页面list2.jsp
		req.getRequestDispatcher("/admin/product/list2.jsp").forward(req, resp);
		
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
