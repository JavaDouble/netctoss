package dao;

import java.util.List;


import entity.Cost;


/**
 * 资费模块
 *
 * @author Double
 *
 */
public interface CostDAO {

	List<Cost> findAll();
	boolean save(Cost c);
	Cost findById(int id);
	boolean updateById(Cost c);
	boolean deleteById(int id);
	boolean useCost(Cost c);
	List<Cost> findByPage(int page, int size);
	int findRows();
}
