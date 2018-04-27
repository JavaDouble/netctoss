package web;


import java.util.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDAO;
import dao.AdminDAOImpl;
import dao.CostDAO;
import dao.CostDAOImpl;
import entity.Admin;
import entity.Cost;
import util.ImageUtil;


/**
 * 控制层：处理路径，分发
 *
 * @author Double
 *
 */
public class MainServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 3682284444702921748L;

	/**
	 * 控制层：处理路径，分发
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获取Servlet路径
		String path=request.getServletPath();

		//根据路径分发
		if("/findCost.do".equals(path)){
			//处理业务  查询资费
			findCost(request, response);
		}else if("/toAddCost.do".equals(path)){
			//打开增加资费页面
			toAddCost(request, response);
		}else if("/addCost.do".equals(path)){
			//增加保存资费
			addCost(request, response);
		}else if("/toUpdateCost.do".equals(path)){
			//打开修改资费页面
			toUpdateCost(request, response);
		}else if("/updateCost.do".equals(path)){
			//修改资费
			updateCost(request, response);
		}else if("/deleteCost.do".equals(path)){
			//删除资费
			deleteCost(request, response);
		}else if("/useCost.do".equals(path)){
			//开启资费
			useCost(request, response);
		}else if("/toCostDetail.do".equals(path)){
			//资费详情
			toCostDetail(request, response);
		}else if("/toLogin.do".equals(path)){
			//打开登录页面
			toLogin(request, response);
		}else if("/login.do".equals(path)){
			//登录验证
			login(request, response);
		}else if("/toIndex.do".equals(path)){
			//打开主页
			toIndex(request, response);
		}else if("/logout.do".equals(path)){
			//退出(登出)
			logout(request, response);
		}else if("/createImage.do".equals(path)){
			//生成图片和验证码
			createImage(request, response);
		}else {
			//错误的路径
			throw new  RuntimeException("查询此页");
		}
	}

	/**
	 * 	//Alt+Shift+M 提取重复代码 快速创建方法的快捷键
	 * 业务层 处理业务逻辑		资费模块：查询所有资费
	 *
	 * @param resquest
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void findCost1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//查询所有资费
		CostDAO dao=new CostDAOImpl();
		List<Cost> list=dao.findAll();
		//转发jsp 1.绑定数据 2.转发
		request.setAttribute("costs", list);
		//当前:/netctoss/findCosts.do
		//目标:/netctoss/WEB-INF/cost/find.jsp
		request.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(request, response);
	}

	/**
	 * 	//Alt+Shift+M 提取重复代码 快速创建方法的快捷键
	 * 业务层 处理业务逻辑		资费模块：查询所有资费并分页
	 *
	 * @param resquest
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findCost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CostDAO dao=new CostDAOImpl();
		//分页
		//从find.jsp中获取请求参数
		String page=request.getParameter("page");
		//System.out.println(page);
		if(page==null || "".equals(page)){
			page="1";
		}
		//从web.xml中获取常量
		String pageSize=this.getServletContext().getInitParameter("pageSize");
		//System.out.println(pageSize);
		List<Cost> list=dao.findByPage(Integer.parseInt(page), Integer.parseInt(pageSize));
		//查询总行数，计算出总页数
		int rows=dao.findRows();
		int totalPage=rows/new Integer(pageSize);
		if(rows%Integer.parseInt(pageSize) != 0){
			totalPage++;
		}
		//转发到查询页面
		request.setAttribute("costs", list);
		request.setAttribute("page", page);
		request.setAttribute("totalPage", totalPage);
		//当前：/netctoss/findCost.do
		//目标:netctoss/WEBiINF/cost/findCost.jsp
		request.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(request, response);
	}

	/**
	 * 业务层 处理业务逻辑		资费模块：打开增加资费页面
	 *
	 * @param request
	 * @param respone
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toAddCost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/cost/add.jsp").forward(request, respone);
	}

	/**
	 * 业务层 处理业务逻辑		资费模块：增加资费
	 * @param resquest
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addCost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 接收参数
		request.setCharacterEncoding("utf-8");
		Cost c = getCost(request);
		CostDAO dao = new CostDAOImpl();
		dao.save(c);
		// 重定向到查询
		// 当前:/netctoss/addCost.do
		// 目标:/netctoss/findCost.do
		response.sendRedirect("findCost.do");
	}

	/**
	 * 	//Alt+Shift+M 提取重复代码 快速创建方法的快捷键
	 *
	 * @param request
	 * @return
	 */
	private Cost getCost(HttpServletRequest request) {
		String name = request.getParameter("name");
		String costType = request.getParameter("costType");
		String baseDuration = request.getParameter("baseDuration");
		String baseCost = request.getParameter("baseCost");
		String unitCost = request.getParameter("unitCost");
		String descr = request.getParameter("descr");
		// 保存资费
		Cost c = new Cost();
		c.setName(name);
		c.setCostType(costType);
		if (baseDuration != null && !baseDuration.equals("")) {
			c.setBaseDuration(new Integer(baseDuration));
		}
		if (baseCost != null && !baseCost.equals("")) {
			c.setBaseCost(new Double(baseCost));
		}
		if (unitCost != null && !unitCost.equals("")) {
			c.setUnitCost(new Double(unitCost));
		}
		c.setDescr(descr);
		return c;
	}


	/**
	 * 业务层 处理业务逻辑		资费模块：跳转到资费修改原液
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toUpdateCost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 接收参数
		int id = Integer.valueOf(request.getParameter("id"));
		// 查询要修改的这条数据
		CostDAO dao=new CostDAOImpl();
		Cost cost=dao.findById(id);
		//转发 	绑定数据和转发
		request.setAttribute("cost", cost);
		request.getRequestDispatcher("WEB-INF/cost/update.jsp").forward(request, response);
	}

	/**
	 * 业务层 处理业务逻辑		资费模块：资费修改一业务
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateCost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 接收参数
		request.setCharacterEncoding("utf-8");
		Cost cost=getCost(request);
		cost.setCostId(Integer.valueOf(request.getParameter("costId")));
		CostDAO dao = new CostDAOImpl();
		dao.updateById(cost);
		response.sendRedirect("findCost.do");
	}

	/**
	 * 业务层 处理业务逻辑		资费模块：删除资费
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteCost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 接收参数
		int id = Integer.valueOf(request.getParameter("id"));
		CostDAO dao = new CostDAOImpl();
		dao.deleteById(id);
		response.sendRedirect("findCost.do");
	}

	/**
	 * 业务层 处理业务逻辑		资费模块：启用资费
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void useCost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 接收参数
		int id = Integer.valueOf(request.getParameter("id"));
		System.out.println(id);
		CostDAO dao = new CostDAOImpl();
		Cost c=dao.findById(id);
		System.out.println("启动资费前："+c);
		if(("0".equals(c.getStatus())) || (c.getStartTime()!=null)){
			System.out.println(c.getStatus());
			System.out.println(c.getStartTime());
			System.out.println("资费已开通！");
		}else{
			Timestamp time=new Timestamp(System.currentTimeMillis());
			c.setStartTime(time);
			c.setStatus("0");
			dao.useCost(c);
			System.out.println("启动资费后："+c);
		}
		response.sendRedirect("findCost.do");
	}

	/**
	 * 业务层 处理业务逻辑		资费模块：打开资费详情页面 	资费详情
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toCostDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 接收参数
		int id = Integer.valueOf(request.getParameter("id"));
		// 查询要修改的这条数据
		CostDAO dao=new CostDAOImpl();
		Cost cost=dao.findById(id);
		System.out.println(cost);
		//转发 	绑定数据和转发
		request.setAttribute("cost", cost);
		request.getRequestDispatcher("WEB-INF/cost/detail.jsp").forward(request, response);
	}

	/**
	 * 业务层 处理业务逻辑		登录：打开登录页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//转发
		request.getRequestDispatcher("WEB-INF/main/login.jsp").forward(request, response);
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//转发
		request.getRequestDispatcher("WEB-INF/main/index.jsp").forward(request, response);
	}

	/**
	 * 业务层 处理业务逻辑		登录：登录成功后，打开主页 验证登录数据
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 接收参数 获取 账号和密码、验证码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String code	= request.getParameter("code");

		//校验验证码
		HttpSession session=request.getSession();
		String imageCode=(String) session.getAttribute("imageCode");

		if(code==null || !imageCode.equalsIgnoreCase(code)){
			//验证码错误
			System.out.println("验证码错误!");
			request.setAttribute("error", "验证码错误");
			request.getRequestDispatcher("WEB-INF/main/login.jsp").forward(request, response);
			return;
		}

		//验证：1.验证账号	2. 验证密码
		AdminDAO dao=new AdminDAOImpl();
		Admin admin=dao.findByUsername(username);
		//1.验证账号
		if(admin==null){
			System.out.println("账号错误!");
			request.setAttribute("error", "账号错误");
			request.getRequestDispatcher("WEB-INF/main/login.jsp").forward(request, response);
		}else if(!admin.getPassword().equals(password)){
			//2.验证密码
			System.out.println("密码错误!");
			request.setAttribute("error", "密码错误");
			request.getRequestDispatcher("WEB-INF/main/login.jsp").forward(request, response);
		}else{
			//将账号存入cookie，后面其他页面上使用  key-value 字符串
			Cookie c=new Cookie("username", username);
			//cookie的有效路径 	/netctoss_v1.0/login.do 对/netctoss_v1.0及其以下有效 不用修改路径
			response.addCookie(c);

			//将账号存入session,后面其他页面中使用
			session.setAttribute("adminCode", username);

			//检查通过
			response.sendRedirect("toIndex.do");
		}
	}

	/**
	 * 业务层 处理业务逻辑		登出：session实现logo区的退出功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//销毁session 删除session对象 	session中的数据随着sessino一并消失
		request.getSession().invalidate();
		System.out.println("销毁session成功！");
		//当前路径logout.do 目前路径：toLogin.do
		response.sendRedirect("toLogin.do");
	}

	/**
	 * 业务层 处理业务逻辑		生成验证码及图片
	 * 校验验证码：session实现验证码校验
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void createImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取工具类生成的图片和验证码
		Object[] objs=ImageUtil.createImage();

		//将验证码存入session
		HttpSession session=request.getSession();
		session.setAttribute("imageCode", objs[0]);

		/*
		 * 想浏览器输入图片、文本、音频和视频 有固定的格式，遵循w3c规范,不遵循浏览器就无法识别。不能识别，浏览器
		 * 就会提示保存。所以要遵循w3c规范， 也可以查看tomcat的Server下的web.xml。
		 * 浏览器和服务器都遵循w3c规范规定的固定格式。
		 * tomcat启动时，会读取Server目录下的web.xml相关参数，有时候需要修改web.xml中内容,如果直接修改
		 * tomcat自带的web.xml，这样对所有的项目都会产生影响，所以我们创建的web项目，有一个web.xml.
		 * 是对tomcat自带的web.xml的补充,相同的内容就覆盖，不同的内容就继承。
		 */
		//将图片输出给浏览器
		response.setContentType("image/png");
		//服务器获取输出流，该流由服务器自动创建，它所输出的目标就是当前访问的浏览器。
		OutputStream os=response.getOutputStream();
		ImageIO.write((BufferedImage) objs[1], "png", os);
		os.close();
	}
}
