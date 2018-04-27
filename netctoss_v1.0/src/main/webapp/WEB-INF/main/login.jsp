<%@page pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>项目－NetCTOSS_V1.0</title>
        <link type="text/css" rel="stylesheet" media="all" href="styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="styles/global_color.css" />
<%--	<!--校验zha  -->
	 <script>
		//检查帐号密码格式
		function checkUser(){
			console.log(1);
			//获取文本框内的值
		 	var username=document.getElementByName("username").value;
		 	//console.log(typeof(username));
			//获取span
		 	var u_msg=document.getElementById("u_msg");
		 	//获取span标签内部的内容
		 	console.log(u_msg.innerHTML);//5-10位字母，数字，下划线
		 	//检查帐号的格式
			//var reg=/^[a-zA-Z0-9_]{5,10}$/;
			var reg=/^\w{5,10}$/;
			if(reg.test(username)){
				u_msg.className="ok";
				return true;
			}else{
				u_msg.className="error";
					if(username.length<5){
					u_msg.innerHTML="帐号长度太短，安全性低";
				}else if(username.length>10){
					u_msg.innerHTML="帐号太长";
				}else{
					u_msg.innerHTML="非法输入";
			/* 	}
					return false; */
			}
		}
		//检查密码格式
		function checkPwd(){
			var password=document.getElementByName("password").value;
			var p_msg=document.getElementById("p_msg");
			//var reg=/^[a-zA-Z0-9_]{8,12}$/;
			var reg=/^\w{8,12}$/;
			if(reg.test(password)){
				p_msg.className="ok";
				return true;
			}else{
				p_msg.className="error";
					if(password.length<8){
					p_msg.innerHTML="密码太短，安全性低";
				}else if(password.length>12){
					p_msg.innerHTML="密码长度超出范围";
				}else{
					p_msg.innerHTML="非法输入";
				}
					return false;
			}
		}
	</script>
	 --%>
    </head>
    <body class="index">
        <div class="login_box">
        <form action="login.do" method="post">
            <table>
                <tr>
                    <td class="login_info">账号：</td>
                    <td colspan="2"><input name="username"  value="${param.username }" type="text" class="width150" /></td>
                    <td class="login_error_info"><span class="required">30长度的字母、数字和下划线</span></td>
                </tr>
                <tr>
                    <td class="login_info">密码：</td>
                    <td colspan="2"><input name="password" value="${param.password }" type="password" class="width150" /></td>
                    <td><span class="required">30长度的字母、数字和下划线</span></td>
                </tr>
                <tr>
                    <td class="login_info">验证码：</td>
                    <td class="width70"><input name="code" type="text" class="width70" /></td>
                    <!-- 路径后面增加了参数(随机数),是为了欺骗浏览器，让它以为路径变了。 -->
                    <td><img src="createImage.do" alt="验证码" title="点击更换" onclick="this.setAttribute('src','createImage.do?x='+Math.random())" /></td>
                    <td><span class="required"></span></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="login_button" colspan="2">
                 		<%--
               			表单元素form有如下2个提交方式:
               			1. onsubmit事件，通过submit按钮自动触发
               			2. submit()，通过js调用这个方法
               			3.在超链接上可以写js，但必须进行声明，语法：javascript:js代码;
                   		 --%>
                        <a href="javascript:document.forms[0].submit();"><img src="images/login_btn.png" /></a>
                    </td>
                    <td><span class="required">${error }</span></td>
                </tr>
            </table>
         </form>
        </div>
    </body>
</html>
