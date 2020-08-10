package cn.smbms.dao.user;


import cn.smbms.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao {
	//login,  用于获取要登陆的用户
	User getUserbyuserCode(Connection connection,String userCode) throws Exception;
	User getUserbyId(Connection connection, int id) throws Exception;
	//修改登录用户的密码
	int updatePwd(Connection connection,int id,String password) throws Exception;
	List<User> getUser(Connection connection) throws  Exception;

	//根据用户要求查询总数
	int getUserCount(Connection connection,String userName,int userRole) throws Exception;
	//分页显示用户数据
	List<User> getSub(Connection connection, String userName, int userRole, int pageIndex, int pageSize) throws Exception;

	int addUser(Connection connection,User user) throws Exception;

	int delUser(Connection connection,int id) throws Exception;

	int updateUser(Connection connection,User user) throws Exception;
}
