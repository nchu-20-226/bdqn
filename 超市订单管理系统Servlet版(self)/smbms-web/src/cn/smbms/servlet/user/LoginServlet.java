package cn.smbms.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import cn.smbms.service.user.UserServiceImpl;
import cn.smbms.tools.Constants;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserService userService=new UserServiceImpl();
		String userCode = req.getParameter("userCode");
		System.out.println(userCode);
		String userPassword = req.getParameter("userPassword");
		try {
			User user = userService.getUserbyuserCode(userCode);
			System.out.println(user.getUserPassword());

			if(user!=null && user.getUserPassword().equals(userPassword)){
				req.getSession().setAttribute(Constants.USER_SESSION,user);
				resp.sendRedirect("jsp/frame.jsp");
			}
			else{
				req.setAttribute("error","用户名或密码错误");
				req.getRequestDispatcher("login.jsp").forward(req,resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
