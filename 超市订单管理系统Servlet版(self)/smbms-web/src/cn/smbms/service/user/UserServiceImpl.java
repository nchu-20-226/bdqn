package cn.smbms.service.user;

import java.sql.Connection;
import java.util.List;

import cn.smbms.dao.BaseDao;
import cn.smbms.dao.user.UserDao;
import cn.smbms.dao.user.UserDaoImpl;
import cn.smbms.pojo.User;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 * @author Administrator
 *
 */
public class UserServiceImpl implements UserService{
	private UserDao userDao=new UserDaoImpl();

	@Override
	public User getUserbyuserCode(String userCode) throws Exception {
		Connection connection = BaseDao.getConnection();
		User user = userDao.getUserbyuserCode(connection, userCode);
		BaseDao.closeResource(connection,null,null);
		return user;
	}

	@Override
	public User getUserbyId(int id) throws Exception {
		Connection connection = BaseDao.getConnection();
		User user = userDao.getUserbyId(connection, id);
		BaseDao.closeResource(connection,null,null);
		return user;
	}

	@Override
	public boolean updatePwd(int id, String password) throws Exception {
		boolean flag=false;
		Connection connection=BaseDao.getConnection();
		int i = userDao.updatePwd(connection, id, password);
		if(i>0){
			flag=true;
		}
		BaseDao.closeResource(connection,null,null);
		return flag;
	}

	@Override
	public int getUserCount(String userName, int userRole) throws Exception {
		Connection connection = BaseDao.getConnection();
		int count = userDao.getUserCount(connection, userName, userRole);
		BaseDao.closeResource(connection,null,null);
		return count;
	}

	@Override
	public List<User> getSub(String userName, int userRole, int pageIndex, int pageSize) throws Exception {
		Connection connection = BaseDao.getConnection();
		List<User> list = userDao.getSub(connection, userName, userRole, pageIndex, pageSize);
		BaseDao.closeResource(connection,null,null);
		return list;
	}

	@Override
	public boolean addUser(User user) throws Exception {
		Connection connection=BaseDao.getConnection();
		boolean flag=false;
		int i = userDao.addUser(connection, user);
		if(i>0){
			flag=true;
		}
		BaseDao.closeResource(connection, null,null);
		return flag;
	}

	@Override
	public boolean delUser(int id) throws Exception {
		Connection connection=BaseDao.getConnection();
		boolean flag=false;
		int i = userDao.delUser(connection, id);
		if(i>0){
			flag=true;
		}
		BaseDao.closeResource(connection, null,null);
		return flag;
	}

	@Override
	public boolean updateUser(User user) throws Exception {
		Connection connection=BaseDao.getConnection();
		boolean flag=false;
		int i = userDao.updateUser(connection, user);
		if(i>0){
			flag=true;
		}
		BaseDao.closeResource(connection, null,null);
		return flag;
	}
}
