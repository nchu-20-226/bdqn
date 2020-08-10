package cn.smbms.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.StringUtils;

import cn.smbms.dao.BaseDao;
import cn.smbms.pojo.User;

/**
 * dao层抛出异常，让service层去捕获处理
 * @author Administrator
 *
 */
public class UserDaoImpl implements UserDao{


	@Override
	public User getUserbyuserCode(Connection connection, String userCode) throws Exception {
		User user=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		String sql="select * from smbms_user where userCode=?";
		Object[] param={userCode};
		rs = BaseDao.execute(connection, ps, rs, sql, param);
		if(rs.next()){
			user = new User();
			user.setId(rs.getInt("id"));
			user.setUserCode(rs.getString("userCode"));
			user.setUserName(rs.getString("userName"));
			user.setUserPassword(rs.getString("userPassword"));
			user.setGender(rs.getInt("gender"));
			user.setBirthday(rs.getDate("birthday"));
			user.setPhone(rs.getString("phone"));
			user.setAddress(rs.getString("address"));
			user.setUserRole(rs.getInt("userRole"));
			user.setCreatedBy(rs.getInt("createdBy"));
			user.setCreationDate(rs.getTimestamp("creationDate"));
			user.setModifyBy(rs.getInt("modifyBy"));
			user.setModifyDate(rs.getTimestamp("modifyDate"));
		}
		BaseDao.closeResource(null,ps,rs);
		return user;
	}

	@Override
	public User getUserbyId(Connection connection, int id) throws Exception {
		User user=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		String sql="select u.* ,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=? and u.userRole = r.id";
		Object[] param={id};
		rs = BaseDao.execute(connection, ps, rs, sql, param);
		if(rs.next()){
			user = new User();
			user.setId(rs.getInt("id"));
			user.setUserCode(rs.getString("userCode"));
			user.setUserName(rs.getString("userName"));
			user.setUserPassword(rs.getString("userPassword"));
			user.setGender(rs.getInt("gender"));
			user.setBirthday(rs.getDate("birthday"));
			user.setPhone(rs.getString("phone"));
			user.setAddress(rs.getString("address"));
			user.setUserRole(rs.getInt("userRole"));
			user.setCreatedBy(rs.getInt("createdBy"));
			user.setCreationDate(rs.getTimestamp("creationDate"));
			user.setModifyBy(rs.getInt("modifyBy"));
			user.setModifyDate(rs.getTimestamp("modifyDate"));
			user.setUserRoleName(rs.getString("userRoleName"));
		}
		BaseDao.closeResource(null,ps,rs);
		return user;
	}

	@Override
	public int updatePwd(Connection connection, int id, String password) throws Exception {
		String sql="update smbms_user set userPassword=? where id=?";
		PreparedStatement ps=null;
		Object[] params={password,id};
		int i = BaseDao.execute(connection, ps, sql, params);
		BaseDao.closeResource(null,ps,null);
		return i;
	}

	@Override
	public List<User> getUser(Connection connection) throws Exception {
		User user=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<User> list=new ArrayList<>();
		String sql="select * from smbms_user";
		rs = BaseDao.execute(connection,ps,rs,sql,null);
		if(rs.next()){
			user = new User();
			user.setId(rs.getInt("id"));
			user.setUserCode(rs.getString("userCode"));
			user.setUserName(rs.getString("userName"));
			user.setUserPassword(rs.getString("userPassword"));
			user.setGender(rs.getInt("gender"));
			user.setBirthday(rs.getDate("birthday"));
			user.setPhone(rs.getString("phone"));
			user.setAddress(rs.getString("address"));
			user.setUserRole(rs.getInt("userRole"));
			user.setCreatedBy(rs.getInt("createdBy"));
			user.setCreationDate(rs.getTimestamp("creationDate"));
			user.setModifyBy(rs.getInt("modifyBy"));
			user.setModifyDate(rs.getTimestamp("modifyDate"));
			list.add(user);
		}
		BaseDao.closeResource(connection,ps,rs);
		return list;
	}

