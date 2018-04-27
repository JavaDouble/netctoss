package dao;

import entity.Admin;

public interface AdminDAO {

	Admin findByUsername(String username);

}
