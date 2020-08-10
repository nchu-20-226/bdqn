package cn.smbms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.smbms.pojo.User;
import cn.smbms.tools.Constants;

public class SysFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request=(HttpServletRequest)servletRequest;
		HttpServletResponse response=(HttpServletResponse)servletResponse;
		//用户模型
		HttpSession session=request.getSession();
		if(session.getAttribute(Constants.USER_SESSION)==null)
		{
			//没有登录
			response.sendRedirect(request.getContextPath()+"/error.jsp");
			return;
		}

		//post 乱码的解码

		chain.doFilter(request,response);
	}

	@Override
	public void destroy() {

	}

}
