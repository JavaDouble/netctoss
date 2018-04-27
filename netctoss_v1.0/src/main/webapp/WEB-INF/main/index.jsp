<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>项目－NetCTOSS_V1.0</title>
        <link type="text/css" rel="stylesheet" media="all" href="styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="styles/global_color.css" />
    </head>
    <body class="index">
        <!--导航区域开始-->
        <div id="index_navi">
		<%--方式一： 使用JSP include指令 引入menu.jsp 将menu.jsp引入到此处，相当于将它内部的代码复制到此处。  --%>
		<%-- <%@include file="../menu.jsp" %> --%>
		<!--方式二：使用jstl标签 引入menu.jsp  -->
		<c:import url="../menu.jsp"></c:import>
        </div>
    </body>
</html>
