package cn.smbms.service.user;

import java.util.List;

import cn.smbms.pojo.User;

public interface UserService {
	User getUserbyuserCode(String userCode) throws  Exception;
	User getUserbyId(int id) throws  Exception;
	//修改密码
	boolean updatePwd(int id, String password) throws Exception;

	//根据用户要求查询总数
	int getUserCount(String userName,int userRole) throws Exception;
	//分页显示用户数据
	List<User> getSub(String userName, int userRole, int pageIndex, int pageSize) throws Exception;

	boolean addUser(User user) throws Exception;
	boolean delUser(int id) throws Exception;
	boolean updateUser(User user) throws Exception;
}
