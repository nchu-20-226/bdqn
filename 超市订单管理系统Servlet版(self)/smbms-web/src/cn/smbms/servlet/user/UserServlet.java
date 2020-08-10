package cn.smbms.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.smbms.service.role.RoleService;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleServiceImpl;
import cn.smbms.service.user.UserService;
import cn.smbms.service.user.UserServiceImpl;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
@WebServlet("/jsp/user.do")
public class UserServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=req.getParameter("method");
		try {
			if(method.equals("savepwd")){
				this.updatePwd(req,resp);
			}else if (method.equals("pwdmodify")){
				this.pwdmodify(req,resp);
			}else if (method.equals("query")){
				this.query(req,resp);
			}else if(method.equals("add")){
				this.addUser(req,resp);
			}else if(method.equals("getrolelist")){
				this.getRoleList(req,resp);
			}else if(method.equals("ucexist")){
				this.ucexist(req,resp);
			}else if(method.equals("deluser")){
				this.deluser(req,resp);
			}else if(method.equals("view")){
				this.userView(req,resp);
			}else if(method.equals("modify")){
				this.getUserById(req,resp,"usermodify.jsp");
			}else if(method.equals("modifyexe")){
				this.userModify(req,resp);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	//修改密码
	private void updatePwd(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserService userService=new UserServiceImpl();

		String newPassword = req.getParameter("newpassword");
		Object o = req.getSession().getAttribute(Constants.USER_SESSION);

		System.out.println("fgafd"+(o!=null));
		System.out.println(newPassword);
		if(o!=null && !StringUtils.isNullOrEmpty(newPassword)){
			System.out.println(((User) o).getId());
			System.out.println(newPassword);
			boolean flag = false;
			try {
				flag = userService.updatePwd(((User) o).getId(), newPassword);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(flag){
				req.setAttribute("message","修改成功，请重新登录");
				req.getSession().removeAttribute(Constants.USER_SESSION);
			}else{
				req.setAttribute("message","修改失败");
			}
		}
		else{
			req.setAttribute("message","新密码有问题");
		}
		req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
	}
	//修改密码时验证旧密码功能
	private void pwdmodify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String oldpassword = req.getParameter("oldpassword");
		Object o = req.getSession().getAttribute(Constants.USER_SESSION);
		Map<String,String> resultMap=new HashMap<>();
		if (o==null){
			resultMap.put("result","sessionerror");
		}else if (StringUtils.isNullOrEmpty(oldpassword)){
			resultMap.put("result","error");
		}else {
			if (!oldpassword.equals(((User)o).getUserPassword())){
				resultMap.put("result","false");
			}else {
				resultMap.put("result","true");
			}
		}

		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.write(JSONArray.toJSONString(resultMap));
		writer.flush();
		writer.close();
	}
	private void query(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		UserService userService=new UserServiceImpl();

		String queryname = req.getParameter("queryname");
		String temp = req.getParameter("queryUserRole");

		String pageIndex = req.getParameter("pageIndex");
		int userRole=0;
		if(temp != null && !temp.equals("")){
			userRole= Integer.parseInt(temp);
		}
		System.out.println(userRole);
		int currentPageNo=1;
		if(pageIndex!=null){
			currentPageNo=Integer.parseInt(pageIndex);
		}
		//获取用户列表
		int totalCount = userService.getUserCount(queryname, userRole);
		PageSupport ps=new PageSupport();
		ps.setCurrentPageNo(currentPageNo);
		ps.setTotalCount(totalCount);

		List<User> userList = userService.getSub(queryname, userRole, currentPageNo,ps.getPageSize());
		req.setAttribute("userList",userList);

		List<Role> roleList = new RoleServiceImpl().getRoleList();
		req.setAttribute("roleList",roleList);

		req.setAttribute("totalCount",totalCount);
		req.setAttribute("currentPageNo",currentPageNo);
		req.setAttribute("totalPageCount",ps.getTotalPageCount());

		req.setAttribute("queryname",queryname);
		req.setAttribute("queryUserRole",userRole);

		req.getRequestDispatcher("userlist.jsp").forward(req,resp);
	}

	private void getRoleList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Role> roleList = null;
		RoleService roleService = new RoleServiceImpl();
		roleList = roleService.getRoleList();
		//把roleList转换成json对象输出
		response.setContentType("application/json");
		PrintWriter outPrintWriter = response.getWriter();
		outPrintWriter.write(JSONArray.toJSONString(roleList));
		outPrintWriter.flush();
		outPrintWriter.close();
	}
	private void  ucexist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userCode = request.getParameter("userCode");
		Map<String,String> resultMap=new HashMap<>();
		if(StringUtils.isNullOrEmpty(userCode)){
			resultMap.put("userCode","exist");
		}else{
			UserService userService=new UserServiceImpl();
			User user = userService.getUserbyuserCode(userCode);
			if(null != user){
				resultMap.put("userCode","exist");
			}else{
				resultMap.put("userCode", "notexist");
			}
		}
		response.setContentType("application/json");
		PrintWriter pw=response.getWriter();
		pw.write(JSONArray.toJSONString(resultMap));
		pw.flush();
		pw.close();

	}
	private void deluser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id = request.getParameter("uid");
		Integer delId = 0;
		try{
			delId = Integer.parseInt(id);
		}catch (Exception e) {
			// TODO: handle exception
			delId = 0;
		}
		Map<String,String> resultMap=new HashMap<>();
		if(delId<=0){
			resultMap.put("delResult","notexist");
		}else{
			UserService userService=new UserServiceImpl();
			if(userService.delUser(delId)){
				resultMap.put("delResult","true");
			}else{
				resultMap.put("delResult","false");
			}
		}
		response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
		writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();

    }
	public void addUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userCode = request.getParameter("userCode");
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		String gender = request.getParameter("gender");
		String sbirthday = request.getParameter("birthday");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String userRole = request.getParameter("userRole");
		Date createDate=new Date();
		User o = (User)request.getSession().getAttribute(Constants.USER_SESSION);
		int createBy=o.getId();
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = fmt.parse(sbirthday);
		User user=new User();
		user.setUserCode(userCode);
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setGender(Integer.valueOf(gender));
		user.setBirthday(birthday);
		user.setPhone(phone);
		if(!StringUtils.isNullOrEmpty(address)){
			user.setAddress(address);
		}
		user.setUserRole(Integer.valueOf(userRole));
		user.setCreatedBy(createBy);
		user.setCreationDate(createDate);
		UserService userService=new UserServiceImpl();
		if(userService.addUser(user)){
			response.sendRedirect(request.getContextPath()+"/jsp/user.do?method=query");
		}else{
			request.getRequestDispatcher("useradd.jsp").forward(request, response);
		}

	}

	private void userView(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String suid = request.getParameter("uid");
		int uid = Integer.parseInt(suid);
		UserService userService=new UserServiceImpl();
        User user = userService.getUserbyId(uid);
        request.setAttribute("user",user);
        request.getRequestDispatcher("userview.jsp").forward(request,response);

    }
    private void getUserById(HttpServletRequest req, HttpServletResponse resp,String url) throws Exception {
		String id = req.getParameter("uid");
		if(!StringUtils.isNullOrEmpty(id)){
			//调用后台方法得到user对象
			UserService userService = new UserServiceImpl();
			User user = userService.getUserbyId(Integer.parseInt(id));
			req.setAttribute("user", user);
			req.getRequestDispatcher(url).forward(req, resp);
		}
	}
	private void userModify(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
		String uid = req.getParameter("uid");
		int id = Integer.parseInt(uid);
		String userName = req.getParameter("userName");
		String gender = req.getParameter("gender");
		String sbirthday = req.getParameter("birthday");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String userRole = req.getParameter("userRole");
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = null;
		if(!StringUtils.isNullOrEmpty(sbirthday)){
			birthday=fmt.parse(sbirthday);
		}
		User user=new User();
		user.setId(id);
		user.setUserName(userName);
		user.setGender(Integer.valueOf(gender));
		user.setBirthday(birthday);
		user.setPhone(phone);
		user.setAddress(address);
		user.setUserRole(Integer.valueOf(userRole));
		UserService userService=new UserServiceImpl();
		if(userService.updateUser(user)){
			resp.sendRedirect(req.getContextPath()+"/jsp/user.do?method=query");
		}else{
			req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
		}

	}



}

