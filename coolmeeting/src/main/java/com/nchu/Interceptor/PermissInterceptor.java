package com.nchu.Interceptor;

import com.nchu.entity.Employee;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PermissInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        String requestURI = request.getRequestURI();
        if("/login".equals(requestURI) || "/register".equals(requestURI) || "/doLogin".equals(requestURI) || "/doReg".equals(requestURI) || "/searchmeetings".equals(requestURI)){
            return true;
        }
        HttpSession session=request.getSession();
        Employee employee = (Employee) session.getAttribute("current");
        if(antPathMatcher.match("/admin/**",requestURI)){
            if(employee!=null && employee.getRole()==2){
                return true;
            }
        }else {
            if(employee!=null){
                return true;
            }
        }

        response.sendRedirect("/login");

        return false;
    }
}
