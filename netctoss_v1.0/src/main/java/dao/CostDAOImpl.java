package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Cost;
import util.DBUtil;

public class CostDAOImpl implements CostDAO {

	@Override
	public List<Cost> findAll() {
		try {
			Connection connection=DBUtil.getConnection();
			String sql ="select * from cost_lqs "+ "order by cost_id";
			System.out.println(sql);
			PreparedStatement ps=connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			List<Cost> list = new ArrayList<Cost>();
			while(rs.next()) {
				Cost c=createCost(rs);
				list.add(c);
			}
				return list;
		} catch (Exception e) {
			//实际开发中，记录日志 log4j slf4j 异常能处理自己处理 不能处理抛出异常
			e.printStackTrace();
			throw new RuntimeException("查询资费失败", e);
		}finally{
			DBUtil.closeConnection();
		}
	}

	@Override
	public boolean save(Cost c) {
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "insert into cost_lqs values(" + "cost_seq_lqs.nextval," + "?,?,?,?,'1',?,sysdate,null,?)";
			System.out.println(sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getName());
			// setInt()和setDouble不支持null,
			// 但业务上、数据库设计上很多数字 字段是允许为null的,此时将它们当做普通的对象Object处理即可.
			ps.setObject(2, c.getBaseDuration());
			ps.setObject(3, c.getBaseCost());
			ps.setObject(4, c.getUnitCost());
			ps.setString(5, c.getDescr());
			ps.setString(6, c.getCostType());
			int num=ps.executeUpdate();
			System.out.println("增加了"+num+"条数据");
			if(num<=0){
				return false;
			}
			System.out.println("资费增加成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("增加资费失败", e);
		} finally {
			DBUtil.closeConnection();
		}
	}



	/**
	 * 根据id查询资费
	 */
	@Override
	public Cost findById(int id) {
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "select * from cost_lqs where cost_id=?";

			System.out.println(sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();

			if(rs.next()) {
				return createCost(rs);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("增加资费失败", e);
		} finally {
			DBUtil.closeConnection();
		}

	}

	/**
	 * 	//Alt+Shift+M 提取重复代码 	快速创建方法的快捷键
	 * findAll()与findById(int id)中的重复代码
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Cost createCost(ResultSet rs) throws SQLException {
		Cost c=new Cost();
		c.setCostId(rs.getInt("cost_id"));
		c.setName(rs.getString("name"));
		c.setBaseDuration(rs.getInt("base_duration"));
		c.setBaseCost(rs.getDouble("base_cost"));
		c.setUnitCost(rs.getDouble("unit_cost"));
		c.setStatus(rs.getString("status"));
		c.setDescr(rs.getString("descr"));
		c.setCreateTime(rs.getTimestamp("creatime"));
		c.setStartTime(rs.getTimestamp("startime"));
		c.setCostType(rs.getString("cost_type"));
		return c;
	}

	@Override
	public boolean updateById(Cost c) {
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "update cost_lqs set name=?, base_duration=?,base_cost=?,unit_cost=?,descr=?,cost_type=? where cost_id=?";
			System.out.println(sql);

			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, c.getName());
			ps.setObject(2, c.getBaseDuration());
			ps.setObject(3, c.getBaseCost());
			ps.setObject(4, c.getUnitCost());
			ps.setString(5, c.getDescr());
			ps.setString(6, c.getCostType());
			ps.setObject(7, c.getCostId());

			int num=ps.executeUpdate();
			System.out.println("修改了"+num+"条数据");
			if(num<=0){
				return false;
			}
			System.out.println("资费修改成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("修改资费失败", e);
		}finally {
			DBUtil.closeConnection();
		}
	}

	@Override
	public boolean deleteById(int id) {
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "delete from cost_lqs where cost_id=?";
			System.out.println(sql);

			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, id);

			int num=ps.executeUpdate();
			System.out.println("删除了"+num+"条数据");
			if(num<=0){
				return false;
			}
			System.out.println("资费删除成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除资费失败", e);
		}finally {
			DBUtil.closeConnection();
		}
	}

	@Override
	public boolean useCost(Cost c) {
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "update cost_lqs set status=?,startime=? where cost_id=?";
			System.out.println(sql);

			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, c.getStatus());
			ps.setTimestamp(2, c.getStartTime());
			ps.setObject(3, c.getCostId());

			int num=ps.executeUpdate();

			if(num<=0){
				return false;
			}
			System.out.println("资费启用成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("启用资费失败", e);
		}finally {
			DBUtil.closeConnection();
		}
	}

	/**
	 * 查询某一页的资费数据
	 * @param page 页码
	 * @param size 每页显示的行数
	 */
	@Override
	public List<Cost> findByPage(int page, int pageSize) {
		try {
			Connection conn = DBUtil.getConnection();
			String sql="SELECT * "+"FROM(SELECT ROWNUM r,t.* "+"FROM(SELECT * "+"FROM cost_lqs "+"ORDER BY cost_id) t "+"WHERE ROWNUM<=?)"+"WHERE r>=?";
			PreparedStatement ps =conn.prepareStatement(sql);
			ps.setInt(1, page*pageSize);
			ps.setInt(2, (page-1)*pageSize+1);
			ResultSet rs = ps.executeQuery();
			List<Cost> list = new ArrayList<Cost>();
			while(rs.next()) {
				Cost c = createCost(rs);
				list.add(c);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("分页查询资费失败", e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	/**
	 *  查询总行数
	 */
	@Override
	public int findRows() {
		try {
			Connection connection=DBUtil.getConnection();
			String sql ="select count(*) from cost_lqs";
			System.out.println(sql);
			PreparedStatement ps=connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询总行数失败", e);
		}finally{
			DBUtil.closeConnection();
		}
		return 0;
	}
	/**
	 * 测试方法
	 * @param args
	 */
	public static void main(String[] args) {
/*		//1.测试资费查询
		CostDAO dao = new CostDAOImpl();
		List<Cost> list = dao.findAll();
		for(Cost c : list) {
			System.out.println(c);
		}

		//2.测试资费查询
		Cost c = new Cost();
		c.setName("包月");
		//c.setBaseDuration(660);
		c.setBaseCost(1999.0);
		//c.setUnitCost(0.6);
		c.setDescr("包月很爽");
		c.setCostType("1");
		boolean flag=dao.save(c);
		if(flag){
			System.out.println("资费增加成功");
		}else{
			System.out.println("资费增加失败");
		}*/

		//3.测试根据id查询资费
		CostDAO dao = new CostDAOImpl();
		Cost c = dao.findById(1);
		System.out.println(c);

		//4.测试总行数
		int rows = dao.findRows();
		System.out.println("资费表中一共有"+rows+"条数据！");

		//5.测试第1页的数据
		List<Cost> list=dao.findByPage(1, 3);
		for(Cost cost:list){
			System.out.println(cost);
		}
	}

}
