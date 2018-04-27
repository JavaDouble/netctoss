package web;

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
/**
 * 使用Filter拦截没有登录的用户，跳转到登录页面
 * @author Double
 *
 */
public class LoginFilter implements Filter {
	//统计拦截次数的变量
	private long count=0;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		/*
		 * 1.排除不过滤的请求
		 * 2.从session尝试获取账号
		 * 3.根据账号判断用户是否登录
		 */
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)res;

		//排序不过滤的请求	这个路径必须准确，不然会报循环重定向错误
		String[] paths=new String[]{"/toLogin.do","/login.do","/createImage.do"};
		//获取当前Servlet路径
		String path=request.getServletPath();
		//如果当前路径时不过滤的请求之一，不过滤请求，请求继续执行
		for(String p:paths){
			if(p.equals(path)){
				chain.doFilter(request, response);
				return;
			}
		}

		//从session中尝试获取账号
		HttpSession session=request.getSession();
		String adminCode=(String) session.getAttribute("adminCode");
		//根据账号判断用户是否登录
		if(adminCode==null){
			//统计拦截次数
			count++;
			System.out.println("拦截次数："+count+"次！");
			//未登录，重定向到登录页面
			response.sendRedirect(request.getContextPath()+"/toLogin.do");
		}else {
			//已登录，请求继续执行
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {

	}

}