	@Override
	public int getUserCount(Connection connection, String userName, int userRole) throws Exception {
		String sql="SELECT COUNT(1) count FROM smbms_role r,smbms_user u WHERE u.`userRole`=r.`id`";
		PreparedStatement ps=null;
		ResultSet rs=null;
		int count=0;
		StringBuffer sb=new StringBuffer(sql);
		List<Object> list=new ArrayList<>();
		if(!StringUtils.isNullOrEmpty(userName)){
			sb.append(" and u.userName LIKE CONCAT('%',?,'%')");
			list.add(userName);
		}
		if (userRole>0){
			sb.append(" and r.id=?");
			list.add(userRole);
		}
		sql=sb.toString();
		rs = BaseDao.execute(connection, ps, rs, sql, list.toArray());
		if(rs.next()){
			count = rs.getInt("count");
		}
		BaseDao.closeResource(null,ps,rs);
		return count;
	}

	@Override
	public List<User> getSub(Connection connection, String userName, int userRole, int pageIndex, int pageSize) throws Exception {
		String sql="SELECT u.id id, userCode,userName, gender,DATE_FORMAT(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(birthday)), '%Y')+0 AS age,phone,r.roleName rolename FROM smbms_role r,smbms_user u WHERE u.`userRole`=r.`id`";
		List<Object> list=new ArrayList<>();
		List<User> userList=new ArrayList<>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		User user=null;
		StringBuffer sb=new StringBuffer(sql);
		if(!StringUtils.isNullOrEmpty(userName)){
			sb.append(" and u.userName like concat('%',?,'%')");
			list.add(userName);
		}
		if(userRole>0){
			sb.append(" and u.userRole = ?");
			list.add(userRole);
		}
		sb.append(" limit ?,?");
		list.add((pageIndex-1)*pageSize);
		list.add(pageSize);
		rs=BaseDao.execute(connection,ps,rs,sb.toString(),list.toArray());
		while(rs.next()){
			System.out.println("----------");
			user=new User();
			user.setId(rs.getInt("id"));
			user.setAge(rs.getInt("age"));
			user.setUserCode(rs.getString("userCode"));
			user.setUserName(rs.getString("userName"));
			user.setPhone(rs.getString("phone"));
			user.setUserRoleName(rs.getString("rolename"));
			user.setGender(rs.getInt("gender"));
			userList.add(user);
		}
		BaseDao.closeResource(null, ps, rs);
		return userList;
	}

	@Override
	public int addUser(Connection connection,User user) throws Exception {
		String sql="insert into smbms_user(userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate) values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps=null;
		Object[] params={user.getUserCode(),user.getUserName(),user.getUserPassword(),user.getGender(),user.getBirthday(),user.getPhone(),user.getAddress(),user.getUserRole(),user.getCreatedBy(),user.getCreationDate()};
		int i = BaseDao.execute(connection, ps, sql, params);
		BaseDao.closeResource(null,ps,null);
		return i;

	}

	@Override
	public int delUser(Connection connection, int id) throws Exception {
		String sql="delete from smbms_user where id=?";
		PreparedStatement ps=null;
		Object[] params={id};
		int i = BaseDao.execute(connection, ps, sql, params);
		BaseDao.closeResource(null,ps,null);
		return i;
	}

	@Override
	public int updateUser(Connection connection, User user) throws Exception {
		String sql="update smbms_user set userName=?,gender=?,birthday=?,phone=?,address=?,userRole=? where id=?";
		PreparedStatement ps=null;
		Object[] params={user.getUserName(),user.getGender(),user.getBirthday(),user.getPhone(),user.getAddress(),user.getUserRole(),user.getId()};
		int i=BaseDao.execute(connection,ps,sql,params);
		BaseDao.closeResource(null,ps,null);
		return i;
	}
}
