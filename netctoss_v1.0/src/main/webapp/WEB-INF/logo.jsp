<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<!--提取logo区内容 复用  -->
	<!--修改路径../images/logo.png，去掉'../'  -->
   <img src="images/logo.png" alt="logo" class="left"/>
   <%-- 获取登录时存取的cookie，二种方式：1.JSP脚本 request.getCookies	2.EL表达式或者JSTL标签  --%>
   <%--
   		EL表达式获取cookie：EL默认取值范围[page/request/session/application]
   		EL也可以从cookie中取值，语法为：cookie.key.value
   --%>
   	<!-- cookie获取账号 -->
	<%-- <span>${cookie.username.value }</span> --%>
	<!-- session获取账号 -->
	<span>${adminCode }</span>
   <a href="<%=request.getContextPath() %>/logout.do">[退出]</a>