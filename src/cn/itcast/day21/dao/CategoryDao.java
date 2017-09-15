package cn.itcast.day21.dao;

import java.util.List;

import cn.itcast.day21.domain.Category;

public interface CategoryDao {
	List<Category> getAll() throws Exception;
}
