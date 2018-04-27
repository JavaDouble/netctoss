package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.Admin;
import util.DBUtil;

public class AdminDAOImpl implements AdminDAO {

	@Override
	public Admin findByUsername(String username) {
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "select * from admin_info_lqs where admin_code=?";
			System.out.println(sql);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();

			if(rs.next()) {
				Admin admin = new Admin();
				admin.setAdminId(rs.getInt("admin_id"));
				admin.setAdminCode(rs.getString("admin_code"));
				admin.setPassword(rs.getString("password"));
				admin.setName(rs.getString("name"));
				admin.setTelephone(rs.getString("telephone"));
				admin.setEmail(rs.getString("email"));
				admin.setEnrolldate(rs.getTimestamp("enrolldate"));
				return admin;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询管理员失败", e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	/**
	 * 测试代码
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		AdminDAO dao = new AdminDAOImpl();
		Admin a = dao.findByUsername("caocao");
		System.out.println(a);
	}

}
