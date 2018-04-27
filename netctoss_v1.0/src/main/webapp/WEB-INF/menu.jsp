<%@page pageEncoding="utf-8"%>
<%--
	把重复的部分抽取出来，其他jsp复用。由于menu.jsp被其他所有jsp公用，因此无法预估那个请求中调用，
	所以此处适合写绝对路径，或者完整路径。
  --%>
   <ul id="menu">
<%-- 	/netctoss_v1.0
		<%=request.getContextPath() %>
		/netctoss_v1.0/WEB-INF/main/index.jsp
		<%=request.getRequestURI() %>
--%>
       <li><a href="<%=request.getContextPath() %>/toIndex.do" class="index_off"></a></li>
       <li><a href="" class="role_off"></a></li>
       <li><a href="" class="admin_off"></a></li>
       <li><a href="<%=request.getContextPath() %>/findCost.do" class="fee_off"></a></li>
       <li><a href="" class="account_off"></a></li>
       <li><a href="" class="service_off"></a></li>
       <li><a href="" class="bill_off"></a></li>
       <li><a href="" class="report_off"></a></li>
       <li><a href="" class="information_off"></a></li>
       <li><a href="" class="password_off"></a></li>
   </ul>